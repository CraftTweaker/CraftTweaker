package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.named_type.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.AbstractEnclosedElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.header.HeaderConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.CasterMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.DocumentedTypeVirtualMembers;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;

public class NamedTypeVirtualCasterConverter extends AbstractEnclosedElementConverter<DocumentedTypeVirtualMembers> {
    
    private final HeaderConverter headerConverter;
    private final CommentConverter commentConverter;
    
    public NamedTypeVirtualCasterConverter(HeaderConverter headerConverter, CommentConverter commentConverter) {
        
        this.headerConverter = headerConverter;
        this.commentConverter = commentConverter;
    }
    
    @Override
    public boolean canConvert(Element enclosedElement) {
        
        return isAnnotationPresentOn(ZenCodeType.Caster.class, enclosedElement);
    }
    
    @Override
    public void convertAndAddTo(Element enclosedElement, DocumentedTypeVirtualMembers result, DocumentationPageInfo pageInfo) {
        
        ExecutableElement method = (ExecutableElement) enclosedElement;
        final CasterMember casterMember = convertCaster(method, pageInfo);
        result.addCaster(casterMember);
    }
    
    private CasterMember convertCaster(ExecutableElement method, DocumentationPageInfo pageInfo) {
        
        final MemberHeader header = convertHeader(method);
        final DocumentationComment comment = convertComment(method, pageInfo);
        final boolean isImplicit = checkIfImplicit(method);
        
        return new CasterMember(header, comment, isImplicit);
    }
    
    private MemberHeader convertHeader(ExecutableElement method) {
        
        final List<? extends VariableElement> parameters = convertParameters(method);
        final List<? extends TypeParameterElement> typeParameters = convertTypeParameters(method);
        final TypeMirror returnType = convertReturnType(method);
        
        return headerConverter.convertHeaderFor(parameters, typeParameters, returnType);
    }
    
    private List<? extends VariableElement> convertParameters(ExecutableElement method) {
        
        return method.getParameters();
    }
    
    private List<? extends TypeParameterElement> convertTypeParameters(ExecutableElement method) {
        
        return method.getTypeParameters();
    }
    
    private TypeMirror convertReturnType(ExecutableElement method) {
        
        return method.getReturnType();
    }
    
    private DocumentationComment convertComment(ExecutableElement method, DocumentationPageInfo pageInfo) {
        
        return commentConverter.convertForMethod(method, pageInfo);
    }
    
    private boolean checkIfImplicit(ExecutableElement method) {
        
        return method.getAnnotation(ZenCodeType.Caster.class).implicit();
    }
    
}
