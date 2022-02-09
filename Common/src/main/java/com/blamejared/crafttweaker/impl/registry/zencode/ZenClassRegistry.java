package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.natives.NativeTypeRegistry;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
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
        
        private NativeTypeRegistry getNativeTypeRegistry() {
            
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
    public NativeTypeRegistry getNativeTypeRegistry(final IScriptLoader loader) {
        
        return this.get(loader).getNativeTypeRegistry();
    }
    
    @Override
    public boolean isBlacklisted(final Class<?> cls) {
        
        return this.blacklistedClasses.contains(cls);
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
    
    private void addNativeAnnotation(Class<?> cls) {
        
        final NativeTypeRegistration annotation = cls.getAnnotation(NativeTypeRegistration.class);
        final String zenCodeName = annotation.zenCodeName();
        nativeTypeRegistry.addNativeType(annotation, cls.getAnnotationsByType(NativeMethod.class));
        addExpansion(cls, zenCodeName);
    }
    
    private boolean areModsMissing(ZenRegister register) {
        
        return register == null || !Arrays.stream(register.modDeps())
                .filter(modId -> modId != null && !modId.isEmpty())
                .allMatch(Services.PLATFORM::isModLoaded);
    }
    
    private void addZenClass(Class<?> cls) {
        
        final ZenCodeType.Name annotation = cls.getAnnotation(ZenCodeType.Name.class);
        final String name = annotation.value();
        if(zenClasses.containsKey(name)) {
            final Class<?> otherCls = zenClasses.get(name);
            CraftTweakerAPI.LOGGER.error("Duplicate ZenCode Name '{}' in classes '{}' and '{}'", name, otherCls, cls);
        }
        
        zenClasses.put(name, cls);
        CraftTweakerAPI.LOGGER.debug("Registering '{}'", name);
    }
    
    private void addGlobal(Class<?> cls) {
        
        final ZenCodeType.Name annotation = cls.getAnnotation(ZenCodeType.Name.class);
        zenGlobals.put(annotation.value(), cls);
    }
    
    private void addExpansion(Class<?> cls, String expandedClassName) {
        
        if(!expansionsByExpandedName.containsKey(expandedClassName)) {
            expansionsByExpandedName.put(expandedClassName, new ArrayList<>());
        }
        expansionsByExpandedName.get(expandedClassName).add(cls);
    }
    
    private boolean hasGlobals(Class<?> cls) {
        
        return Stream.concat(Arrays.stream(cls.getFields()), Arrays.stream(cls.getMethods()))
                .filter(member -> member.isAnnotationPresent(ZenCodeGlobals.Global.class))
                .filter(member -> Modifier.isPublic(member.getModifiers()))
                .anyMatch(member -> Modifier.isStatic(member.getModifiers()));
    }
    */
    
    /*
    public void addNativeType(Class<?> cls) {
        
        if(areModsMissing(cls.getAnnotation(ZenRegister.class))) {
            return;
        }
        
        if(cls.isAnnotationPresent(NativeTypeRegistration.class)) {
            addNativeAnnotation(cls);
        }
    }
    
    public void initNativeTypes() {
        
        for(CrTNativeTypeInfo nativeTypeInfo : nativeTypeRegistry.getNativeTypeInfos()) {
            final String craftTweakerName = nativeTypeInfo.getCraftTweakerName();
            final String vanillaClass = nativeTypeInfo.getVanillaClass().getCanonicalName();
            
            zenClasses.put(craftTweakerName, nativeTypeInfo.getVanillaClass());
            CraftTweakerAPI.LOGGER.debug("Registering {} for native type '{}'", craftTweakerName, vanillaClass);
        }
    }*/
    
}
