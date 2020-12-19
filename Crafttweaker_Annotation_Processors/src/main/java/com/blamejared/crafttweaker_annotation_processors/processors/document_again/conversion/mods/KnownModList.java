package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.mods;

import com.blamejared.crafttweaker_annotation_processors.processors.AnnotationMirrorUtil;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.HashMap;
import java.util.Map;

public class KnownModList {
    
    private final ProcessingEnvironment environment;
    private final Map<String, String> modIdByPackage = new HashMap<>();
    
    public KnownModList(ProcessingEnvironment environment) {
        this.environment = environment;
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
        final Elements elementUtils = environment.getElementUtils();
        final TypeElement typeElement = elementUtils.getTypeElement("net.minecraftforge.fml.common.Mod");
        
        for(Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
            final AnnotationMirror modAnnotation = AnnotationMirrorUtil.getMirror(element, typeElement);
            final String modId = AnnotationMirrorUtil.getAnnotationValue(modAnnotation, "value");
            
            final PackageElement modPackage = elementUtils.getPackageOf(element);
            modIdByPackage.put(modPackage.getQualifiedName().toString(), modId);
        }
    }
}
