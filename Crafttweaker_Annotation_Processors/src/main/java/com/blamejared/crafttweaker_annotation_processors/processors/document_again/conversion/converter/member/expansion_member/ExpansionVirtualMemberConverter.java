package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.expansion_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.MemberConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.expansion_member.ExpansionCasterConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.expansion_member.ExpansionMethodConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.header.HeaderConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.DocumentedVirtualMembers;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

public class ExpansionVirtualMemberConverter extends MemberConverter<DocumentedVirtualMembers> {
    
    public ExpansionVirtualMemberConverter(TypeConverter typeConverter, CommentConverter commentConverter, HeaderConverter headerConverter) {
        addElementConverter(ElementKind.METHOD, new ExpansionMethodConverter(typeConverter, commentConverter, headerConverter));
        addElementConverter(ElementKind.METHOD, new ExpansionCasterConverter(typeConverter, headerConverter, commentConverter));
    }
    
    @Override
    protected DocumentedVirtualMembers createResultObject() {
        return new DocumentedVirtualMembers();
    }
    
    @Override
    protected boolean isCandidate(Element enclosedElement) {
        return enclosedElement.getModifiers().contains(Modifier.STATIC);
    }
}
