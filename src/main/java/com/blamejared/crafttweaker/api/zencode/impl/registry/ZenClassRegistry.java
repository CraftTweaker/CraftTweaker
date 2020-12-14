package com.blamejared.crafttweaker.api.zencode.impl.registry;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.NativeExpansion;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.native_types.CrTNativeTypeInfo;
import com.blamejared.crafttweaker.impl.native_types.CrTNativeTypeRegistration;
import com.blamejared.crafttweaker.impl.native_types.NativeTypeRegistry;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraftforge.fml.ModList;
import org.objectweb.asm.Type;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ZenClassRegistry {
    
    private final NativeTypeRegistry nativeTypeRegistry = new NativeTypeRegistry();
    
    /**
     * All classes with @ZenRegister and their modDeps fulfilled
     */
    private final List<Class<?>> allRegisteredClasses = new ArrayList<>();
    
    /**
     * All classes that have at least one global field
     */
    private final List<Class<?>> zenGlobals = new ArrayList<>();
    
    /**
     * All Classes with @Name, key is @Name#value
     */
    private final BiMap<String, Class<?>> zenClasses = HashBiMap.create();
    
    /**
     * All classes with @Expansion, grouped by @Expansion#value
     */
    private final Map<String, List<Class<?>>> expansionsByExpandedName = new HashMap<>();
    
    
    public List<Class<? extends IRecipeManager>> getRecipeManagers() {
        return getImplementationsOf(IRecipeManager.class);
    }
    
    public boolean isRegistered(Class<?> cls) {
        return zenClasses.inverse().containsKey(cls);
    }
    
    public String getNameFor(Class<?> cls) {
        return zenClasses.inverse().get(cls);
    }
    
    public Optional<String> tryGetNameFor(Class<?> cls) {
        if(isRegistered(cls)) {
            return Optional.ofNullable(getNameFor(cls));
        }
        return Optional.empty();
    }
    
    public <T> List<Class<? extends T>> getImplementationsOf(Class<T> checkFor) {
        //Cast okay because of isAssignableFrom
        //noinspection unchecked
        return allRegisteredClasses.stream()
                .filter(checkFor::isAssignableFrom)
                .filter(cls -> !cls.isInterface() && !Modifier.isAbstract(cls.getModifiers()))
                .map(cls -> (Class<? extends T>) cls)
                .collect(Collectors.toList());
    }
    
    public List<Class<?>> getAllRegisteredClasses() {
        return allRegisteredClasses;
    }
    
    public List<Class<?>> getZenGlobals() {
        return zenGlobals;
    }
    
    public BiMap<String, Class<?>> getZenClasses() {
        return zenClasses;
    }
    
    public Map<String, List<Class<?>>> getExpansionsByExpandedName() {
        return expansionsByExpandedName;
    }
    
    public void addType(Type type) {
        try {
            addClass(Class.forName(type.getClassName(), false, CraftTweaker.class.getClassLoader()));
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void addClass(Class<?> cls) {
        if(!areModsPresent(cls.getAnnotation(ZenRegister.class))) {
            return;
        }
        
        allRegisteredClasses.add(cls);
        CraftTweaker.LOG.debug("Found ZenRegister: {}", cls.getCanonicalName());
        
        if(cls.isAnnotationPresent(ZenCodeType.Name.class)) {
            addZenClass(cls);
        }
        
        if(cls.isAnnotationPresent(ZenCodeType.Expansion.class)) {
            final String expandedClassName = cls.getAnnotation(ZenCodeType.Expansion.class).value();
            addExpansion(cls, expandedClassName);
        }
        
        if(cls.isAnnotationPresent(NativeExpansion.class)) {
            addNativeAnnotation(cls);
        }
        
        if(hasGlobals(cls)) {
            zenGlobals.add(cls);
        }
    }
    
    private void addNativeAnnotation(Class<?> cls) {
        final NativeExpansion annotation = cls.getAnnotation(NativeExpansion.class);
        final Class<?> expandedNativeType = annotation.value();
        if(nativeTypeRegistry.hasInfoFor(expandedNativeType)) {
            final String expandedClassName = nativeTypeRegistry.getCrTNameFor(expandedNativeType);
            addExpansion(cls, expandedClassName);
        } else if(expandedNativeType.isAnnotationPresent(ZenCodeType.Name.class)) {
            final String expandedClassName = expandedNativeType.getAnnotation(ZenCodeType.Name.class)
                    .value();
            addExpansion(cls, expandedClassName);
        } else {
            CraftTweakerAPI.logError("Cannot add Expansion for '%s' as it's not registered as native type!", expandedNativeType
                    .getCanonicalName());
        }
    }
    
    private boolean areModsPresent(ZenRegister register) {
        return register != null && Arrays.stream(register.modDeps())
                .filter(modId -> modId != null && !modId.isEmpty())
                .allMatch(ModList.get()::isLoaded);
    }
    
    private void addZenClass(Class<?> cls) {
        final ZenCodeType.Name annotation = cls.getAnnotation(ZenCodeType.Name.class);
        zenClasses.put(annotation.value(), cls);
        CraftTweakerAPI.logDebug("Registering %s", annotation.value());
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
    
    public List<Class<?>> getClassesInPackage(String name) {
        return zenClasses.entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(name))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
    
    public Set<String> getRootPackages() {
        return zenClasses.keySet()
                .stream()
                .map(key -> key.split("\\.", 2)[0])
                .collect(Collectors.toSet());
    }
    
    public void initNativeTypes() {
        CrTNativeTypeRegistration.registerNativeTypes(nativeTypeRegistry);
        for(CrTNativeTypeInfo nativeTypeInfo : nativeTypeRegistry.getNativeTypeInfos()) {
            final String craftTweakerName = nativeTypeInfo.getCraftTweakerName();
            final String vanillaClass = nativeTypeInfo.getVanillaClass().getCanonicalName();
            
            zenClasses.put(craftTweakerName, nativeTypeInfo.getVanillaClass());
            CraftTweakerAPI.logDebug("Registering %s for native type '%s'", craftTweakerName, vanillaClass);
        }
    }
    
    public NativeTypeRegistry getNativeTypeRegistry() {
        return nativeTypeRegistry;
    }
}
