package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public abstract class MemberConverter<T> {
    
    private final EnumMap<ElementKind, List<AbstractEnclosedElementConverter<?>>> elementConverters = new EnumMap<>(ElementKind.class);
    
    protected abstract boolean isCandidate(Element enclosedElement);
    
    protected abstract T createResultObject();
    
    public T convertFor(TypeElement typeElement, DocumentationPageInfo pageInfo) {
        final T result = createResultObject();
        for(Element enclosedElement : typeElement.getEnclosedElements()) {
            convertMemberFor(enclosedElement, result, pageInfo);
        }
        
        return result;
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void convertMemberFor(Element enclosedElement, T result, DocumentationPageInfo pageInfo) {
        if(!isCandidate(enclosedElement)) {
            return;
        }
        
        final ElementKind kind = enclosedElement.getKind();
        final List<AbstractEnclosedElementConverter<?>> converters = elementConverters.get(kind);
        for(AbstractEnclosedElementConverter converter : converters) {
            if(converter.canConvert(enclosedElement)) {
                converter.convertAndAddTo(enclosedElement, result, pageInfo);
                return;
            }
        }
    }
    
    protected void addElementConverter(ElementKind kind, AbstractEnclosedElementConverter<?> expansionMethodConverter) {
        elementConverters.computeIfAbsent(kind, ignored -> new ArrayList<>())
                .add(expansionMethodConverter);
    }
}
