package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.enum_constant.DocumentedEnumConstants;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.enum_constant.EnumConstant;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

/**
 * @author youyihj
 */
public class EnumConstantConverter {
    
    private final CommentConverter commentConverter;
    
    public EnumConstantConverter(CommentConverter commentConverter) {
        
        this.commentConverter = commentConverter;
    }
    
    public void convertAndAddTo(Element enumElement, DocumentedEnumConstants result) {

        enumElement.getEnclosedElements().stream()
                .filter(this::isEnumConstantType)
                .forEach(enumConstantElement -> this.addEnumConstantInfo(enumConstantElement, result));
    }

    private boolean isEnumConstantType(Element element) {

        return element.getKind() == ElementKind.ENUM_CONSTANT;
    }
    
    private void addEnumConstantInfo(Element enumConstantElement, DocumentedEnumConstants result) {
        
        final DocumentationComment description = commentConverter.convertElement(enumConstantElement, DocumentationComment.empty());
        result.addConstant(new EnumConstant(enumConstantElement.getSimpleName().toString(), description));
    }
    
}
