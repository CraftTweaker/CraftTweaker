package com.blamejared.crafttweaker.impl.registry.natives;

import com.blamejared.crafttweaker.api.natives.IExecutableReferenceInfo;
import org.openzen.zencode.java.ZenCodeType;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

final class ExecutableReferenceInfo implements IExecutableReferenceInfo {
    
    static class AnnotationCreator {
        
        private final Map<Class<? extends Annotation>, Annotation> toAdd = new HashMap<>();
        
        <T extends Annotation> void appendAnnotation(T annotation) {
            
            this.toAdd.put(annotation.annotationType(), annotation);
        }
        
        void appendMethodAnnotation() {
            
            this.appendAnnotation(this.createMethodAnnotation());
        }
        
        void appendGetterAnnotation(final String getterName) {
    
            if(getterName.isEmpty()) {
                return;
            }
            this.appendAnnotation(this.createGetterAnnotation(getterName));
        }
        
        void appendSetterAnnotation(final String setterName) {
    
            if(setterName.isEmpty()) {
                return;
            }
            this.appendAnnotation(this.createSetterAnnotation(setterName));
        }
        
        void appendConstructorAnnotation() {
            
            this.appendAnnotation(this.createConstructorAnnotation());
        }
        
        Map<Class<? extends Annotation>, Annotation> toMap(final Map<Class<? extends Annotation>, Annotation> other) {
            
            final Map<Class<? extends Annotation>, Annotation> result = new HashMap<>(other);
            this.toAdd.forEach((k, v) -> {
                if(result.containsKey(k)) {
                    throw new IllegalStateException("Attempted to add the same annotation class " + k + " more than once");
                }
                result.put(k, v);
            });
            return result;
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
        
        private ZenCodeType.Getter createGetterAnnotation(final String setterName) {
            
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
        
        private ZenCodeType.Setter createSetterAnnotation(final String setterName) {
            
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
    
    private final List<Class<?>> arguments;
    private final Map<Class<? extends Annotation>, Annotation> presentAnnotationTypes;
    
    ExecutableReferenceInfo(final List<Class<?>> arguments, final Map<Class<? extends Annotation>, Annotation> presentAnnotationTypes) {
        
        this.arguments = Collections.unmodifiableList(arguments);
        this.presentAnnotationTypes = Collections.unmodifiableMap(presentAnnotationTypes);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Annotation> Optional<T> getAnnotation(final Class<T> annotationClass) {
        
        return Optional.ofNullable(this.presentAnnotationTypes.get(annotationClass)).map(it -> (T) it);
    }
    
    List<Class<?>> arguments() {
        
        return this.arguments;
    }
    
    Map<Class<? extends Annotation>, Annotation> presentAnnotationTypes() {
        
        return this.presentAnnotationTypes;
    }
    
    boolean matchesParameters(final Class<?>... types) {
        
        return Arrays.asList(types).equals(this.arguments);
    }
    
}
