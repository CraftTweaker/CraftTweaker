package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.named_type.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.AbstractEnclosedElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.PropertyMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.DocumentedTypeVirtualMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

public class NamedTypeVirtualFieldConverter extends AbstractEnclosedElementConverter<DocumentedTypeVirtualMembers> {
    
    private final TypeConverter typeConverter;
    
    public NamedTypeVirtualFieldConverter(TypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }
    
    @Override
    public boolean canConvert(Element enclosedElement) {
        return isAnnotationPresentOn(ZenCodeType.Field.class, enclosedElement);
    }
    
    @Override
    public void convertAndAddTo(Element enclosedElement, DocumentedTypeVirtualMembers result, DocumentationPageInfo pageInfo) {
        final PropertyMember property = convertProperty(enclosedElement);
        result.addProperty(property);
    }
    
    private PropertyMember convertProperty(Element enclosedElement) {
        final String name = convertName(enclosedElement);
        final AbstractTypeInfo type = convertType(enclosedElement);
        final boolean hasGetter = convertHasGetter();
        final boolean hasSetter = convertHasSetter(enclosedElement);
        
        return new PropertyMember(name, type, hasGetter, hasSetter);
    }
    
    private String convertName(Element enclosedElement) {
        return enclosedElement.getSimpleName().toString();
    }
    
    private AbstractTypeInfo convertType(Element enclosedElement) {
        return typeConverter.convertType(enclosedElement.asType());
    }
    
    private boolean convertHasGetter() {
        return true;
    }
    
    private boolean convertHasSetter(Element enclosedElement) {
        return isMutable(enclosedElement);
    }
    
    private boolean isMutable(Element enclosedElement) {
        return !enclosedElement.getModifiers().contains(Modifier.FINAL);
    }
}
