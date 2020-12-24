package com.blamejared.crafttweaker_annotation_processors.processors.document.native_types.dependency_rule;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import org.jetbrains.annotations.*;
import org.reflections.*;

import javax.lang.model.element.*;
import javax.lang.model.util.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class NativeTypeConversionRule implements ModDependencyConversionRule {
    
    private final Reflections reflections;
    private final Elements elementUtils;
    
    public NativeTypeConversionRule(Reflections reflections, Elements elementUtils) {
        this.reflections = reflections;
        this.elementUtils = elementUtils;
    }
    
    @Override
    public Map<TypeElement, AbstractTypeInfo> getAll() {
        return getNativeExpansionClasses().stream().filter(this::isDocumented).collect(createTypeInfoMap());
    }
    
    private Collector<Class<?>, ?, Map<TypeElement, AbstractTypeInfo>> createTypeInfoMap() {
        final Function<Class<?>, TypeElement> keyMapper = this::getTypeElementFromClass;
        final Function<Class<?>, AbstractTypeInfo> valueMapper = this::getTypeInfoFromClass;
        
        return Collectors.toMap(keyMapper, valueMapper);
    }
    
    private TypeElement getTypeElementFromClass(Class<?> documentedClass) {
        final NativeTypeRegistration nativeAnnotation = getNativeAnnotation(documentedClass);
        return elementUtils.getTypeElement(nativeAnnotation.value().getCanonicalName());
    }
    
    private AbstractTypeInfo getTypeInfoFromClass(Class<?> documentedClass) {
        final TypePageInfo pageInfo = getPageInfoFromClass(documentedClass);
        return new TypePageTypeInfo(pageInfo);
    }
    
    @NotNull
    private TypePageInfo getPageInfoFromClass(Class<?> documentedClass) {
        final TypeName typeName = getTypeNameFromClass(documentedClass);
        final String path = getDocPathFromClass(documentedClass);
        
        return new TypePageInfo("unknown", path, typeName);
    }
    
    private TypeName getTypeNameFromClass(Class<?> documentedClass) {
        final NativeTypeRegistration annotation = getNativeAnnotation(documentedClass);
        return new TypeName(annotation.zenCodeName());
    }
    
    private NativeTypeRegistration getNativeAnnotation(Class<?> documentedClass) {
        return documentedClass.getAnnotation(NativeTypeRegistration.class);
    }
    
    private String getDocPathFromClass(Class<?> documentedClass) {
        return documentedClass.getAnnotation(Document.class).value();
    }
    
    private Set<Class<?>> getNativeExpansionClasses() {
        return reflections.getTypesAnnotatedWith(NativeTypeRegistration.class);
    }
    
    private boolean isDocumented(Class<?> nativeRegistrationClass) {
        return nativeRegistrationClass.isAnnotationPresent(Document.class);
    }
}
