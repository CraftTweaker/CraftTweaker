package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.natives.INativeTypeRegistry;
import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;
import com.blamejared.crafttweaker.impl.registry.natives.NativeTypeRegistry;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zenscript.codemodel.type.BasicTypeID;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class LoaderSpecificZenClassRegistry {
    
    private record ClassData(
            List<Class<?>> registeredClasses,
            BiMap<String, Class<?>> globals,
            BiMap<String, Class<?>> classes,
            Multimap<String, Class<?>> expansions
    ) {
        
        ClassData() {
            
            this(new ArrayList<>(), HashBiMap.create(), HashBiMap.create(), HashMultimap.create());
        }
        
        void inheritFrom(final IZenClassRegistry.IClassData other) {
            
            other.registeredClasses().forEach(clazz -> {
                if(this.registeredClasses().contains(clazz)) {
                    throw new IllegalStateException("Unable to register the same class twice: " + clazz.getName());
                }
                this.registeredClasses().add(clazz);
            });
            other.globals().forEach((global, clazz) -> {
                if(this.globals().containsKey(global) || this.globals().containsValue(clazz)) {
                    throw new IllegalStateException("Unable to register a global twice: (" + global + ", " + clazz + ')');
                }
                this.globals().put(global, clazz);
            });
            other.classes().forEach((name, clazz) -> {
                if(this.classes().containsKey(name) || this.classes().containsValue(clazz)) {
                    throw new IllegalStateException("Unable to register a class twice: (" + name + ", " + clazz + ')');
                }
                this.classes().put(name, clazz);
            });
            other.expansions().forEach((target, expansion) -> {
                final Collection<Class<?>> current = this.expansions().get(target);
                if(current.contains(expansion)) {
                    throw new IllegalStateException("Unable to register an expansion for " + target + " twice: " + expansion);
                }
                this.expansions().put(target, expansion);
            });
        }
        
    }
    
    private record ClassDataView(
            List<Class<?>> registeredClasses,
            BiMap<String, Class<?>> globals,
            BiMap<String, Class<?>> classes,
            Multimap<String, Class<?>> expansions
    ) implements IZenClassRegistry.IClassData {
        
        ClassDataView(final ClassData view) {
            
            this(
                    Collections.unmodifiableList(view.registeredClasses()),
                    Maps.unmodifiableBiMap(view.globals()),
                    Maps.unmodifiableBiMap(view.classes()),
                    Multimaps.unmodifiableMultimap(view.expansions())
            );
        }
        
    }
    
    private static final class TypeVerifier {
        
        private static final Supplier<Set<String>> BUILTIN_TYPES = Suppliers.memoize(
                () -> Arrays.stream(BasicTypeID.values()).map(it -> it.name).collect(Collectors.toUnmodifiableSet())
        );
        private static final String ARRAY_OPEN = Pattern.quote("[");
        private static final String GENERIC_OPEN = Pattern.quote("<");
        
        private TypeVerifier() {}
        
        boolean isTypeKnown(final String type, final Collection<String> additionalTypes) {
            
            if(type == null) {
                return false;
            }
            
            if(type.isEmpty()) {
                return true;
            }
            
            if(additionalTypes.contains(type)) {
                return true;
            }
            
            if(this.isBuiltin(type)) {
                return true;
            }
            
            if(this.isArray(type)) {
                return this.isArrayKnown(type, additionalTypes);
            }
            
            if(this.isAssociativeArray(type)) {
                return this.isAssociativeArrayKnown(type, additionalTypes);
            }
            
            if(this.isGeneric(type)) {
                return this.isGenericKnown(type, additionalTypes);
            }
            
            return false;
        }
        
        private boolean isBuiltin(final String type) {
            
            return BUILTIN_TYPES.get().contains(type);
        }
        
        private boolean isArray(final String type) {
            
            return type.endsWith("[]");
        }
        
        private boolean isAssociativeArray(final String type) {
            
            return type.endsWith("]");
        }
        
        private boolean isGeneric(final String type) {
            
            return type.endsWith(">");
        }
        
        private boolean isArrayKnown(final String type, final Collection<String> additionalTypes) {
            
            final String notArrayType = type.substring(0, type.length() - 2);
            return this.isTypeKnown(notArrayType, additionalTypes);
        }
        
        private boolean isAssociativeArrayKnown(final String type, final Collection<String> additionalTypes) {
            
            final String[] parts = type.split(ARRAY_OPEN, 2);
            final String keyType = parts[1].substring(0, parts[1].length() - 1);
            return this.isTypeKnown(parts[0], additionalTypes) && this.isTypeKnown(keyType, additionalTypes);
        }
        
        private boolean isGenericKnown(final String type, final Collection<String> additionalTypes) {
            
            final String[] parts = type.split(GENERIC_OPEN, 2);
            final String innerType = parts[1].substring(0, parts[1].length() - 1);
            return this.isTypeKnown(parts[0], additionalTypes) && this.isTypeKnown(innerType, additionalTypes);
        }
        
    }
    
    private static final TypeVerifier VERIFIER = new TypeVerifier();
    static final LoaderSpecificZenClassRegistry EMPTY = new LoaderSpecificZenClassRegistry();
    
    private final NativeTypeRegistry nativeTypeRegistry;
    private final ClassData data;
    private final IZenClassRegistry.IClassData view;
    
    LoaderSpecificZenClassRegistry() {
        
        this.nativeTypeRegistry = new NativeTypeRegistry();
        this.data = new ClassData();
        this.view = new ClassDataView(this.data);
    }
    
    boolean isRegistered(final Class<?> clazz) {
        
        return this.data.classes().containsValue(clazz);
    }
    
    Optional<String> getNameFor(final Class<?> clazz) {
        
        return Optional.ofNullable(this.data.classes().inverse().get(clazz));
    }
    
    @SuppressWarnings("unchecked")
    <T> List<Class<? extends T>> getImplementationsOf(final Class<T> checkFor) {
        //Cast okay because of isAssignableFrom
        return this.data.registeredClasses()
                .stream()
                .filter(checkFor::isAssignableFrom)
                .filter(cls -> !cls.isInterface() && !Modifier.isAbstract(cls.getModifiers()))
                .map(cls -> (Class<? extends T>) cls)
                .collect(Collectors.toList());
    }
    
    IZenClassRegistry.IClassData getImmutableDataView() {
        
        return this.view;
    }
    
    INativeTypeRegistry getNativeTypeRegistry() {
        
        return this.nativeTypeRegistry;
    }
    
    List<Class<?>> getClassesInPackage(final String packageName) {
        
        return this.data.classes()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(packageName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
    
    List<Class<?>> getGlobalsInPackage(final String packageName) {
        
        return this.data.globals()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(packageName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
    
    Set<String> getRootPackages() {
        
        return this.data.classes()
                .keySet()
                .stream()
                .map(key -> key.split("\\.", 2)[0])
                .collect(Collectors.toSet());
    }
    
    void registerNativeType(final NativeTypeInfo info) {
        
        if(this == EMPTY) {
            throw new UnsupportedOperationException();
        }
        
        this.nativeTypeRegistry.addNativeType(info);
        this.data.classes().put(info.name(), info.targetedType());
        CommonLoggers.zenCode()
                .debug("Registering {} for native type '{}'", info.name(), info.targetedType().getName());
    }
    
    void registerZenType(final Class<?> clazz, final ZenTypeInfo info, final boolean globals) {
        
        if(this == EMPTY) {
            throw new UnsupportedOperationException();
        }
        
        this.data.registeredClasses().add(clazz);
        
        switch(info.kind()) {
            case CLASS -> this.registerZenClass(clazz, info.targetName());
            case EXPANSION -> this.registerZenExpansion(clazz, info.targetName());
        }
        if(globals) {
            this.registerGlobals(clazz, info);
        }
    }
    
    void inheritFrom(final Collection<LoaderSpecificZenClassRegistry> registries) {
        
        if(this == EMPTY) {
            throw new UnsupportedOperationException();
        }
        registries.forEach(this::inheritFrom);
    }
    
    LoaderSpecificZenClassRegistry createSnapshot() {
        
        final LoaderSpecificZenClassRegistry snapshot = new LoaderSpecificZenClassRegistry();
        snapshot.inheritFrom(this);
        return snapshot;
    }
    
    private void registerZenClass(final Class<?> clazz, final String name) {
        
        final Class<?> other = this.data.classes().get(name);
        if(other != null) {
            CommonLoggers.zenCode()
                    .error("Duplicate ZenCode Name '{}' in classes '{}' and '{}'", name, other.getName(), clazz.getName());
            return;
        }
        
        this.data.classes().put(name, clazz);
        CommonLoggers.zenCode().debug("Registering '{}' to '{}'", name, clazz);
    }
    
    private void registerZenExpansion(final Class<?> clazz, final String expansionTarget) {
        
        if(!VERIFIER.isTypeKnown(expansionTarget, this.data.classes().keySet())) {
            CommonLoggers.zenCode()
                    .warn("Attempting to register expansion for unknown type '{}', carrying on anyways", expansionTarget);
        }
        this.data.expansions().put(expansionTarget, clazz);
        CommonLoggers.zenCode().debug("Registering expansion '{}' to '{}'", clazz.getName(), expansionTarget);
    }
    
    private void registerGlobals(final Class<?> clazz, final ZenTypeInfo info) {
        
        if(info.kind() == ZenTypeInfo.TypeKind.EXPANSION) {
            throw new IllegalArgumentException("Cannot register globals for an expansion");
        }
        
        boolean hasGlobals = Stream.concat(Arrays.stream(clazz.getDeclaredFields()), Arrays.stream(clazz.getDeclaredMethods()))
                .filter(it -> it.isAnnotationPresent(ZenCodeGlobals.Global.class))
                .mapToInt(Member::getModifiers)
                .anyMatch(it -> Modifier.isPublic(it) && Modifier.isStatic(it));
        
        if(hasGlobals) {
            this.data.globals().put(info.targetName(), clazz);
        }
    }
    
    private void inheritFrom(final LoaderSpecificZenClassRegistry other) {
        
        this.nativeTypeRegistry.inheritFrom(other.nativeTypeRegistry);
        this.data.inheritFrom(other.view);
    }
    
}
