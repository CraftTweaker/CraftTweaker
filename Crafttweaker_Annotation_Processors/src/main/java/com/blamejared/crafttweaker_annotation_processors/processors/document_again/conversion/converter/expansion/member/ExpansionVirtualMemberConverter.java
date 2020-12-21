package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.expansion.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.MemberConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.DocumentedVirtualMembers;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

public class ExpansionVirtualMemberConverter extends MemberConverter<DocumentedVirtualMembers> {
    
    public ExpansionVirtualMemberConverter(DependencyContainer dependencyContainer) {
        //TODO: Add converters
        addElementConverter(ElementKind.METHOD, dependencyContainer.getInstanceOfClass(ExpansionMethodConverter.class));
        addElementConverter(ElementKind.METHOD, dependencyContainer.getInstanceOfClass(ExpansionCasterConverter.class));
        addElementConverter(ElementKind.METHOD, dependencyContainer.getInstanceOfClass(ExpansionGetterSetterConverter.class));
    }
    
    @Override
    protected DocumentedVirtualMembers createResultObject(DocumentationPageInfo pageInfo) {
        return new DocumentedVirtualMembers();
    }
    
    @Override
    protected boolean isCandidate(Element enclosedElement) {
        return enclosedElement.getModifiers().contains(Modifier.STATIC);
    }
}
