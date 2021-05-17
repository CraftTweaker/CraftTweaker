package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.named_type.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.AbstractEnclosedElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.PropertyMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.DocumentedTypeVirtualMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

public class NamedTypeVirtualGetterSetterConverter extends AbstractEnclosedElementConverter<DocumentedTypeVirtualMembers> {
    
    private final TypeConverter typeConverter;
    private final CommentConverter commentConverter;
    
    public NamedTypeVirtualGetterSetterConverter(TypeConverter typeConverter, CommentConverter commentConverter) {
        this.typeConverter = typeConverter;
        this.commentConverter = commentConverter;
    }
    
    @Override
    public boolean canConvert(Element enclosedElement) {
        return isAnnotationPresentOn(ZenCodeType.Setter.class, enclosedElement) || isAnnotationPresentOn(ZenCodeType.Getter.class, enclosedElement);
    }
    
    @Override
    public void convertAndAddTo(Element enclosedElement, DocumentedTypeVirtualMembers result, DocumentationPageInfo pageInfo) {
        final ExecutableElement method = (ExecutableElement) enclosedElement;
        final PropertyMember propertyMember = convertMember(method);
        
        result.addProperty(propertyMember);
    }
    
    private PropertyMember convertMember(ExecutableElement method) {
        final String name = convertName(method);
        final AbstractTypeInfo typeInfo = convertType(method);
        final boolean hasGetter = convertHasGetter(method);
        final boolean hasSetter = convertHasSetter(method);
        final DocumentationComment description = commentConverter.convertElement(method, DocumentationComment.empty());
        
        return new PropertyMember(name, typeInfo, hasGetter, hasSetter, description);
    }
    
    private String convertName(ExecutableElement enclosedElement) {
        if(isAnnotationPresentOn(ZenCodeType.Getter.class, enclosedElement)) {
            return enclosedElement.getAnnotation(ZenCodeType.Getter.class).value();
        } else {
            return enclosedElement.getAnnotation(ZenCodeType.Setter.class).value();
        }
    }
    
    private AbstractTypeInfo convertType(ExecutableElement enclosedElement) {
        final TypeMirror returnType = enclosedElement.getReturnType();
        return typeConverter.convertType(returnType);
    }
    
    private boolean convertHasGetter(ExecutableElement enclosedElement) {
        return isAnnotationPresentOn(ZenCodeType.Getter.class, enclosedElement);
    }
    
    private boolean convertHasSetter(ExecutableElement enclosedElement) {
        return isAnnotationPresentOn(ZenCodeType.Setter.class, enclosedElement);
    }
}
