package com.blamejared.crafttweaker_annotation_processors.processors;

import com.blamejared.crafttweaker_annotations.annotations.*;
import org.reflections.*;
import org.reflections.util.*;

import java.util.*;

public class ReflectionReader {
    private ReflectionReader(){}
    
    private static Set<Class<?>> scannedClasses = null;
    
    public static Set<Class<?>> getClassesWithZenWrapper(ClassLoader loader) {
        if(scannedClasses == null) {
            final ConfigurationBuilder configuration = new ConfigurationBuilder()
                    .addUrls(ClasspathHelper.forJavaClassPath())
                    .addClassLoaders(ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader(), loader)
                    .addUrls(ClasspathHelper.forClassLoader());
            scannedClasses = new Reflections(configuration).getTypesAnnotatedWith(ZenWrapper.class, true);
        }
        
        return scannedClasses;
    }
}
