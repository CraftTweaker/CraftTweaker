package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.expansion.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter.ReturnTypeInfoReader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.AbstractEnclosedElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.header.HeaderConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.DocumentedVirtualMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.VirtualMethodMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.util.Collections;
import java.util.List;

public class ExpansionMethodConverter extends AbstractEnclosedElementConverter<DocumentedVirtualMembers> {
    
    private final CommentConverter commentConverter;
    private final HeaderConverter headerConverter;
    private final TypeConverter typeConverter;
    private final ReturnTypeInfoReader infoReader;
    
    public ExpansionMethodConverter(CommentConverter commentConverter, HeaderConverter headerConverter, TypeConverter typeConverter, ReturnTypeInfoReader infoReader) {
        this.commentConverter = commentConverter;
        this.headerConverter = headerConverter;
        this.typeConverter = typeConverter;
        this.infoReader = infoReader;
    }
    
    @Override
    public boolean canConvert(Element enclosedElement) {
        return isAnnotationPresentOn(ZenCodeType.Method.class, enclosedElement);
    }
    
    @Override
    public void convertAndAddTo(Element enclosedElement, DocumentedVirtualMembers result, DocumentationPageInfo pageInfo) {
        final ExecutableElement method = (ExecutableElement) enclosedElement;
        final VirtualMethodMember convertedMethod = convert(method, pageInfo);
        final AbstractTypeInfo ownerTypeInfo = getOwnerTypeInfo(pageInfo);
        
        result.addMethod(convertedMethod, ownerTypeInfo);
    }
    
    private AbstractTypeInfo getOwnerTypeInfo(DocumentationPageInfo pageInfo) {
        return typeConverter.convertByName(((TypePageInfo) pageInfo).zenCodeName);
    }
    
    private VirtualMethodMember convert(ExecutableElement method, DocumentationPageInfo pageInfo) {
        final String name = method.getSimpleName().toString();
        final DocumentationComment description = commentConverter.convertForMethod(method, pageInfo);
        final MemberHeader header = convertHeader(method);
        final String returnTypeInfo = this.infoReader.readForMethod(method).orElse(null);
        
        return new VirtualMethodMember(header, description, name, returnTypeInfo);
    }
    
    private MemberHeader convertHeader(ExecutableElement method) {
        final List<? extends VariableElement> parameters = getParametersFor(method);
        final List<? extends TypeParameterElement> typeParameters = getTypeParametersFor(method);
        final TypeMirror returnType = method.getReturnType();
        return headerConverter.convertHeaderFor(parameters, typeParameters, returnType);
    }
    
    private List<? extends VariableElement> getParametersFor(ExecutableElement method) {
        final List<? extends VariableElement> parameters = method.getParameters();
        if(parameters.isEmpty()) {
            //Error, but caught in Validator AP
            return Collections.emptyList();
        }
        
        return parameters.subList(1, parameters.size());
    }
    
    private List<? extends TypeParameterElement> getTypeParametersFor(ExecutableElement method) {
        return method.getTypeParameters();
    }
}
