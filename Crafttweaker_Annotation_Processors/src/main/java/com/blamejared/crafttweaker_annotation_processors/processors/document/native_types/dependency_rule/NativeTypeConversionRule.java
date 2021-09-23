package com.blamejared.crafttweaker_annotation_processors.processors.document.native_types.dependency_rule;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.element.ClassTypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.TypeName;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.TypePageTypeInfo;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class NativeTypeConversionRule implements ModDependencyConversionRule {
    
    private final Reflections reflections;
    private final Types typeUtils;
    private final ClassTypeConverter classTypeConverter;
    
    public NativeTypeConversionRule(Reflections reflections, Types typeUtils, ClassTypeConverter classTypeConverter) {
        
        this.reflections = reflections;
        this.typeUtils = typeUtils;
        this.classTypeConverter = classTypeConverter;
    }
    
    @Override
    public Map<TypeElement, AbstractTypeInfo> getAll() {
        
        return getNativeExpansionClasses().stream()
                .filter(this::isDocumented)
                .collect(createTypeInfoMap());
    }
    
    private Collector<Class<?>, ?, Map<TypeElement, AbstractTypeInfo>> createTypeInfoMap() {
        
        final Function<Class<?>, TypeElement> keyMapper = this::getTypeElementFromClass;
        final Function<Class<?>, AbstractTypeInfo> valueMapper = this::getTypeInfoFromClass;
        
        return Collectors.toMap(keyMapper, valueMapper);
    }
    
    private TypeElement getTypeElementFromClass(Class<?> documentedClass) {
        
        final NativeTypeRegistration nativeAnnotation = getNativeAnnotation(documentedClass);
        final TypeMirror nativeType = classTypeConverter.getTypeMirror(nativeAnnotation, NativeTypeRegistration::value);
        
        return (TypeElement) typeUtils.asElement(nativeType);
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
