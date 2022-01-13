package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.element;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.function.Function;

public class ClassTypeConverter {
    
    private final Elements elementUtils;
    private final Types types;
    
    public ClassTypeConverter(Elements elementUtils, Types types) {
        
        this.elementUtils = elementUtils;
        this.types = types;
    }
    
    public <T extends Annotation> TypeMirror getTypeMirror(T annotation, Function<T, Class<?>> classGetter) {
        
        try {
            return getNativeTypeFromClass(classGetter.apply(annotation));
        } catch(MirroredTypeException exception) {
            return getFromMirroredTypeException(exception);
        } catch(TypeNotPresentException exception) {
            return getFromTypeNotPresentException(exception);
        }
    }
    
    private TypeMirror getFromTypeNotPresentException(TypeNotPresentException exception) {
        
        final String typeName = exception.typeName().replace('$', '.');
        final TypeElement typeElement = elementUtils.getTypeElement(typeName);
        if(typeElement == null){
            return types.getNullType();
        }
        return typeElement.asType();
    }
    
    private TypeMirror getFromMirroredTypeException(MirroredTypeException exception) {
        
        return exception.getTypeMirror();
    }
    
    private TypeMirror getNativeTypeFromClass(Class<?> cls) {
        
        return elementUtils.getTypeElement(cls.getCanonicalName()).asType();
    }
    
}
