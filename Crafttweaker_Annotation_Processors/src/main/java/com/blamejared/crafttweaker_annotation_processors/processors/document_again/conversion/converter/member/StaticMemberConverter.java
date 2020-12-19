package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.static_member.StaticFieldConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.static_member.DocumentedStaticMembers;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

public class StaticMemberConverter extends MemberConverter<DocumentedStaticMembers> {
    
    public StaticMemberConverter(TypeConverter typeConverter) {
        addElementConverter(ElementKind.FIELD, new StaticFieldConverter(typeConverter));
    }
    
    @Override
    protected DocumentedStaticMembers createResultObject() {
        return new DocumentedStaticMembers();
    }
    
    @Override
    protected boolean isCandidate(Element enclosedElement) {
        return enclosedElement.getModifiers().contains(Modifier.STATIC);
    }
}
