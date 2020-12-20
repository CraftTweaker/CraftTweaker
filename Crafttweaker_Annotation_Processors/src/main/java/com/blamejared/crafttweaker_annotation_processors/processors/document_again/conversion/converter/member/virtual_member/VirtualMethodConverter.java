package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.virtual_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.AbstractEnclosedElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.DocumentedTypeVirtualMembers;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.Element;

public class VirtualMethodConverter extends AbstractEnclosedElementConverter<DocumentedTypeVirtualMembers> {
    
    @Override
    public boolean canConvert(Element enclosedElement) {
        return isAnnotationPresentOn(ZenCodeType.Method.class, enclosedElement);
    }
    
    @Override
    public void convertAndAddTo(Element enclosedElement, DocumentedTypeVirtualMembers result, DocumentationPageInfo pageInfo) {
    
    }
}
