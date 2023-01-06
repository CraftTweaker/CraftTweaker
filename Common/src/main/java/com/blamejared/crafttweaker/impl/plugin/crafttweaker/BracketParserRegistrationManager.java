package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.annotation.BracketDumper;
import com.blamejared.crafttweaker.api.annotation.BracketResolver;
import com.blamejared.crafttweaker.api.annotation.BracketValidator;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.google.common.base.CaseFormat;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.openzen.zencode.java.ZenCodeType;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

final class BracketParserRegistrationManager {
    
    private record BracketData(Map<String, BracketHandle> brackets, Multimap<String, String> packageLookup) {
        
        BracketData() {
            
            this(new HashMap<>(), HashMultimap.create());
        }
        
    }
    
    private record BracketHandle(Method resolver, Method validator, BracketDumpInfo dumper) {
        
        public static final BracketHandle EMPTY = new BracketHandle(null, null, null);
        
        BracketHandle resolver(final Method resolver) {
            
            return new BracketHandle(resolver, this.validator, this.dumper);
        }
        
        BracketHandle validator(final Method validator) {
            
            return new BracketHandle(this.resolver, validator, this.dumper);
        }
        
        BracketHandle dumper(final String subCommandName, final String outputFileName, final Method dumper) {
            
            return new BracketHandle(
                    this.resolver,
                    this.validator,
                    (this.dumper != null ? this.dumper : new BracketDumpInfo(subCommandName, outputFileName)).method(dumper)
            );
        }
        
    }
    
    private record BracketDumpInfo(String subCommandName, String outputFileName, List<MethodHandle> dumpMethods) {
        
        BracketDumpInfo(final String subCommandName, final String outputFileName) {
            
            this(subCommandName, outputFileName, List.of());
        }
        
        BracketDumpInfo method(final Method dumper) {
            
            return new BracketDumpInfo(
                    this.subCommandName,
                    this.outputFileName,
                    Stream.concat(this.dumpMethods().stream(), Stream.of(this.lookup(dumper))).toList()
            );
        }
        
        private MethodHandle lookup(final Method method) {
            
            try {
                return MethodHandles.publicLookup().unreflect(method);
            } catch(final IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        
    }
    
    private record FlattenedData(String loader, String name, BracketHandle handle) {}
    
    private final Map<String, BracketData> data;
    
    BracketParserRegistrationManager() {
        
        this.data = new HashMap<>();
    }
    
    void addRegistrationCandidate(final Class<?> clazz, final String loader) {
        
        Arrays.stream(clazz.getMethods()).forEach(it -> this.tryAddMethod(this.data, loader, it));
    }
    
    void attemptRegistration(final IBracketParserRegistrationHandler handler) {
        
        this.validateBrackets(this.data);
        this.register(this.data, handler);
        this.data.clear();
    }
    
    private void tryAddMethod(final Map<String, BracketData> data, final String loader, final Method method) {
        
        final BracketData bracketData = data.computeIfAbsent(loader, it -> new BracketData());
        
        if(method.isAnnotationPresent(BracketResolver.class)) {
            this.addBracketResolver(bracketData, method);
        }
        if(method.isAnnotationPresent(BracketDumper.class)) {
            this.addBracketDumper(bracketData.brackets(), method);
        }
        if(method.isAnnotationPresent(BracketValidator.class)) {
            this.addBracketValidator(bracketData.brackets(), method);
        }
    }
    
    private void addBracketResolver(final BracketData data, final Method method) {
        
        if(!Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
            CraftTweakerCommon.logger()
                    .warn("Method '{}' is marked as a bracket resolver, but it is not public and static.", method.toString());
            return;
        }
        
        Class<?>[] parameters = method.getParameterTypes();
        final String name = method.getAnnotation(BracketResolver.class).value();
        
        if(parameters.length != 1 || !parameters[0].equals(String.class)) {
            CraftTweakerCommon.logger()
                    .error("Method '{}' is marked as a bracket resolver, but it does not have a String as it's only parameter.", method.toString());
            return;
        }
        
        if(data.brackets().getOrDefault(name, BracketHandle.EMPTY).resolver() != null) {
            final Method current = data.brackets().get(name).resolver();
            CraftTweakerCommon.logger()
                    .error("Bracket resolver '{}' was already registered: current {}, attempt {}", name, current, method);
            return;
        }
        
        this.updateBracketHandle(data.brackets(), name, it -> it.resolver(method));
        
        // TODO("Needed?")
        final Class<?> cls = method.getDeclaringClass();
        final String clsName = cls.isAnnotationPresent(ZenCodeType.Name.class)
                ? cls.getAnnotation(ZenCodeType.Name.class).value()
                : cls.getCanonicalName();
        
        data.packageLookup().put(clsName.split("\\.", 2)[0], name);
    }
    
    private void addBracketDumper(final Map<String, BracketHandle> targetMap, final Method method) {
        
        if(!Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
            CraftTweakerCommon.logger()
                    .warn("Method '{}' is marked as a bracket dumper, but it is not public and static.", method.toString());
            return;
        }
        
        if(method.getParameterCount() != 0) {
            CraftTweakerCommon.logger()
                    .warn("Method '{}' is marked as bracket dumper but does not have 0 parameters.", method.toString());
            return;
        }
        
        if(!Collection.class.isAssignableFrom(method.getReturnType()) ||
                !(method.getGenericReturnType() instanceof final ParameterizedType param) ||
                param.getActualTypeArguments()[0] != String.class) {
            CraftTweakerCommon.logger()
                    .warn("Method '{}' is marked as bracket dumper but does not have 'Collection<String>' as return type.", method.toGenericString());
            return;
        }
        
        final BracketDumper annotation = method.getAnnotation(BracketDumper.class);
        final String name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, annotation.value());
        final String subCommand = annotation.subCommandName().isEmpty() ? null : annotation.subCommandName();
        final String fileName = annotation.fileName().isEmpty() ? null : annotation.fileName();
        this.updateBracketHandle(targetMap, name, it -> it.dumper(subCommand, fileName, method));
    }
    
    private void addBracketValidator(final Map<String, BracketHandle> targetMap, final Method method) {
        
        if(!Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
            CraftTweakerCommon.logger()
                    .warn("Method '{}' is marked as a bracket validator, but it is not public and static.", method.toString());
            return;
        }
        
        final BracketValidator validator = method.getAnnotation(BracketValidator.class);
        final String name = validator.value();
        final Class<?>[] parameters = method.getParameterTypes();
        
        if(parameters.length != 1 || !parameters[0].equals(String.class)) {
            CraftTweakerCommon.logger()
                    .error("Method '{}' is marked as a bracket validator, but it does not have a String as it's only parameter.", method.toString());
            return;
        }
        
        if(targetMap.getOrDefault(name, BracketHandle.EMPTY).validator() != null) {
            final Method current = targetMap.get(name).resolver();
            CraftTweakerCommon.logger()
                    .error("Bracket resolver '{}' was already registered: current {}, attempt {}", name, current, method);
            return;
        }
        
        if(method.getReturnType() != boolean.class) {
            CraftTweakerCommon.logger()
                    .error("Method '{}' is marked as a bracket validator, so it must return a boolean", method);
            return;
        }
        
        this.updateBracketHandle(targetMap, name, it -> it.validator(method));
    }
    
    private void updateBracketHandle(final Map<String, BracketHandle> map, final String target, final UnaryOperator<BracketHandle> operation) {
        
        map.put(target, operation.apply(map.getOrDefault(target, BracketHandle.EMPTY)));
    }
    
    private void validateBrackets(final Map<String, BracketData> data) {
        
        data.values()
                .stream()
                .map(BracketData::brackets)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .filter(it -> it.getValue().validator() != null)
                .filter(it -> it.getValue().resolver() == null)
                .map(Map.Entry::getKey)
                .forEach(it -> CraftTweakerCommon.logger().info("BEP '{}' has a validator but no BEP method", it));
    }
    
    private void register(final Map<String, BracketData> data, final IBracketParserRegistrationHandler handler) {
        
        data.entrySet()
                .stream()
                .flatMap(it -> it.getValue()
                        .brackets()
                        .entrySet()
                        .stream()
                        .map(x -> new FlattenedData(it.getKey(), x.getKey(), x.getValue())))
                .forEach(it -> this.register(it, handler));
    }
    
    private void register(final FlattenedData data, final IBracketParserRegistrationHandler handler) {
        
        handler.registerParserFor(
                data.loader(),
                data.name(),
                data.handle().resolver(),
                data.handle().validator(),
                this.convert(data.handle().dumper())
        );
    }
    
    private IBracketParserRegistrationHandler.DumperData convert(final BracketDumpInfo info) {
        
        if(info == null) {
            
            return null;
        }
        return new IBracketParserRegistrationHandler.DumperData(
                info.subCommandName(),
                info.outputFileName(),
                () -> info.dumpMethods().stream().map(this::invoke).flatMap(Collection::stream)
        );
    }
    
    @SuppressWarnings("unchecked")
    private Collection<String> invoke(final MethodHandle handle) {
        
        try {
            return (Collection<String>) handle.invokeExact();
        } catch(final Throwable e) {
            throw new RuntimeException("Error executing bracket dumper", e);
        }
    }
    
}
