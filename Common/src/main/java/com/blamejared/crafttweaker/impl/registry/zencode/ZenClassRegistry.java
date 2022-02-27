package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.natives.INativeTypeRegistry;
import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
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
import org.openzen.zenscript.codemodel.Modifiers;
import org.openzen.zenscript.codemodel.type.BasicTypeID;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ZenClassRegistry implements IZenClassRegistry {
    
    public record ClassData(List<Class<?>> registeredClasses,
                            BiMap<String, Class<?>> globals,
                            BiMap<String, Class<?>> classes,
                            Multimap<String, Class<?>> expansions
    ) implements IZenClassRegistry.IClassData {
        
        private ClassData() {
            
            this(new ArrayList<>(), HashBiMap.create(), HashBiMap.create(), HashMultimap.create());
        }
        
        @SuppressWarnings("CopyConstructorMissesField") // It is not a copy constructor, IntelliJ...
        private ClassData(final ClassData view) {
            
            this(
                    Collections.unmodifiableList(view.registeredClasses()),
                    Maps.unmodifiableBiMap(view.globals()),
                    Maps.unmodifiableBiMap(view.classes()),
                    Multimaps.unmodifiableMultimap(view.expansions())
            );
        }
        
        private void inheritFrom(final ClassData other) {
            
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
    
    private static final class LoaderSpecificZenClassRegistry {
        
        private static final Supplier<Set<String>> BUILTIN_TYPES = Suppliers.memoize(
                () -> Arrays.stream(BasicTypeID.values()).map(Object::toString).collect(Collectors.toUnmodifiableSet())
        );
        
        private final NativeTypeRegistry nativeTypeRegistry = new NativeTypeRegistry();
        private final ClassData data = new ClassData();
        private final Supplier<ClassData> view = Suppliers.memoize(() -> new ClassData(this.data));
        
        private boolean isRegistered(final Class<?> clazz) {
            
            return this.data.classes().containsValue(clazz);
        }
        
        private Optional<String> getNameFor(final Class<?> clazz) {
            
            return Optional.ofNullable(this.data.classes().inverse().get(clazz));
        }
        
        @SuppressWarnings("unchecked")
        private <T> List<Class<? extends T>> getImplementationsOf(final Class<T> checkFor) {
            //Cast okay because of isAssignableFrom
            return this.data.registeredClasses()
                    .stream()
                    .filter(checkFor::isAssignableFrom)
                    .filter(cls -> !cls.isInterface() && !Modifier.isAbstract(cls.getModifiers()))
                    .map(cls -> (Class<? extends T>) cls)
                    .collect(Collectors.toList());
        }
        
        private IClassData getImmutableDataView() {
            
            return this.view.get();
        }
        
        private INativeTypeRegistry getNativeTypeRegistry() {
            
            return this.nativeTypeRegistry;
        }
        
        private List<Class<?>> getClassesInPackage(final String packageName) {
            
            return this.data.classes()
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().startsWith(packageName))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }
        
        private List<Class<?>> getGlobalsInPackage(final String packageName) {
            
            return this.data.globals()
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().startsWith(packageName))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }
        
        private Set<String> getRootPackages() {
            
            return this.data.classes()
                    .keySet()
                    .stream()
                    .map(key -> key.split("\\.", 2)[0])
                    .collect(Collectors.toSet());
        }
        
        private void registerNativeType(final NativeTypeInfo info) {
            
            if(this == EMPTY) {
                throw new UnsupportedOperationException();
            }
            
            this.nativeTypeRegistry.addNativeType(info);
            this.data.classes().put(info.name(), info.targetedType());
            CraftTweakerAPI.LOGGER.debug("Registering {} for native type '{}'", info.name(), info.targetedType()
                    .getName());
        }
        
        private void registerZenType(final Class<?> clazz, final ZenTypeInfo info, final boolean globals) {
            
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
        
        private void registerZenClass(final Class<?> clazz, final String name) {
            
            final Class<?> other = this.data.classes().get(name);
            if(other != null) {
                CraftTweakerAPI.LOGGER.error("Duplicate ZenCode Name '{}' in classes '{}' and '{}'", name, other.getName(), clazz.getName());
                return;
            }
            
            this.data.classes().put(name, clazz);
            CraftTweakerAPI.LOGGER.debug("Registering '{}' to '{}'", name, clazz);
        }
        
        private void registerZenExpansion(final Class<?> clazz, final String expansionTarget) {
            
            if(!this.knowsType(expansionTarget)) {
                CraftTweakerAPI.LOGGER.warn("Attempting to register expansion for unknown type '{}', carrying on anyways", expansionTarget);
            }
            this.data.expansions().put(expansionTarget, clazz);
            CraftTweakerAPI.LOGGER.debug("Registering expansion '{}' to '{}'", clazz.getName(), expansionTarget);
        }
        
        private void registerGlobals(final Class<?> clazz, final ZenTypeInfo info) {
            
            if(info.kind() == ZenTypeInfo.TypeKind.EXPANSION) {
                throw new IllegalArgumentException("Cannot register globals for an expansion");
            }
            
            boolean hasGlobals = Stream.concat(Arrays.stream(clazz.getDeclaredFields()), Arrays.stream(clazz.getDeclaredMethods()))
                    .filter(it -> it.isAnnotationPresent(ZenCodeGlobals.Global.class))
                    .mapToInt(Member::getModifiers)
                    .anyMatch(it -> Modifiers.isPublic(it) && Modifiers.isStatic(it));
            
            if(hasGlobals) {
                this.data.globals().put(info.targetName(), clazz);
            }
        }
        
        private boolean knowsType(final String expansionTarget) {
            
            if(expansionTarget.endsWith("[]")) { // Array
                return this.knowsType(expansionTarget.substring(0, expansionTarget.length() - 2));
            }
            if(expansionTarget.contains("<")) { // Generic
                final String[] split = expansionTarget.split(Pattern.quote("<"), 2);
                return this.knowsType(split[0]) && this.knowsType(split[1].substring(0, split[1].length() - 1));
            }
            if(BUILTIN_TYPES.get().contains(expansionTarget)) {
                return true;
            }
            return this.data.classes().containsKey(expansionTarget);
        }
        
        private void inheritFrom(final Collection<LoaderSpecificZenClassRegistry> registries) {
            
            if(this == EMPTY) {
                throw new UnsupportedOperationException();
            }
            registries.forEach(this::inheritFrom);
        }
        
        private void inheritFrom(final LoaderSpecificZenClassRegistry other) {
            
            this.nativeTypeRegistry.inheritFrom(other.nativeTypeRegistry);
            this.data.inheritFrom(other.view.get());
        }
        
    }
    
    private static final LoaderSpecificZenClassRegistry EMPTY = new LoaderSpecificZenClassRegistry();
    private final Set<Class<?>> blacklistedClasses = new HashSet<>();
    private final Map<IScriptLoader, LoaderSpecificZenClassRegistry> registryMap = new HashMap<>();
    
    @Override
    public boolean isRegistered(final IScriptLoader loader, final Class<?> clazz) {
        
        return this.get(loader).isRegistered(clazz);
    }
    
    @Override
    public Optional<String> getNameFor(final IScriptLoader loader, final Class<?> clazz) {
        
        return this.get(loader).getNameFor(clazz);
    }
    
    @Override
    public <T> List<Class<? extends T>> getImplementationsOf(final IScriptLoader loader, final Class<T> checkFor) {
        
        return this.get(loader).getImplementationsOf(checkFor);
    }
    
    @Override
    public IClassData getClassData(final IScriptLoader loader) {
        
        return this.get(loader).getImmutableDataView();
    }
    
    @Override
    public List<Class<?>> getClassesInPackage(final IScriptLoader loader, final String packageName) {
        
        return this.get(loader).getClassesInPackage(packageName);
    }
    
    @Override
    public List<Class<?>> getGlobalsInPackage(final IScriptLoader loader, final String packageName) {
        
        return this.get(loader).getGlobalsInPackage(packageName);
    }
    
    @Override
    public Set<String> getRootPackages(final IScriptLoader loader) {
        
        return this.get(loader).getRootPackages();
    }
    
    @Override
    public INativeTypeRegistry getNativeTypeRegistry(final IScriptLoader loader) {
        
        return this.get(loader).getNativeTypeRegistry();
    }
    
    @Override
    public boolean isBlacklisted(final Class<?> cls) {
        
        return this.blacklistedClasses.contains(cls);
    }
    
    public void fillLoaderData(final Collection<IScriptLoader> loaders) {
        
        loaders.forEach(it -> this.registryMap.put(it, new LoaderSpecificZenClassRegistry()));
    }
    
    public void registerNativeType(final IScriptLoader loader, final NativeTypeInfo info) {
        
        this.get(loader).registerNativeType(info);
    }
    
    public void registerZenType(final IScriptLoader loader, final Class<?> clazz, final ZenTypeInfo info, final boolean globals) {
        
        if(this.isIncompatible(clazz)) {
            this.blacklistedClasses.add(clazz);
            return;
        }
        
        this.get(loader).registerZenType(clazz, info, globals);
    }
    
    public void applyInheritanceRules() {
        
        List.copyOf(this.registryMap.keySet()).forEach(loader -> {
            try {
                final LoaderSpecificZenClassRegistry loaderData = this.get(loader);
                final Collection<LoaderSpecificZenClassRegistry> inheritedData = loader.inheritedLoaders()
                        .stream()
                        .map(this::get)
                        .toList();
                loaderData.inheritFrom(inheritedData);
            } catch(final Exception e) {
                throw new IllegalStateException("Unable to apply inheritance rules for loader " + loader.name());
            }
        });
    }
    
    private LoaderSpecificZenClassRegistry get(final IScriptLoader loader) {
        
        return this.registryMap.getOrDefault(Objects.requireNonNull(loader, "loader"), EMPTY);
    }
    
    /**
     * Checks that the class does not have any fields or methods that would cause errors when converting in the JavaNativeModule.
     * Does so by simply calling the declaredMethods and fields getters.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private boolean isIncompatible(final Class<?> cls) {
        
        try {
            cls.getDeclaredFields();
            cls.getFields();
            cls.getDeclaredMethods();
            cls.getMethods();
            cls.getConstructors();
            cls.getDeclaredConstructors();
            return false;
        } catch(Throwable t) {
            CraftTweakerAPI.LOGGER.error("Could not register class '{}'! This is most likely a compatibility issue!", cls
                    .getCanonicalName(), t);
            return true;
        }
    }
    
}
