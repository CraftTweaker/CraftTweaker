package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.BracketDumper;
import com.blamejared.crafttweaker.api.annotation.BracketResolver;
import com.blamejared.crafttweaker.api.annotation.BracketValidator;
import com.blamejared.crafttweaker.api.command.type.BracketDumperInfo;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.bracket.ValidatedEscapableBracketParser;
import com.google.common.base.CaseFormat;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zenscript.parser.BracketExpressionParser;

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
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Contains all info on Bracket resolvers, validators and dumpers
 */
public final class BracketResolverRegistry {
    
    private record BracketData(Map<String, BracketHandle> brackets, Multimap<String, String> packageLookup) {
        
        public static BracketData of() {
            
            return new BracketData(new HashMap<>(), HashMultimap.create());
        }
        
    }
    
    private record BracketHandle(Method resolver, Method validator, boolean validatesCustom, BracketDumperInfo dumper) {
        
        public static final BracketHandle EMPTY = new BracketHandle(null, null, false, null);
        
        BracketHandle resolver(final Method resolver) {
            
            return new BracketHandle(resolver, this.validator, this.validatesCustom, this.dumper);
        }
        
        BracketHandle validator(final Method validator, final boolean validatesCustom) {
            
            return new BracketHandle(this.resolver, validator, validatesCustom, this.dumper);
        }
        
        BracketHandle dumper(final Supplier<BracketDumperInfo> dumperCreator, final Method dumpingMethod) {
            
            return new BracketHandle(
                    this.resolver,
                    this.validator,
                    this.validatesCustom,
                    Util.make(
                            this.dumper == null ? dumperCreator.get() : this.dumper,
                            it -> it.addMethodHandle(this.lookup(MethodHandles.publicLookup(), dumpingMethod))
                    )
            );
        }
        
        private MethodHandle lookup(final MethodHandles.Lookup lookup, final Method method) {
            
            try {
                return lookup.unreflect(method);
            } catch(final IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        
    }
    
    private final Map<IScriptLoader, BracketData> brackets = new HashMap<>();
    
    public void addClasses(final List<Class<?>> clsList) {
        
        clsList.stream()
                .flatMap(it -> Arrays.stream(it.getMethods())
                        .map(m -> Pair.of(CraftTweakerAPI.getRegistry().findLoader(it), m)))
                .forEach(it -> this.tryAddMethod(this.brackets, it.getFirst(), it.getSecond()));
    }
    
    public void validateBrackets() {
        
        this.brackets.values()
                .stream()
                .map(BracketData::brackets)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .filter(it -> it.getValue().validator() != null)
                .filter(it -> it.getValue().resolver() == null && !it.getValue().validatesCustom())
                .map(Map.Entry::getKey)
                .forEach(it -> CraftTweakerAPI.LOGGER.info("BEP '{}' has a validator but no BEP method", it));
    }
    
    private void tryAddMethod(final Map<IScriptLoader, BracketData> unbaked, final IScriptLoader targetLoader, final Method method) {
        
        final BracketData targetMap = unbaked.computeIfAbsent(targetLoader, it -> BracketData.of());
        
        if(method.isAnnotationPresent(BracketResolver.class)) {
            this.addBracketResolver(targetMap, method);
        }
        if(method.isAnnotationPresent(BracketDumper.class)) {
            this.addBracketDumper(targetMap.brackets(), method);
        }
        if(method.isAnnotationPresent(BracketValidator.class)) {
            this.addBracketValidator(targetMap.brackets(), method);
        }
    }
    
    private void addBracketResolver(final BracketData data, final Method method) {
        
        if(!Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
            CraftTweakerAPI.LOGGER.warn("Method '{}' is marked as a bracket resolver, but it is not public and static.", method.toString());
            return;
        }
        
        Class<?>[] parameters = method.getParameterTypes();
        final String name = method.getAnnotation(BracketResolver.class).value();
        
        if(parameters.length != 1 || !parameters[0].equals(String.class)) {
            CraftTweakerAPI.LOGGER.error("Method '{}' is marked as a bracket resolver, but it does not have a String as it's only parameter.", method.toString());
            return;
        }
        
        if(data.brackets().getOrDefault(name, BracketHandle.EMPTY).resolver() != null) {
            final Method current = data.brackets().get(name).resolver();
            CraftTweakerAPI.LOGGER.error("Bracket resolver '{}' was already registered: current {}, attempt {}", name, current, method);
            return;
        }
        
        this.updateBracketHandle(data.brackets(), name, it -> it.resolver(method));
        final Class<?> cls = method.getDeclaringClass();
        final String clsName = cls.isAnnotationPresent(ZenCodeType.Name.class)
                ? cls.getAnnotation(ZenCodeType.Name.class).value()
                : cls.getCanonicalName();
        
        data.packageLookup().put(clsName.split("\\.", 2)[0], name);
    }
    
    private void addBracketDumper(final Map<String, BracketHandle> targetMap, final Method method) {
        
        if(!Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
            CraftTweakerAPI.LOGGER.warn("Method '{}' is marked as a bracket dumper, but it is not public and static.", method.toString());
            return;
        }
        
        if(method.getParameterCount() != 0) {
            CraftTweakerAPI.LOGGER.warn("Method '{}' is marked as bracket dumper but does not have 0 parameters.", method.toString());
            return;
        }
        
        if(!Collection.class.isAssignableFrom(method.getReturnType()) ||
                !(method.getGenericReturnType() instanceof final ParameterizedType param) ||
                param.getActualTypeArguments()[0] != String.class) {
            CraftTweakerAPI.LOGGER.warn("Method '{}' is marked as bracket dumper but does not have 'Collection<String>' as return type.", method.toGenericString());
            return;
        }
        
        final BracketDumper annotation = method.getAnnotation(BracketDumper.class);
        final String name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, annotation.value());
        final String subCommand = annotation.subCommandName();
        final String fileName = annotation.fileName();
        this.updateBracketHandle(targetMap, name, it -> it.dumper(() -> new BracketDumperInfo(name, subCommand, fileName), method));
    }
    
    private void addBracketValidator(final Map<String, BracketHandle> targetMap, final Method method) {
        
        if(!Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
            CraftTweakerAPI.LOGGER.warn("Method '{}' is marked as a bracket validator, but it is not public and static.", method.toString());
            return;
        }
        
        final BracketValidator validator = method.getAnnotation(BracketValidator.class);
        final String name = validator.value();
        final Class<?>[] parameters = method.getParameterTypes();
        
        if(parameters.length != 1 || !parameters[0].equals(String.class)) {
            CraftTweakerAPI.LOGGER.error("Method '{}' is marked as a bracket validator, but it does not have a String as it's only parameter.", method.toString());
            return;
        }
        
        if(targetMap.getOrDefault(name, BracketHandle.EMPTY).validator() != null) {
            final Method current = targetMap.get(name).resolver();
            CraftTweakerAPI.LOGGER.error("Bracket resolver '{}' was already registered: current {}, attempt {}", name, current, method);
            return;
        }
        
        if(method.getReturnType() != boolean.class) {
            CraftTweakerAPI.LOGGER.error("Method '{}' is marked as a bracket validator, so it must return a boolean", method);
            return;
        }
        
        this.updateBracketHandle(targetMap, name, it -> it.validator(method, validator.validatesCustom()));
    }
    
    private void updateBracketHandle(final Map<String, BracketHandle> map, final String target, final UnaryOperator<BracketHandle> operation) {
        
        map.put(target, operation.apply(map.getOrDefault(target, BracketHandle.EMPTY)));
    }
    
    public Map<String, BracketDumperInfo> getBracketDumpers(final IScriptLoader loader) {
        
        return this.brackets.getOrDefault(loader, BracketData.of())
                .brackets()
                .entrySet()
                .stream()
                .filter(it -> it.getValue().dumper() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, it -> it.getValue().dumper()));
    }
    
    /**
     * Gets the Bracket Resolver list.
     *
     * @return ImmutableList of the Bracket Resolvers
     */
    public List<Pair<String, BracketExpressionParser>> getBracketResolvers(final IScriptLoader loader, final String rootPackage, final ScriptingEngine engine, final JavaNativeModule module) {
        
        final BracketData data = this.brackets.getOrDefault(loader, BracketData.of());
        return data.packageLookup()
                .get(rootPackage)
                .stream()
                .map(it -> Pair.of(it, this.makeParserFrom(it, data.brackets().get(it), engine, module)))
                .toList();
    }
    
    private BracketExpressionParser makeParserFrom(final String name, final BracketHandle handle, final ScriptingEngine engine, final JavaNativeModule module) {
        
        return new ValidatedEscapableBracketParser(name, module.loadStaticMethod(handle.resolver()), handle.validator(), engine.registry);
    }
    
}
