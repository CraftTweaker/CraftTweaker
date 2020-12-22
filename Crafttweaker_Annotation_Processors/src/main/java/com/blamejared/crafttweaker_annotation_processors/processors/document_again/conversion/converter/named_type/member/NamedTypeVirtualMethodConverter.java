package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.named_type.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.header.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.*;
import org.openzen.zencode.java.*;

import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.*;
import java.util.*;

public class NamedTypeVirtualMethodConverter extends AbstractEnclosedElementConverter<DocumentedTypeVirtualMembers> {
    
    private final TypeConverter typeConverter;
    private final HeaderConverter headerConverter;
    private final CommentConverter commentConverter;
    private final Types typeUtils;
    
    public NamedTypeVirtualMethodConverter(TypeConverter typeConverter, HeaderConverter headerConverter, CommentConverter commentConverter, Types typeUtils) {
        this.typeConverter = typeConverter;
        this.headerConverter = headerConverter;
        this.commentConverter = commentConverter;
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
        
        return new VirtualMethodMember(header, comment, name);
    }
    
    private DocumentationComment convertComment(ExecutableElement enclosedElement, DocumentationPageInfo pageInfo) {
        return commentConverter.convertForMethod(enclosedElement, pageInfo);
    }
    
    private MemberHeader convertHeader(ExecutableElement enclosedElement) {
        final List<? extends VariableElement> parameters = getParameters(enclosedElement);
        final List<? extends TypeParameterElement> typeParameters = enclosedElement.getTypeParameters();
        final TypeMirror returnType = enclosedElement.getReturnType();
        
        return headerConverter.convertHeaderFor(parameters, typeParameters, returnType);
    }
    
    private List<? extends VariableElement> getParameters(ExecutableElement enclosedElement) {
        final List<? extends VariableElement> parameters = new ArrayList<>(enclosedElement.getParameters());
        final int size = enclosedElement.getTypeParameters().size();
        removeClassParametersIfPresent(parameters, size);
        
        return parameters;
    }
    
    private void removeClassParametersIfPresent(List<? extends VariableElement> parameters, int maximumNumberOfParameters) {
        for(int i = 0; i < maximumNumberOfParameters; i++) {
            final VariableElement variableElement = parameters.get(0);
            final Element element = typeUtils.asElement(variableElement.asType());
            if(isNoClassParameter(element)) {
                return;
            } else {
                parameters.remove(0);
            }
        }
    }
    
    private boolean isNoClassParameter(Element element) {
        return !(element instanceof TypeElement) || !isClassType((TypeElement) element);
    }
    
    private boolean isClassType(TypeElement element) {
        return element.getQualifiedName().toString().equals(Class.class.getCanonicalName());
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
