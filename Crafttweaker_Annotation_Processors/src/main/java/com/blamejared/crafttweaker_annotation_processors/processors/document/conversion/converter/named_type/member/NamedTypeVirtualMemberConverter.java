package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.named_type.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.MemberConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.DocumentedTypeVirtualMembers;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

public class NamedTypeVirtualMemberConverter extends MemberConverter<DocumentedTypeVirtualMembers> {
    
    public NamedTypeVirtualMemberConverter(DependencyContainer dependencyContainer) {
        addConverters(dependencyContainer);
    }
    
    @Override
    protected boolean isCandidate(Element enclosedElement) {
        return isVirtual(enclosedElement);
    }
    
    @Override
    protected DocumentedTypeVirtualMembers createResultObject(DocumentationPageInfo pageInfo) {
        return new DocumentedTypeVirtualMembers();
    }
    
    private void addConverters(DependencyContainer dependencyContainer) {
        addMethodConverters(dependencyContainer);
        addFieldConverters(dependencyContainer);
        addConstructorConverters(dependencyContainer);
    }
    
    private void addMethodConverters(DependencyContainer dependencyContainer) {
        addElementConverter(ElementKind.METHOD, dependencyContainer.getInstanceOfClass(NamedTypeVirtualMethodConverter.class));
        addElementConverter(ElementKind.METHOD, dependencyContainer.getInstanceOfClass(NamedTypeVirtualCasterConverter.class));
        addElementConverter(ElementKind.METHOD, dependencyContainer.getInstanceOfClass(NamedTypeVirtualGetterSetterConverter.class));
        addElementConverter(ElementKind.METHOD, dependencyContainer.getInstanceOfClass(NamedTypeVirtualOperatorConverter.class));
    }
    
    private void addFieldConverters(DependencyContainer dependencyContainer) {
        addElementConverter(ElementKind.FIELD, dependencyContainer.getInstanceOfClass(NamedTypeVirtualFieldConverter.class));
    }
    
    private void addConstructorConverters(DependencyContainer dependencyContainer) {
        addElementConverter(ElementKind.CONSTRUCTOR, dependencyContainer.getInstanceOfClass(NamedTypeVirtualConstructorConverter.class));
    }
    
    private boolean isVirtual(Element enclosedElement) {
        return !enclosedElement.getModifiers().contains(Modifier.STATIC);
    }
}
