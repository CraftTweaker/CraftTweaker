package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.static_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.AbstractEnclosedElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.PropertyMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.static_member.DocumentedStaticMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

public class StaticFieldConverter extends AbstractEnclosedElementConverter<DocumentedStaticMembers> {
    
    
    public StaticFieldConverter(TypeConverter typeConverter) {
        super(typeConverter);
    }
    
    @Override
    public boolean canConvert(Element enclosedElement) {
        return isAnnotationPresentOn(ZenCodeType.Field.class, enclosedElement);
    }
    
    @Override
    public void convertAndAddTo(Element enclosedField, DocumentedStaticMembers result, DocumentationPageInfo pageInfo) {
        final boolean isFinal = enclosedField.getModifiers().contains(Modifier.FINAL);
        final String name = enclosedField.getSimpleName().toString();
        final TypeMirror typeMirror = enclosedField.asType();
        final AbstractTypeInfo fieldType = typeConverter.convertType(typeMirror);
        
        final PropertyMember propertyMember = new PropertyMember(name, fieldType, true, !isFinal);
        result.addProperty(propertyMember);
    }
}
