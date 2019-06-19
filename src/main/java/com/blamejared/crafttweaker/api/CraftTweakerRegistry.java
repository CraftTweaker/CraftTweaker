package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraftforge.fml.*;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.util.*;
import java.util.stream.Collectors;

public class CraftTweakerRegistry {
    
    private static final List<Class> ZEN_CLASSES = new ArrayList<>();
    private static final Type TYPE_ZEN_REGISTER = Type.getType(ZenRegister.class);
    
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
    }
    
    /**
     * Adds a class to {@link CraftTweakerRegistry#ZEN_CLASSES} if it's required mod deps are found
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
    
    
}
