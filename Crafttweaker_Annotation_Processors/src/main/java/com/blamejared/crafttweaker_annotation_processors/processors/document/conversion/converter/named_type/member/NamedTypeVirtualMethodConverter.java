package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.named_type.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter.ReturnTypeInfoReader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.AbstractEnclosedElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.header.HeaderConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.DocumentedTypeVirtualMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.VirtualMethodMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.List;

public class NamedTypeVirtualMethodConverter extends AbstractEnclosedElementConverter<DocumentedTypeVirtualMembers> {
    
    private final TypeConverter typeConverter;
    private final HeaderConverter headerConverter;
    private final CommentConverter commentConverter;
    private final ReturnTypeInfoReader returnTypeInfoReader;
    private final Types typeUtils;
    
    public NamedTypeVirtualMethodConverter(TypeConverter typeConverter, HeaderConverter headerConverter, CommentConverter commentConverter,
                                           ReturnTypeInfoReader returnTypeInfoReader, Types typeUtils) {
        this.typeConverter = typeConverter;
        this.headerConverter = headerConverter;
        this.commentConverter = commentConverter;
        this.returnTypeInfoReader = returnTypeInfoReader;
        this.typeUtils = typeUtils;
    }
    
    @Override
    public boolean canConvert(Element enclosedElement) {
        return isAnnotationPresentOn(ZenCodeType.Method.class, enclosedElement);
    }
    
    @Override
    public void convertAndAddTo(Element enclosedElement, DocumentedTypeVirtualMembers result, DocumentationPageInfo pageInfo) {
        ExecutableElement methodElement = (ExecutableElement) enclosedElement;
        final AbstractTypeInfo ownerType = getOwnerType(pageInfo);
        final VirtualMethodMember methodMember = convertMethod(methodElement, pageInfo);
        result.addMethod(methodMember, ownerType);
    }
    
    private VirtualMethodMember convertMethod(ExecutableElement enclosedElement, DocumentationPageInfo pageInfo) {
        final String name = convertName(enclosedElement);
        final MemberHeader header = convertHeader(enclosedElement);
        final DocumentationComment comment = convertComment(enclosedElement, pageInfo);
        final String returnTypeInfo = getReturnTypeInfo(enclosedElement);
        
        return new VirtualMethodMember(header, comment, name, returnTypeInfo);
    }
    
    private DocumentationComment convertComment(ExecutableElement enclosedElement, DocumentationPageInfo pageInfo) {
        return commentConverter.convertForMethod(enclosedElement, pageInfo);
    }
    
    private String getReturnTypeInfo(final ExecutableElement executableElement) {
        return this.returnTypeInfoReader.readForMethod(executableElement).orElse(null);
    }
    
    private MemberHeader convertHeader(ExecutableElement enclosedElement) {
        final List<? extends VariableElement> parameters = getParameters(enclosedElement);
        final List<? extends TypeParameterElement> typeParameters = enclosedElement.getTypeParameters();
        final TypeMirror returnType = enclosedElement.getReturnType();
        
        return headerConverter.convertHeaderFor(parameters, typeParameters, returnType);
    }
    
    private List<? extends VariableElement> getParameters(ExecutableElement enclosedElement) {
        return enclosedElement.getParameters();
    }
    
    private String convertName(ExecutableElement enclosedElement) {
        return enclosedElement.getSimpleName().toString();
    }
    
    private AbstractTypeInfo getOwnerType(DocumentationPageInfo pageInfo) {
        if(pageInfo instanceof TypePageInfo) {
            return getOwnerType((TypePageInfo) pageInfo);
        }
        
        throw new IllegalStateException("Could not get OwnerType!");
    }
    
    private AbstractTypeInfo getOwnerType(TypePageInfo pageInfo) {
        return typeConverter.convertByName(pageInfo.zenCodeName);
    }
}
