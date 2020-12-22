package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.named_type.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.AbstractEnclosedElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.header.HeaderConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.DocumentedTypeVirtualMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.OperatorMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;

public class NamedTypeVirtualOperatorConverter extends AbstractEnclosedElementConverter<DocumentedTypeVirtualMembers> {
    
    private final CommentConverter commentConverter;
    private final HeaderConverter headerConverter;
    private final TypeConverter typeConverter;
    
    public NamedTypeVirtualOperatorConverter(CommentConverter commentConverter, HeaderConverter headerConverter, TypeConverter typeConverter) {
        this.commentConverter = commentConverter;
        this.headerConverter = headerConverter;
        this.typeConverter = typeConverter;
    }
    
    @Override
    public boolean canConvert(Element enclosedElement) {
        return isAnnotationPresentOn(ZenCodeType.Operator.class, enclosedElement);
    }
    
    @Override
    public void convertAndAddTo(Element enclosedElement, DocumentedTypeVirtualMembers result, DocumentationPageInfo pageInfo) {
        ExecutableElement method = (ExecutableElement) enclosedElement;
        final OperatorMember operatorMember = convertOperator(method, pageInfo);
        
        result.addOperator(operatorMember);
    }
    
    private OperatorMember convertOperator(ExecutableElement method, DocumentationPageInfo pageInfo) {
        final MemberHeader header = convertHeader(method);
        final DocumentationComment comment = convertComment(method, pageInfo);
        final ZenCodeType.OperatorType type = convertType(method);
        final AbstractTypeInfo ownerType = convertOwnerType(pageInfo);
        
        return new OperatorMember(header, comment, type, ownerType);
    }
    
    private AbstractTypeInfo convertOwnerType(DocumentationPageInfo pageInfo) {
        if(pageInfo instanceof TypePageInfo) {
            return typeConverter.convertByName(((TypePageInfo) pageInfo).zenCodeName);
        }
        
        throw new IllegalArgumentException("Cannot get ownerType from " + pageInfo.getSimpleName());
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
    
    private ZenCodeType.OperatorType convertType(ExecutableElement method) {
        return method.getAnnotation(ZenCodeType.Operator.class).value();
    }
}
