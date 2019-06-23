package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.google.common.collect.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.forgespi.language.*;
import org.objectweb.asm.Type;
import org.openzen.zencode.java.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

public class CraftTweakerRegistry {
    
    private static final Type TYPE_ZEN_REGISTER = Type.getType(ZenRegister.class);
    
    private static final List<Class> ZEN_CLASSES = new ArrayList<>();
    private static final List<Class> ZEN_GLOBALS = new ArrayList<>();
    private static final List<Method> BRACKET_RESOLVERS = new ArrayList<>();
    private static final Map<String, Class> ZEN_CLASS_MAP = new HashMap<>();
    
    
    /**
     * Find all classes that have a {@link ZenRegister} annotation and registers them to the class list for loading.
     */
    public static void findClasses() {
        final List<ModFileScanData.AnnotationData> annotations = ModList.get().getAllScanData().stream().map(ModFileScanData::getAnnotations).flatMap(Collection::stream).filter(a -> TYPE_ZEN_REGISTER.equals(a.getAnnotationType())).collect(Collectors.toList());
        for(ModFileScanData.AnnotationData data : annotations) {
            try {
                addClass(data);
            } catch(ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        
        sortClasses();
    }
    
    /**
     * Adds a class to {@link CraftTweakerRegistry#ZEN_CLASSES} if it's required mod deps are found.
     *
     * @param data Scan data for a given class.
     */
    private static void addClass(ModFileScanData.AnnotationData data) throws ClassNotFoundException {
        if(data.getAnnotationData().containsKey("modDeps")) {
            List<String> modOnly = (List<String>) data.getAnnotationData().get("modDeps");
            for(String mod : modOnly) {
                if(mod != null && !mod.isEmpty() && !ModList.get().isLoaded(mod)) {
                    return;
                }
            }
        }
        CraftTweaker.LOG.info("Found ZenRegister: {}", data.getClassType().getClassName());
        ZEN_CLASSES.add(Class.forName(data.getClassType().getClassName(), false, CraftTweaker.class.getClassLoader()));
    }
    
    /**
     * Sorts {@link CraftTweakerRegistry#ZEN_CLASSES} into their respective List/Maps.
     */
    private static void sortClasses() {
        for(Class zenClass : ZEN_CLASSES) {
            if(zenClass.isAnnotationPresent(ZenCodeType.Name.class)) {
                ZenCodeType.Name name = (ZenCodeType.Name) zenClass.getAnnotation(ZenCodeType.Name.class);
                ZEN_CLASS_MAP.put(name.value(), zenClass);
            }
            
            if(hasGlobal(zenClass)) {
                ZEN_GLOBALS.add(zenClass);
            }
            
            for(Method method : zenClass.getDeclaredMethods()) {
                if(!method.isAnnotationPresent(BracketResolver.class)) {
                    continue;
                }
                if(!Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
                    CraftTweakerAPI.logWarning("Method \"%s\" is marked as a BracketResolver, but it is not public and static.", method.toString());
                    continue;
                }
                Class<?>[] parameters = method.getParameterTypes();
                if(parameters.length == 1 && parameters[0].equals(String.class)) {
                    BRACKET_RESOLVERS.add(method);
                } else {
                    CraftTweakerAPI.logWarning("Method \"%s\" is marked as a BracketResolver, but it does not have a String as it's only parameter.", method.toString());
                }
            }
        }
        
        
    }
    
    /**
     * Used to determine if the class has a field / method
     *
     * @param zenClass class to scan
     * @return true if this class has a global annotation on a method.
     */
    private static boolean hasGlobal(Class zenClass) {
        for(Method method : zenClass.getDeclaredMethods()) {
            if(method.isAnnotationPresent(ZenCodeGlobals.Global.class) && Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
                return true;
            }
        }
        
        for(Field field : zenClass.getDeclaredFields()) {
            if(field.isAnnotationPresent(ZenCodeGlobals.Global.class) && Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Gets an ImmutableMap of {@link CraftTweakerRegistry#ZEN_CLASS_MAP}.
     * <p>
     * Classes should not be added manually to this map. See {@link ZenRegister}
     *
     * @return Map of String -> Class for ZenName -> Java class
     */
    public static Map<String, Class> getZenClassMap() {
        return ImmutableMap.copyOf(ZEN_CLASS_MAP);
    }
    
    /**
     * Gets all classes with a given package root.
     * <p>
     * Providing "crafttweaker" as the name can return:
     * <p>
     * {@code "crafttweaker.sub.package.Class}
     * and
     * {@code "crafttweaker.sub.other.package.Class}
     *
     * @param name Name of the Zen Package.
     * @return list of classes in the Zen Package.
     */
    public static List<Class> getClassesInPackage(String name) {
        return ZEN_CLASS_MAP.keySet().stream().filter(key -> key.startsWith(name)).map(ZEN_CLASS_MAP::get).collect(Collectors.toList());
    }
    
    /**
     * Gets all the top level Zen Packages.
     *
     * @return Set of top level packages
     */
    public static Set<String> getRootPackages() {
        return ZEN_CLASS_MAP.keySet().stream().map(s -> s.split("\\.")[0]).collect(Collectors.toSet());
    }
    
    /**
     * Gets the ZenGlobals list.
     *
     * @return ImmutableList of the ZenGlobals
     */
    public static List<Class> getZenGlobals() {
        return ImmutableList.copyOf(ZEN_GLOBALS);
    }
    
    /**
     * Gets the Bracket Resolver list.
     *
     * @return ImmutableList of the Bracket Resolvers
     */
    public static List<Method> getBracketResolvers() {
        return ImmutableList.copyOf(BRACKET_RESOLVERS);
    }
    
}
