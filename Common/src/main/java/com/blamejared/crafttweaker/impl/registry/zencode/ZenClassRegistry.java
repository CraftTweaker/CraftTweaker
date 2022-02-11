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
        
    }
    
    private static final class LoaderSpecificZenClassRegistry {
        
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
                CraftTweakerAPI.LOGGER.error("Duplicate ZenCode Name '{}' in classes '{}' and '{}'", name, other, clazz);
                return;
            }
            
            this.data.classes().put(name, clazz);
            CraftTweakerAPI.LOGGER.debug("Registering '{}' to '{}'", name, clazz);
        }
        
        private void registerZenExpansion(final Class<?> clazz, final String expansionTarget) {
            
            this.data.expansions().put(expansionTarget, clazz);
            CraftTweakerAPI.LOGGER.debug("Registering expansion '{}' to '{}'", clazz, expansionTarget);
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
    
    private LoaderSpecificZenClassRegistry get(final IScriptLoader loader) {
        
        return this.registryMap.getOrDefault(Objects.requireNonNull(loader, "loader"), EMPTY);
    }
    
    /*
    public void addClass(Class<?> cls) {
        
        if(areModsMissing(cls.getAnnotation(ZenRegister.class))) {
            final String canonicalName = cls.getCanonicalName();
            CraftTweakerAPI.LOGGER.debug("Skipping class '{}' since its Mod dependencies are not fulfilled", canonicalName);
            return;
        }
        
        if(isIncompatible(cls)) {
            blacklistedClasses.add(cls);
            return;
        }
        
        allRegisteredClasses.add(cls);
        
        if(cls.isAnnotationPresent(ZenCodeType.Name.class)) {
            addZenClass(cls);
        }
        
        if(cls.isAnnotationPresent(ZenCodeType.Expansion.class)) {
            final String expandedClassName = cls.getAnnotation(ZenCodeType.Expansion.class).value();
            addExpansion(cls, expandedClassName);
        }
        
        if(cls.isAnnotationPresent(TypedExpansion.class)) {
            addTypedExpansion(cls);
        }
        
        if(hasGlobals(cls)) {
            if(cls.isAnnotationPresent(ZenCodeType.Name.class)) {
                addGlobal(cls);
            } else {
                CraftTweakerAPI.LOGGER.warn("Class: '{}' has a Global value, but is missing the '{}' annotation! Please report this to the mod author!", cls, ZenCodeType.Name.class.getName());
            }
        }
    }*/
    
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
    
    /*
    private void addTypedExpansion(Class<?> cls) {
        
        final TypedExpansion annotation = cls.getAnnotation(TypedExpansion.class);
        final Class<?> expandedType = annotation.value();
        
        if(nativeTypeRegistry.hasInfoFor(expandedType)) {
            final String expandedClassName = nativeTypeRegistry.getCrTNameFor(expandedType);
            addExpansion(cls, expandedClassName);
        } else if(expandedType.isAnnotationPresent(ZenCodeType.Name.class)) {
            final ZenCodeType.Name nameAnnotation = expandedType.getAnnotation(ZenCodeType.Name.class);
            final String expandedClassName = nameAnnotation.value();
            addExpansion(cls, expandedClassName);
        } else {
            final String expandedTypeClassName = expandedType.getCanonicalName();
            CraftTweakerAPI.LOGGER.error("Cannot add Expansion for '{}' as the expanded type is not registered!", expandedTypeClassName);
        }
    }
    
    private boolean areModsMissing(ZenRegister register) {
        
        return register == null || !Arrays.stream(register.modDeps())
                .filter(modId -> modId != null && !modId.isEmpty())
                .allMatch(Services.PLATFORM::isModLoaded);
    }
    */
}
