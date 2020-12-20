package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.named_type.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.AbstractEnclosedElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.PropertyMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.DocumentedTypeVirtualMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.Element;

public class NamedTypeVirtualGetterSetterConverter extends AbstractEnclosedElementConverter<DocumentedTypeVirtualMembers> {
    
    private final TypeConverter typeConverter;
    
    public NamedTypeVirtualGetterSetterConverter(TypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }
    
    @Override
    public boolean canConvert(Element enclosedElement) {
        return isAnnotationPresentOn(ZenCodeType.Setter.class, enclosedElement) || isAnnotationPresentOn(ZenCodeType.Getter.class, enclosedElement);
    }
    
    @Override
    public void convertAndAddTo(Element enclosedElement, DocumentedTypeVirtualMembers result, DocumentationPageInfo pageInfo) {
        final PropertyMember propertyMember = convertMember(enclosedElement);
        
        result.addProperty(propertyMember);
    }
    
    private PropertyMember convertMember(Element enclosedElement) {
        final String name = convertName(enclosedElement);
        final AbstractTypeInfo typeInfo = convertType(enclosedElement);
        final boolean hasGetter = convertHasGetter(enclosedElement);
        final boolean hasSetter = convertHasSetter(enclosedElement);
        
        
        return new PropertyMember(name, typeInfo, hasGetter, hasSetter);
    }
    
    private String convertName(Element enclosedElement) {
        if(isAnnotationPresentOn(ZenCodeType.Getter.class, enclosedElement)) {
            return enclosedElement.getAnnotation(ZenCodeType.Getter.class).value();
        } else {
            return enclosedElement.getAnnotation(ZenCodeType.Setter.class).value();
        }
    }
    
    private AbstractTypeInfo convertType(Element enclosedElement) {
        return typeConverter.convertType(enclosedElement.asType());
    }
    
    private boolean convertHasGetter(Element enclosedElement) {
        return isAnnotationPresentOn(ZenCodeType.Getter.class, enclosedElement);
    }
    
    private boolean convertHasSetter(Element enclosedElement) {
        return isAnnotationPresentOn(ZenCodeType.Setter.class, enclosedElement);
    }
}
