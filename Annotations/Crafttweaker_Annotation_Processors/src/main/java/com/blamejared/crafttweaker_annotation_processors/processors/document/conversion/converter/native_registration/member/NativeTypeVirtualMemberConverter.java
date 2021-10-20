package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.native_registration.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.expansion.member.ExpansionVirtualMemberConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.element.ClassTypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.MetaData;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.DocumentedGenericParameter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.DocumentedParameter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.examples.Example;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.examples.ExampleData;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.ConstructorMember;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.DocumentedTypeVirtualMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.DocumentedVirtualMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import javax.annotation.Nonnull;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NativeTypeVirtualMemberConverter extends ExpansionVirtualMemberConverter {
    
    private final TypeConverter typeConverter;
    private final ClassTypeConverter classTypeConverter;
    
    public NativeTypeVirtualMemberConverter(DependencyContainer dependencyContainer, TypeConverter typeConverter, ClassTypeConverter classTypeConverter, Elements elements) {
        
        super(dependencyContainer, elements);
        this.typeConverter = typeConverter;
        this.classTypeConverter = classTypeConverter;
    }
    
    @Override
    protected DocumentedVirtualMembers createResultObject(DocumentationPageInfo pageInfo) {
        
        return new DocumentedTypeVirtualMembers();
    }
    
    @Override
    public DocumentedVirtualMembers convertFor(TypeElement typeElement, DocumentationPageInfo pageInfo) {
        
        final DocumentedVirtualMembers virtualMembers = super.convertFor(typeElement, pageInfo);
        return addInjectedConstructors((DocumentedTypeVirtualMembers) virtualMembers, typeElement, (TypePageInfo) pageInfo);
    }
    
    private DocumentedVirtualMembers addInjectedConstructors(DocumentedTypeVirtualMembers virtualMembers, TypeElement typeElement, TypePageInfo pageInfo) {
        
        final NativeTypeRegistration nativeAnnotation = getNativeAnnotation(typeElement);
        for(NativeConstructor constructor : nativeAnnotation.constructors()) {
            final ConstructorMember constructorMember = getInjectedConstructor(constructor, pageInfo);
            virtualMembers.addConstructor(constructorMember);
        }
        return virtualMembers;
    }
    
    private ConstructorMember getInjectedConstructor(NativeConstructor constructor, TypePageInfo pageInfo) {
        
        final MemberHeader header = getHeader(constructor.value(), pageInfo);
        final DocumentationComment description = getDescription(constructor);
        
        
        return new ConstructorMember(header, description);
    }
    
    private DocumentationComment getDescription(NativeConstructor constructor) {
        
        final String description = constructor.description();
        final String deprecation = this.nullIfEmpty(constructor.deprecationMessage());
        final String since = this.nullIfEmpty(constructor.getSinceVersion());
        final ExampleData exampleData = ExampleData.empty();
        final MetaData metaData = MetaData.empty();
        
        return new DocumentationComment(description, deprecation, since, exampleData, metaData);
    }
    
    private String nullIfEmpty(final String string) {
        
        return string.isEmpty() ? null : string;
    }
    
    private MemberHeader getHeader(NativeConstructor.ConstructorParameter[] parameters, TypePageInfo pageInfo) {
        
        final List<DocumentedParameter> convertedParameters = getParameters(parameters);
        final AbstractTypeInfo returnType = getReturnType(pageInfo);
        final List<DocumentedGenericParameter> genericParameters = Collections.emptyList();
        
        return new MemberHeader(returnType, convertedParameters, genericParameters);
    }
    
    private AbstractTypeInfo getReturnType(TypePageInfo pageInfo) {
        
        return typeConverter.convertByName(pageInfo.zenCodeName);
    }
    
    @Nonnull
    private List<DocumentedParameter> getParameters(NativeConstructor.ConstructorParameter[] parameters) {
        
        return Arrays.stream(parameters).map(this::convertParameter).collect(Collectors.toList());
    }
    
    private DocumentedParameter convertParameter(NativeConstructor.ConstructorParameter constructorParameter) {
        
        final String name = getParameterName(constructorParameter);
        final AbstractTypeInfo type = getParameterType(constructorParameter);
        final DocumentationComment description = getParameterDescription(constructorParameter);
        
        return new DocumentedParameter(name, type, description);
    }
    
    private AbstractTypeInfo getParameterType(NativeConstructor.ConstructorParameter constructorParameter) {
        
        final TypeMirror nativeType = classTypeConverter.getTypeMirror(constructorParameter, NativeConstructor.ConstructorParameter::type);
        return typeConverter.convertType(nativeType);
    }
    
    private DocumentationComment getParameterDescription(NativeConstructor.ConstructorParameter constructorParameter) {
        
        final String description = constructorParameter.description();
        final ExampleData exampleData = extractExampleDataForParameter(constructorParameter);
        
        return new DocumentationComment(description, null, null, exampleData, MetaData.empty());
    }
    
    private ExampleData extractExampleDataForParameter(NativeConstructor.ConstructorParameter constructorParameter) {
        
        final Example parameterExample = getParameterExample(constructorParameter);
        return new ExampleData(parameterExample);
    }
    
    private Example getParameterExample(NativeConstructor.ConstructorParameter constructorParameter) {
        
        final String name = getParameterName(constructorParameter);
        final Example result = new Example(name);
        for(String textValue : constructorParameter.examples()) {
            result.addTextValue(textValue);
        }
        return result;
    }
    
    private String getParameterName(NativeConstructor.ConstructorParameter constructorParameter) {
        
        return constructorParameter.name();
    }
    
    private NativeTypeRegistration getNativeAnnotation(TypeElement typeElement) {
        
        return typeElement.getAnnotation(NativeTypeRegistration.class);
    }
    
}
