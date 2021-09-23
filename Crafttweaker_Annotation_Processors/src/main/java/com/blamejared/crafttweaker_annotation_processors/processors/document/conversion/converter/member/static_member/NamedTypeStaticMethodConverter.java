package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.static_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter.ReturnTypeInfoReader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.AbstractEnclosedElementConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.header.HeaderConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.TypeName;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member.BracketExpressionStaticMethodMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member.DocumentedStaticMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member.StaticMethodMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.util.annotations.AnnotationMirrorUtil;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.List;

public class NamedTypeStaticMethodConverter extends AbstractEnclosedElementConverter<DocumentedStaticMembers> {
    
    private static final String BEPAnnotationClass = "com.blamejared.crafttweaker.api.annotations.BracketResolver";
    
    private final CommentConverter commentConverter;
    private final HeaderConverter headerConverter;
    private final TypeConverter typeConverter;
    private final AnnotationMirrorUtil annotationMirrorUtil;
    private final ReturnTypeInfoReader returnTypeInfoReader;
    private final Types typeUtils;
    
    public NamedTypeStaticMethodConverter(CommentConverter commentConverter, HeaderConverter headerConverter, TypeConverter typeConverter, AnnotationMirrorUtil annotationMirrorUtil, ReturnTypeInfoReader returnTypeInfoReader, Types typeUtils) {
        
        this.commentConverter = commentConverter;
        this.headerConverter = headerConverter;
        this.typeConverter = typeConverter;
        this.annotationMirrorUtil = annotationMirrorUtil;
        this.returnTypeInfoReader = returnTypeInfoReader;
        this.typeUtils = typeUtils;
    }
    
    @Override
    public boolean canConvert(Element enclosedElement) {
        
        return isClassMethod(enclosedElement) || isExpansionMethod(enclosedElement);
    }
    
    private boolean isClassMethod(Element enclosedElement) {
        
        final Element classElement = enclosedElement.getEnclosingElement();
        return isAnnotationPresentOn(ZenCodeType.Name.class, classElement) && isAnnotationPresentOn(ZenCodeType.Method.class, enclosedElement);
    }
    
    private boolean isExpansionMethod(Element enclosedElement) {
        
        final Element classElement = enclosedElement.getEnclosingElement();
        return isAnnotationPresentOn(NativeTypeRegistration.class, classElement) && isAnnotationPresentOn(ZenCodeType.StaticExpansionMethod.class, enclosedElement);
    }
    
    @Override
    public void convertAndAddTo(Element enclosedElement, DocumentedStaticMembers result, DocumentationPageInfo pageInfo) {
        
        final StaticMethodMember staticMethodMember = convertStaticMethodMember(enclosedElement, pageInfo);
        final AbstractTypeInfo ownerType = getOwnerType(pageInfo);
        
        result.addMethod(staticMethodMember, ownerType);
    }
    
    private StaticMethodMember convertStaticMethodMember(Element enclosedElement, DocumentationPageInfo pageInfo) {
        
        ExecutableElement method = (ExecutableElement) enclosedElement;
        
        if(isBEPMethod(method)) {
            return convertBEPMethodMember(method, pageInfo);
        } else {
            return convertMethodMember(method, pageInfo);
        }
    }
    
    private boolean isBEPMethod(ExecutableElement method) {
        
        return annotationMirrorUtil.isAnnotationPresentOn(method, BEPAnnotationClass);
    }
    
    private StaticMethodMember convertBEPMethodMember(ExecutableElement method, DocumentationPageInfo pageInfo) {
        
        final String name = getName(method);
        final MemberHeader header = getHeader(method);
        final DocumentationComment comment = getComment(method, pageInfo);
        final String bepName = getBepName(method);
        final String returnTypeInfo = getReturnTypeInfo(method);
        
        return new BracketExpressionStaticMethodMember(name, header, comment, returnTypeInfo, bepName);
    }
    
    private String getBepName(ExecutableElement method) {
        
        final AnnotationMirror mirror = annotationMirrorUtil.getMirror(method, BEPAnnotationClass);
        return annotationMirrorUtil.getAnnotationValue(mirror, "value");
    }
    
    private StaticMethodMember convertMethodMember(ExecutableElement method, DocumentationPageInfo pageInfo) {
        
        final String name = getName(method);
        final MemberHeader header = getHeader(method);
        final DocumentationComment comment = getComment(method, pageInfo);
        final String returnTypeInfo = getReturnTypeInfo(method);
        
        return new StaticMethodMember(name, header, comment, returnTypeInfo);
    }
    
    @Nullable
    private String getReturnTypeInfo(ExecutableElement method) {
        
        return returnTypeInfoReader.readForMethod(method).orElse(null);
    }
    
    private String getName(ExecutableElement method) {
        
        if(hasCustomName(method)) {
            return getCustomName(method);
        }
        return method.getSimpleName().toString();
    }
    
    private boolean hasCustomName(ExecutableElement method) {
        
        return !getCustomName(method).isEmpty();
    }
    
    private String getCustomName(ExecutableElement method) {
        
        ZenCodeType.Method methodAnnotation = getMethodAnnotation(method);
        if(methodAnnotation != null) {
            return methodAnnotation.value();
        }
        ZenCodeType.StaticExpansionMethod staticExpansionMethodAnnotation = getStaticExpansionMethodAnnotation(method);
        if(staticExpansionMethodAnnotation != null) {
            return staticExpansionMethodAnnotation.value();
        }
        return "";
    }
    
    private ZenCodeType.Method getMethodAnnotation(ExecutableElement method) {
        
        return method.getAnnotation(ZenCodeType.Method.class);
    }
    
    private ZenCodeType.StaticExpansionMethod getStaticExpansionMethodAnnotation(ExecutableElement method) {
        
        return method.getAnnotation(ZenCodeType.StaticExpansionMethod.class);
    }
    
    private MemberHeader getHeader(ExecutableElement enclosedElement) {
        
        final List<? extends VariableElement> parameters = getParameters(enclosedElement);
        final List<? extends TypeParameterElement> typeParameters = getTypeParameters(enclosedElement);
        final TypeMirror returnType = getReturnType(enclosedElement);
        return headerConverter.convertHeaderFor(parameters, typeParameters, returnType);
    }
    
    private List<? extends VariableElement> getParameters(ExecutableElement enclosedElement) {
        
        return enclosedElement.getParameters();
    }
    
    private List<? extends TypeParameterElement> getTypeParameters(ExecutableElement enclosedElement) {
        
        return enclosedElement.getTypeParameters();
    }
    
    private TypeMirror getReturnType(ExecutableElement enclosedElement) {
        
        return enclosedElement.getReturnType();
    }
    
    private DocumentationComment getComment(ExecutableElement enclosedElement, DocumentationPageInfo pageInfo) {
        
        return commentConverter.convertForMethod(enclosedElement, pageInfo);
    }
    
    private AbstractTypeInfo getOwnerType(DocumentationPageInfo pageInfo) {
        
        if(pageInfo instanceof TypePageInfo) {
            return getOwnerType(((TypePageInfo) pageInfo));
        }
        
        throw new IllegalStateException("Could not get OwnerType!");
    }
    
    private AbstractTypeInfo getOwnerType(TypePageInfo pageInfo) {
        
        final TypeName zenCodeName = pageInfo.zenCodeName;
        return typeConverter.convertByName(zenCodeName);
    }
    
}
