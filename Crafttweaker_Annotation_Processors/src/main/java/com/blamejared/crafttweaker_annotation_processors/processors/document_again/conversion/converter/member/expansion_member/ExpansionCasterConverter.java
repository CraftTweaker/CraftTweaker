package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.expansion_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.AbstractEnclosedElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.header.HeaderConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.CasterMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.DocumentedVirtualMembers;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;

public class ExpansionCasterConverter extends AbstractEnclosedElementConverter<DocumentedVirtualMembers> {
    
    private final HeaderConverter headerConverter;
    private final CommentConverter commentConverter;
    
    public ExpansionCasterConverter(TypeConverter typeConverter, HeaderConverter headerConverter, CommentConverter commentConverter) {
        super(typeConverter);
        this.headerConverter = headerConverter;
        this.commentConverter = commentConverter;
    }
    
    @Override
    public boolean canConvert(Element enclosedElement) {
        return isAnnotationPresentOn(ZenCodeType.Caster.class, enclosedElement);
    }
    
    @Override
    public void convertAndAddTo(Element enclosedElement, DocumentedVirtualMembers result, DocumentationPageInfo pageInfo) {
        final ExecutableElement method = (ExecutableElement) enclosedElement;
        final CasterMember caster = convertCaster(method, pageInfo);
        result.addCaster(caster);
    }
    
    private CasterMember convertCaster(ExecutableElement method, DocumentationPageInfo pageInfo) {
        final MemberHeader header = convertHeader(method);
        final DocumentationComment comment = convertComment(method, pageInfo);
        final boolean implicit = isImplicit(method);
        
        return new CasterMember(header, comment, implicit);
    }
    
    private MemberHeader convertHeader(ExecutableElement method) {
        final List<? extends VariableElement> parameters = method.getParameters();
        final List<? extends TypeParameterElement> typeParameters = method.getTypeParameters();
        final TypeMirror returnType = method.getReturnType();
        return headerConverter.convertHeaderFor(parameters, typeParameters, returnType);
    }
    
    private DocumentationComment convertComment(ExecutableElement method, DocumentationPageInfo pageInfo) {
        return commentConverter.convertForMethod(method, pageInfo);
    }
    
    private boolean isImplicit(ExecutableElement method) {
        return method.getAnnotation(ZenCodeType.Caster.class).implicit();
    }
}
