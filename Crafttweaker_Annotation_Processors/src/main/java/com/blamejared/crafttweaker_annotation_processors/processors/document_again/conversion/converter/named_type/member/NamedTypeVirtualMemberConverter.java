package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.named_type.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.MemberConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.DocumentedTypeVirtualMembers;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

public class NamedTypeVirtualMemberConverter extends MemberConverter<DocumentedTypeVirtualMembers> {
    
    public NamedTypeVirtualMemberConverter(DependencyContainer dependencyContainer) {
        //TODO: Add converters
        addElementConverter(ElementKind.METHOD, dependencyContainer.getInstanceOfClass(NamedTypeVirtualMethodConverter.class));
    }
    
    @Override
    protected boolean isCandidate(Element enclosedElement) {
        return isVirtual(enclosedElement);
    }
    
    private boolean isVirtual(Element enclosedElement) {
        return !enclosedElement.getModifiers().contains(Modifier.STATIC);
    }
    
    @Override
    protected DocumentedTypeVirtualMembers createResultObject() {
        return new DocumentedTypeVirtualMembers();
    }
}
