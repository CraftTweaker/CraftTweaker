package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.expansion_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.AbstractEnclosedElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.header.HeaderConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.DocumentedVirtualMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.VirtualMethodMember;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.Collections;
import java.util.List;

public class ExpansionMethodConverter extends AbstractEnclosedElementConverter<DocumentedVirtualMembers> {
    
    private final CommentConverter commentConverter;
    private final HeaderConverter headerConverter;
    
    public ExpansionMethodConverter(TypeConverter typeConverter, CommentConverter commentConverter, HeaderConverter headerConverter) {
        super(typeConverter);
        this.commentConverter = commentConverter;
        this.headerConverter = headerConverter;
    }
    
    @Override
    public boolean canConvert(Element enclosedElement) {
        return isAnnotationPresentOn(ZenCodeType.Method.class, enclosedElement);
    }
    
    @Override
    public void convertAndAddTo(Element enclosedElement, DocumentedVirtualMembers result, DocumentationPageInfo pageInfo) {
        final ExecutableElement method = (ExecutableElement) enclosedElement;
        final VirtualMethodMember convertedMethod = convert(method, pageInfo);
        result.addMethod(convertedMethod);
    }
    
    private VirtualMethodMember convert(ExecutableElement method, DocumentationPageInfo pageInfo) {
        final String name = method.getSimpleName().toString();
        final DocumentationComment description = commentConverter.convertForMethod(method, pageInfo);
        final MemberHeader header = convertHeader(method);
        
        return new VirtualMethodMember(header, description, name);
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
