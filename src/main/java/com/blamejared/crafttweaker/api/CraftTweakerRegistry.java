package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraftforge.fml.ModList;
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
        modLoop:
        for(ModFileScanData.AnnotationData data : annotations) {
            List<String> modOnly = (List<String>) data.getAnnotationData().get("modDeps");
            for(String mod : modOnly) {
                if(mod != null && !mod.isEmpty() && !ModList.get().isLoaded(mod)) {
                    continue modLoop;
                }
            }
            CraftTweaker.LOG.info("Found ZenRegister: {}", data.getClass().getName());
            ZEN_CLASSES.add(data.getClass());
        }
    }
    
    
}
