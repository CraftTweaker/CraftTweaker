package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.expansion.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.AbstractEnclosedElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.PropertyMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.DocumentedVirtualMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

public class ExpansionGetterSetterConverter extends AbstractEnclosedElementConverter<DocumentedVirtualMembers> {
    
    private final TypeConverter typeConverter;
    private final CommentConverter commentConverter;
    
    public ExpansionGetterSetterConverter(TypeConverter typeConverter, CommentConverter commentConverter) {
        
        this.typeConverter = typeConverter;
        this.commentConverter = commentConverter;
    }
    
    @Override
    public boolean canConvert(Element enclosedElement) {
        
        return isGetter(enclosedElement) || isSetter(enclosedElement);
    }
    
    private boolean isGetter(Element enclosedElement) {
        
        return isAnnotationPresentOn(ZenCodeType.Getter.class, enclosedElement);
    }
    
    private boolean isSetter(Element enclosedElement) {
        
        return isAnnotationPresentOn(ZenCodeType.Setter.class, enclosedElement);
    }
    
    @Override
    public void convertAndAddTo(Element enclosedElement, DocumentedVirtualMembers result, DocumentationPageInfo pageInfo) {
        
        final PropertyMember propertyMember = convertProperty(enclosedElement);
        
        result.addProperty(propertyMember);
    }
    
    private PropertyMember convertProperty(Element enclosedElement) {
        
        final ExecutableElement method = (ExecutableElement) enclosedElement;
        final String name = getName(method);
        final AbstractTypeInfo type = getTypeInfo(method);
        final boolean hasGetter = isGetter(method);
        final boolean hasSetter = isSetter(method);
        final DocumentationComment description = commentConverter.convertElement(enclosedElement, DocumentationComment.empty());
        
        return new PropertyMember(name, type, hasGetter, hasSetter, description);
    }
    
    private String getName(Element enclosedElement) {
        
        if(isGetter(enclosedElement)) {
            return getGetterName(enclosedElement);
        } else {
            return getSetterName(enclosedElement);
        }
    }
    
    private String getGetterName(Element enclosedElement) {
        
        return enclosedElement.getAnnotation(ZenCodeType.Getter.class).value();
    }
    
    private String getSetterName(Element enclosedElement) {
        
        return enclosedElement.getAnnotation(ZenCodeType.Setter.class).value();
    }
    
    private AbstractTypeInfo getTypeInfo(ExecutableElement enclosedElement) {
        
        if(isGetter(enclosedElement)) {
            return getGetterTypeInfo(enclosedElement);
        } else {
            return getSetterTypeInfo(enclosedElement);
        }
    }
    
    private AbstractTypeInfo getGetterTypeInfo(ExecutableElement enclosedElement) {
        
        final TypeMirror returnType = enclosedElement.getReturnType();
        return typeConverter.convertType(returnType);
    }
    
    private AbstractTypeInfo getSetterTypeInfo(ExecutableElement enclosedElement) {
        
        final TypeMirror typeMirror = enclosedElement.getParameters().get(0).asType();
        return typeConverter.convertType(typeMirror);
    }
    
}
