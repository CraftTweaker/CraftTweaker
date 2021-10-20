package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.native_registration.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.PropertyMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member.DocumentedStaticMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

/**
 * @author youyihj
 */
public class NativeEnumConstantConverter {
    
    private final CommentConverter commentConverter;
    
    public NativeEnumConstantConverter(CommentConverter commentConverter) {
        
        this.commentConverter = commentConverter;
    }
    
    public void convertAndAddTo(Element enumElement, DocumentedStaticMembers result, AbstractTypeInfo thisTypeInfo) {
        
        if(isEnumType(enumElement)) {
            enumElement.getEnclosedElements().stream()
                    .filter(it -> it.getKind() == ElementKind.ENUM_CONSTANT)
                    .forEach(enumConstantElement -> this.addEnumConstantInfo(enumConstantElement, result, thisTypeInfo));
        }
    }
    
    private boolean isEnumType(Element element) {
        
        return element.getKind() == ElementKind.ENUM;
    }
    
    private void addEnumConstantInfo(Element enumConstantElement, DocumentedStaticMembers result, AbstractTypeInfo thisTypeInfo) {
        
        final DocumentationComment description = commentConverter.convertElement(enumConstantElement, DocumentationComment.empty());
        result.addProperty(new PropertyMember(enumConstantElement.getSimpleName()
                .toString(), thisTypeInfo, true, false, description));
    }
    
}
