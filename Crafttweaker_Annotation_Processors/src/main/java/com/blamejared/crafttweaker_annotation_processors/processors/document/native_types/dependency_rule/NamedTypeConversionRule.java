package com.blamejared.crafttweaker_annotation_processors.processors.document.native_types.dependency_rule;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.TypeName;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.TypePageTypeInfo;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;
import org.reflections.Reflections;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class NamedTypeConversionRule implements ModDependencyConversionRule {
    
    private final Reflections reflections;
    private final Elements elements;
    
    public NamedTypeConversionRule(Reflections reflections, Elements elements) {
        
        this.reflections = reflections;
        this.elements = elements;
    }
    
    @Override
    public Map<TypeElement, AbstractTypeInfo> getAll() {
        
        return getClasses().stream().filter(this::isDocumented).collect(createTypeInfoMap());
    }
    
    private Collector<Class<?>, ?, Map<TypeElement, AbstractTypeInfo>> createTypeInfoMap() {
        
        final Function<Class<?>, TypeElement> keyMapper = this::getTypeElementFromClass;
        final Function<Class<?>, AbstractTypeInfo> valueMapper = this::getTypeInfoFromClass;
        
        return Collectors.toMap(keyMapper, valueMapper);
    }
    
    private TypeElement getTypeElementFromClass(Class<?> documentedClass) {
        
        return elements.getTypeElement(documentedClass.getCanonicalName());
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
        
        final ZenCodeType.Name annotation = documentedClass.getAnnotation(ZenCodeType.Name.class);
        return new TypeName(annotation.value());
    }
    
    private String getDocPathFromClass(Class<?> documentedClass) {
        
        return documentedClass.getAnnotation(Document.class).value();
    }
    
    private boolean isDocumented(Class<?> aClass) {
        
        return aClass.isAnnotationPresent(Document.class);
    }
    
    
    private Set<Class<?>> getClasses() {
        
        return reflections.getTypesAnnotatedWith(ZenCodeType.Name.class, true);
    }
    
}
