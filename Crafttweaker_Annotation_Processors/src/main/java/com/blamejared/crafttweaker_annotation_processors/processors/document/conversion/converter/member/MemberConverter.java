package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

public abstract class MemberConverter<T> {
    
    private final EnumMap<ElementKind, List<AbstractEnclosedElementConverter<T>>> elementConverters = new EnumMap<>(ElementKind.class);
    private final Elements elementUtils;
    
    protected MemberConverter(Elements elementUtils) {this.elementUtils = elementUtils;}
    
    protected abstract boolean isCandidate(Element enclosedElement);
    
    protected abstract T createResultObject(DocumentationPageInfo pageInfo);
    
    public T convertFor(TypeElement typeElement, DocumentationPageInfo pageInfo) {
        final T result = createResultObject(pageInfo);
    
        final List<Element> enclosedElements = new ArrayList<>(this.elementUtils.getAllMembers(typeElement));
        enclosedElements.removeAll(ElementFilter.typesIn(enclosedElements));
        enclosedElements.removeAll(ElementFilter.packagesIn(enclosedElements));
    
        for(Element enclosedElement : enclosedElements) {
            convertMemberFor(enclosedElement, result, pageInfo);
        }
        
        return result;
    }
    
    private void convertMemberFor(Element enclosedElement, T result, DocumentationPageInfo pageInfo) {
        
        if(!isCandidate(enclosedElement)) {
            return;
        }
        
        final ElementKind kind = enclosedElement.getKind();
        final List<AbstractEnclosedElementConverter<T>> converters = getConvertersFor(kind);
        for(AbstractEnclosedElementConverter<T> converter : converters) {
            if(converter.canConvert(enclosedElement)) {
                converter.convertAndAddTo(enclosedElement, result, pageInfo);
            }
        }
    }
    
    private List<AbstractEnclosedElementConverter<T>> getConvertersFor(ElementKind kind) {
        
        return elementConverters.getOrDefault(kind, Collections.emptyList());
    }
    
    protected void addElementConverter(ElementKind kind, AbstractEnclosedElementConverter<T> expansionMethodConverter) {
        
        elementConverters.computeIfAbsent(kind, ignored -> new ArrayList<>()).add(expansionMethodConverter);
    }
    
}
