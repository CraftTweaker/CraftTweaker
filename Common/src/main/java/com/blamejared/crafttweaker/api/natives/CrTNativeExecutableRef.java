package com.blamejared.crafttweaker.api.natives;

import org.openzen.zencode.java.ZenCodeType;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A ref to a constructor or a method,
 */
public class CrTNativeExecutableRef {
    
    private final Class<?>[] arguments;
    private final Map<Class<? extends Annotation>, Annotation> presentAnnotationTypes = new HashMap<>();
    
    CrTNativeExecutableRef(Class<?>[] arguments) {
        
        this.arguments = arguments;
    }
    
    
    <T extends Annotation> void withAnnotation(T annotation) {
        
        this.presentAnnotationTypes.put(annotation.annotationType(), annotation);
    }
    
    void withMethodAnnotation() {
        
        withAnnotation(createMethodAnnotation());
    }
    
    CrTNativeExecutableRef withGetter(String getterName) {
        
        if(!getterName.isEmpty()) {
            withAnnotation(createGetterAnnotation(getterName));
        }
        return this;
    }
    
    CrTNativeExecutableRef withSetter(String setterName) {
        
        if(!setterName.isEmpty()) {
            withAnnotation(createSetterAnnotation(setterName));
        }
        return this;
        
    }
    
    void withConstructorAnnotation() {
        
        withAnnotation(createConstructorAnnotation());
    }
    
    
    @SuppressWarnings("unchecked")
    public <T extends Annotation> Optional<T> getAnnotation(Class<T> annotationClass) {
        
        return Optional.ofNullable((T) presentAnnotationTypes.getOrDefault(annotationClass, null));
    }
    
    boolean matchesParameters(Class<?>[] parameters) {
        
        return Arrays.equals(arguments, parameters);
    }
    
    private ZenCodeType.Method createMethodAnnotation() {
        
        return new ZenCodeType.Method() {
            @Override
            public String value() {
                
                return "";
            }
            
            @Override
            public Class<? extends Annotation> annotationType() {
                
                return ZenCodeType.Method.class;
            }
        };
    }
    
    private ZenCodeType.Constructor createConstructorAnnotation() {
        
        return new ZenCodeType.Constructor() {
            @Override
            public Class<? extends Annotation> annotationType() {
                
                return ZenCodeType.Constructor.class;
            }
        };
    }
    
    private ZenCodeType.Getter createGetterAnnotation(String setterName) {
        
        return new ZenCodeType.Getter() {
            @Override
            public String value() {
                
                return setterName;
            }
            
            @Override
            public Class<? extends Annotation> annotationType() {
                
                return ZenCodeType.Getter.class;
            }
        };
    }
    
    private ZenCodeType.Setter createSetterAnnotation(String setterName) {
        
        return new ZenCodeType.Setter() {
            @Override
            public String value() {
                
                return setterName;
            }
            
            @Override
            public Class<? extends Annotation> annotationType() {
                
                return ZenCodeType.Setter.class;
            }
        };
    }
    
}
