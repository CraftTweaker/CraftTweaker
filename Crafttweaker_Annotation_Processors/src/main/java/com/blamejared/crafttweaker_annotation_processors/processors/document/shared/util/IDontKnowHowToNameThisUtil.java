package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.util;

import com.blamejared.crafttweaker_annotation_processors.processors.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import org.openzen.zencode.java.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.tools.*;
import java.util.*;

public class IDontKnowHowToNameThisUtil {
    
    /**
     * Suppress the "Custom Type without ZenAnnotation used" error for types starting with this name
     */
    private static final Set<String> internalClassNamePrefixes = new HashSet<>();
    
    static {
        internalClassNamePrefixes.add("java.lang");
        internalClassNamePrefixes.add("java.util");
        internalClassNamePrefixes.add("org.openzen");
    }
    
    private IDontKnowHowToNameThisUtil() {
    }
    
    
    /**
     * Returns the path where the documentation md file for this type lies.
     * Logs an error if that type as no ZC Annotations, unless annotated with forComment
     *
     * @param forComment Is this lookup only to resolve a {@code {@link }} then errors will eb suppressed
     * @return The docPath, or {@code null}
     */
    public static String getDocPath(TypeElement element, ProcessingEnvironment environment, boolean forComment) {
        final Document document = element.getAnnotation(Document.class);
        final Messager messager = environment.getMessager();
        
        if(document == null) {
            if(forComment) {
                return null;
            }
            
            if(element.getAnnotation(ZenCodeType.Name.class) != null || element.getAnnotation(ZenCodeType.Expansion.class) != null) {
                messager.printMessage(Diagnostic.Kind.ERROR, "ZenType/Expansion without a Document annotation!", element);
            }
            /*
            //TODO: do we still need this?
            else if(!isInternalClass(element)) {
                
                messager.printMessage(Diagnostic.Kind.ERROR, "Custom Type without Zen annotations used! " + element
                        .getQualifiedName(), element);
            }
            */
            return null;
        }
        
        if(document.value().isEmpty()) {
            final AnnotationMirror mirror = AnnotationMirrorUtil.getMirror(element, Document.class);
            messager.printMessage(Diagnostic.Kind.ERROR, "Empty Doc Path provided!", element, mirror);
        }
        return document.value();
    }
    
    private static boolean isInternalClass(TypeElement element) {
        final String qualifiedName = element.getQualifiedName().toString();
        return internalClassNamePrefixes.stream().anyMatch(qualifiedName::startsWith);
    }
    
}
