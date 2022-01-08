package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.mods;


import com.blamejared.crafttweaker_annotation_processors.processors.util.annotations.AnnotationMirrorUtil;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import java.util.HashMap;
import java.util.Map;

public class KnownModList {
    
    private final Map<String, String> modIdByPackage = new HashMap<>();
    
    private final ProcessingEnvironment environment;
    private final AnnotationMirrorUtil annotationMirrorUtil;
    
    public KnownModList(ProcessingEnvironment environment, AnnotationMirrorUtil annotationMirrorUtil) {
        
        this.environment = environment;
        this.annotationMirrorUtil = annotationMirrorUtil;
    }
    
    public String getModIdForPackage(Element element) {
        
        final PackageElement elementPackage = environment.getElementUtils().getPackageOf(element);
        final String packageName = elementPackage.getQualifiedName().toString();
        
        for(String knownPackName : modIdByPackage.keySet()) {
            if(packageName.startsWith(knownPackName)) {
                return modIdByPackage.get(knownPackName);
            }
        }
        return null;
    }
    
    public void fillModIdInfo(RoundEnvironment roundEnv) {
        
        //TODO not really a thing in fabric
        
        //        final Elements elementUtils = environment.getElementUtils();
        //        final TypeElement typeElement = elementUtils.getTypeElement("net.minecraftforge.fml.common.Mod");
        //
        //        for(Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
        //            final AnnotationMirror modAnnotation = annotationMirrorUtil.getMirror(element, typeElement);
        //            final String modId = annotationMirrorUtil.getAnnotationValue(modAnnotation, "value");
        //
        //            final PackageElement modPackage = elementUtils.getPackageOf(element);
        //            modIdByPackage.put(modPackage.getQualifiedName().toString(), modId);
        //        }
    }
    
}
