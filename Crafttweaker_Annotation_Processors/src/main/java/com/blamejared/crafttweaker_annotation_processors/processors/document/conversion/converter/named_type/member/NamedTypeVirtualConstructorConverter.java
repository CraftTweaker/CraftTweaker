package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.named_type.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.header.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.*;
import org.openzen.zencode.java.*;

import javax.lang.model.element.*;
import javax.lang.model.type.*;
import java.util.*;

public class NamedTypeVirtualConstructorConverter extends AbstractEnclosedElementConverter<DocumentedTypeVirtualMembers> {
    
    private final HeaderConverter headerConverter;
    private final CommentConverter commentConverter;
    
    public NamedTypeVirtualConstructorConverter(HeaderConverter headerConverter, CommentConverter commentConverter) {
        this.headerConverter = headerConverter;
        this.commentConverter = commentConverter;
    }
    
    @Override
    public boolean canConvert(Element enclosedElement) {
        return isAnnotationPresentOn(ZenCodeType.Constructor.class, enclosedElement);
    }
    
    @Override
    public void convertAndAddTo(Element enclosedElement, DocumentedTypeVirtualMembers result, DocumentationPageInfo pageInfo) {
        final ConstructorMember constructorMember = convertConstructor(enclosedElement, pageInfo);
        result.addConstructor(constructorMember);
    }
    
    private ConstructorMember convertConstructor(Element enclosedElement, DocumentationPageInfo pageInfo) {
        ExecutableElement constructor = (ExecutableElement) enclosedElement;
        final MemberHeader header = convertHeader(constructor);
        final DocumentationComment comment = convertComment(constructor, pageInfo);
        
        return new ConstructorMember(header, comment);
    }
    
    private MemberHeader convertHeader(ExecutableElement constructor) {
        final List<? extends VariableElement> parameters = convertParameters(constructor);
        final List<? extends TypeParameterElement> typeParameters = convertTypeParameters(constructor);
        final TypeMirror returnType = convertReturnType(constructor);
        
        return headerConverter.convertHeaderFor(parameters, typeParameters, returnType);
    }
    
    private List<? extends VariableElement> convertParameters(ExecutableElement constructor) {
        return constructor.getParameters();
    }
    
    private List<? extends TypeParameterElement> convertTypeParameters(ExecutableElement constructor) {
        return constructor.getTypeParameters();
    }
    
    private TypeMirror convertReturnType(ExecutableElement element) {
        return element.getEnclosingElement().asType();
    }
    
    private DocumentationComment convertComment(ExecutableElement enclosedElement, DocumentationPageInfo pageInfo) {
        return commentConverter.convertForConstructor(enclosedElement, pageInfo);
    }
}
