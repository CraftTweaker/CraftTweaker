package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.named_type.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.AbstractEnclosedElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.header.HeaderConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.ConstructorMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.DocumentedTypeVirtualMembers;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;

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
    
    private TypeMirror convertReturnType(ExecutableElement constructor) {
        return constructor.getReturnType();
    }
    
    private DocumentationComment convertComment(ExecutableElement enclosedElement, DocumentationPageInfo pageInfo) {
        return commentConverter.convertForMethod(enclosedElement, pageInfo);
    }
}
