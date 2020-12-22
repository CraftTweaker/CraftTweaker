package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.static_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.AbstractEnclosedElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.MemberConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.dependencies.IHasPostCreationCall;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.static_member.DocumentedStaticMembers;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

public class StaticMemberConverter extends MemberConverter<DocumentedStaticMembers> implements IHasPostCreationCall {
    
    private final DependencyContainer dependencyContainer;
    
    public StaticMemberConverter(DependencyContainer dependencyContainer) {
        this.dependencyContainer = dependencyContainer;
    }
    
    @Override
    protected DocumentedStaticMembers createResultObject(DocumentationPageInfo pageInfo) {
        return new DocumentedStaticMembers();
    }
    
    @Override
    protected boolean isCandidate(Element enclosedElement) {
        return enclosedElement.getModifiers().contains(Modifier.STATIC);
    }
    
    @Override
    public void afterCreation() {
        addElementConverters();
    }
    
    private void addElementConverters() {
        addElementConverter(ElementKind.FIELD, StaticFieldConverter.class);
        addElementConverter(ElementKind.METHOD, NamedTypeStaticMethodConverter.class);
    }
    
    private void addElementConverter(ElementKind kind, Class<? extends AbstractEnclosedElementConverter<DocumentedStaticMembers>> converterClass) {
        addElementConverter(kind, dependencyContainer.getInstanceOfClass(converterClass));
    }
}
