package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info;

import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.IHasPostCreationCall;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import org.openzen.zencode.java.ZenCodeType;
import org.reflections.Reflections;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KnownTypeRegistry implements IHasPostCreationCall {
    
    private final Elements elementUtils;
    private final Reflections reflections;
    
    private final Collection<TypeElement> namedTypes = new HashSet<>();
    private final Collection<TypeElement> nativeTypes = new HashSet<>();
    private final Collection<TypeElement> expansionTypes = new HashSet<>();
    private final Collection<TypeElement> typedExpansionTypes = new HashSet<>();
    
    private final Collection<TypeElement> nativeTypesFromDependencies = new HashSet<>();
    private final Collection<TypeElement> namedTypesFromDependencies = new HashSet<>();
    
    public KnownTypeRegistry(Elements elementUtils, Reflections reflections) {
        this.elementUtils = elementUtils;
        this.reflections = reflections;
    }
    
    
    public void addNamedTypes(Set<? extends Element> elements) {
        addTypeElementsTo(elements, namedTypes);
        
    }
    
    public void addNativeTypes(Set<? extends Element> elements) {
        addTypeElementsTo(elements, nativeTypes);
    }
    
    public void addExpansionTypes(Set<? extends Element> elements) {
        addTypeElementsTo(elements, expansionTypes);
    }
    
    public void addTypedExpansionTypes(Set<? extends Element> elements) {
        addTypeElementsTo(elements, typedExpansionTypes);
    }
    
    private void addTypeElementsTo(Set<? extends Element> elements, Collection<TypeElement> result) {
        elements.stream().map(element -> (TypeElement) element).forEach(result::add);
    }
    
    private void update(Collection<TypeElement> toUpdate) {
        final Set<TypeElement> newList = toUpdate.stream()
                .map(Object::toString)
                .map(elementUtils::getTypeElement)
                .collect(Collectors.toSet());
        
        toUpdate.clear();
        toUpdate.addAll(newList);
    }
    
    public Collection<TypeElement> getNamedTypes() {
        update(namedTypes);
        return namedTypes;
    }
    
    public Collection<TypeElement> getNativeTypes() {
        update(nativeTypes);
        return nativeTypes;
    }
    
    public Collection<TypeElement> getNativeTypesFromDependencies() {
        update(nativeTypesFromDependencies);
        return nativeTypesFromDependencies;
    }
    
    public Collection<TypeElement> getNamedTypesFromDependencies() {
        update(namedTypesFromDependencies);
        return namedTypesFromDependencies;
    }
    
    public Stream<TypeElement> getAllNativeTypes() {
        return Stream.concat(getNativeTypes().stream(), getNativeTypesFromDependencies().stream());
    }
    
    public Stream<TypeElement> getAllNamedTypes() {
        return Stream.concat(getNamedTypes().stream(), getNamedTypesFromDependencies().stream());
    }
    
    public Collection<TypeElement> getExpansionTypes() {
        update(expansionTypes);
        return expansionTypes;
    }
    
    public Collection<TypeElement> getTypedExpansionTypes() {
        update(typedExpansionTypes);
        return typedExpansionTypes;
    }
    
    @Override
    public void afterCreation() {
        initTypesFromDependencies();
    }
    
    private void initTypesFromDependencies() {
        initTypesFromDependencies(ZenCodeType.Name.class, namedTypesFromDependencies);
        initTypesFromDependencies(NativeTypeRegistration.class, nativeTypesFromDependencies);
    }
    
    private void initTypesFromDependencies(Class<? extends Annotation> annotationClass, Collection<TypeElement> resultCollection) {
        final List<TypeElement> result = reflections.getTypesAnnotatedWith(annotationClass)
                .stream()
                .map(Class::getCanonicalName)
                .map(elementUtils::getTypeElement)
                .collect(Collectors.toList());
        
        resultCollection.addAll(result);
    }
}
