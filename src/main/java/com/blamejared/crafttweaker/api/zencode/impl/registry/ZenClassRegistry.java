package com.blamejared.crafttweaker.api.zencode.impl.registry;

import com.blamejared.crafttweaker.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.managers.*;
import net.minecraftforge.fml.*;
import org.objectweb.asm.Type;
import org.openzen.zencode.java.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

public class ZenClassRegistry {
    
    /**
     * All classes with @ZenRegister and their modDeps fulfilled
     */
    private final List<Class<?>> allRegisteredClasses = new ArrayList<>();
    
    /**
     * All classes that have at least one global field
     */
    private final List<Class<?>> zenGlobals = new ArrayList<>();
    
    /**
     * All Classes with @Name, sorted by @Name#value
     */
    private final Map<String, Class<?>> zenClasses = new HashMap<>();
    
    /**
     * All classes with @Expansion, grouped by @Expansion#value
     */
    private final Map<String, List<Class<?>>> expansionsByExpandedName = new HashMap<>();
    
    
    public List<Class<? extends IRecipeManager>> getRecipeManagers() {
        //Cast okay because of isAssignableFrom
        //noinspection unchecked
        return allRegisteredClasses.stream()
                .filter(IRecipeManager.class::isAssignableFrom)
                .filter(cls -> !cls.isInterface())
                .map(cls -> (Class<? extends IRecipeManager>) cls)
                .collect(Collectors.toList());
    }
    
    public List<Class<?>> getAllRegisteredClasses() {
        return allRegisteredClasses;
    }
    
    public List<Class<?>> getZenGlobals() {
        return zenGlobals;
    }
    
    public Map<String, Class<?>> getZenClasses() {
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
            addExpansion(cls);
        }
        
        if(hasGlobals(cls)) {
            zenGlobals.add(cls);
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
    }
    
    private void addExpansion(Class<?> cls) {
        final ZenCodeType.Expansion annotation = cls.getAnnotation(ZenCodeType.Expansion.class);
        if(!expansionsByExpandedName.containsKey(annotation.value())) {
            expansionsByExpandedName.put(annotation.value(), new ArrayList<>());
        }
        expansionsByExpandedName.get(annotation.value()).add(cls);
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
}
