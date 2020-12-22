package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.native_registration;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.NativeConversionRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.DocumentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.expansion.member.ExpansionVirtualMemberConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.header.GenericParameterConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.static_member.StaticMemberConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.named_type.ImplementationConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.named_type.SuperTypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.mods.KnownModList;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypeName;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.DocumentedGenericParameter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.Example;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.static_member.DocumentedStaticMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.DocumentedVirtualMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.page.DocumentationPage;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.page.TypePage;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.TypePageTypeInfo;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import javax.annotation.Nonnull;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.stream.Collectors;

public class NativeRegistrationConverter extends DocumentConverter {
    
    private final StaticMemberConverter staticMemberConverter;
    private final ExpansionVirtualMemberConverter virtualMemberConverter;
    private final SuperTypeConverter superTypeConverter;
    private final ImplementationConverter implementationConverter;
    private final GenericParameterConverter genericParameterConverter;
    private final NativeConversionRegistry nativeConversionRegistry;
    private final Elements elementUtils;
    private final Types typeUtils;
    
    public NativeRegistrationConverter(KnownModList knownModList, CommentConverter commentConverter, StaticMemberConverter staticMemberConverter, ExpansionVirtualMemberConverter virtualMemberConverter, SuperTypeConverter superTypeConverter, ImplementationConverter implementationConverter, GenericParameterConverter genericParameterConverter, NativeConversionRegistry nativeConversionRegistry, Elements elementUtils, Types typeUtils) {
        super(knownModList, commentConverter);
        this.staticMemberConverter = staticMemberConverter;
        this.virtualMemberConverter = virtualMemberConverter;
        this.superTypeConverter = superTypeConverter;
        this.implementationConverter = implementationConverter;
        this.genericParameterConverter = genericParameterConverter;
        this.nativeConversionRegistry = nativeConversionRegistry;
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
    }
    
    @Override
    public boolean canConvert(TypeElement typeElement) {
        return getNativeAnnotation(typeElement) != null;
    }
    
    private NativeTypeRegistration getNativeAnnotation(TypeElement typeElement) {
        return typeElement.getAnnotation(NativeTypeRegistration.class);
    }
    
    @Override
    protected TypePageInfo prepareConversion(TypeElement element) {
        final TypePageInfo typePageInfo = createTypePageInfo(element);
        registerNativeType(element, typePageInfo);
        return typePageInfo;
    }
    
    @Override
    protected Example getFallbackThisInformationFor(TypeElement typeElement) {
        final String text = "my" + getName(typeElement);
        return new Example("this", text);
    }
    
    private void registerNativeType(TypeElement element, TypePageInfo typePageInfo) {
        final AbstractTypeInfo typeInfo = new TypePageTypeInfo(typePageInfo);
        final TypeElement nativeType = getNativeType(element);
        
        nativeConversionRegistry.addNativeConversion(nativeType, typeInfo);
    }
    
    private TypeElement getNativeType(TypeElement element) {
        try {
            final NativeTypeRegistration nativeAnnotation = getNativeAnnotation(element);
            return getNativeTypeFromClass(nativeAnnotation.value());
        } catch(MirroredTypeException exception) {
            final TypeMirror typeMirror = exception.getTypeMirror();
            return (TypeElement) typeUtils.asElement(typeMirror);
        }
    }
    
    private TypeElement getNativeTypeFromClass(Class<?> cls) {
        return elementUtils.getTypeElement(cls.getCanonicalName());
    }
    
    @Nonnull
    private TypePageInfo createTypePageInfo(TypeElement element) {
        final DocumentationPageInfo documentationPageInfo = super.prepareConversion(element);
        
        final TypeName name = getName(element);
        final String declaringModId = documentationPageInfo.declaringModId;
        final String outputPath = documentationPageInfo.getOutputPath();
        return new TypePageInfo(declaringModId, outputPath, name);
    }
    
    @Nonnull
    private TypeName getName(TypeElement element) {
        return new TypeName(getNativeAnnotation(element).zenCodeName());
    }
    
    @Override
    public DocumentationPage convert(TypeElement typeElement, DocumentationPageInfo pageInfo) {
        final DocumentedVirtualMembers virtualMembers = convertVirtualMembers(typeElement, pageInfo);
        final AbstractTypeInfo superType = convertSuperType(typeElement);
        final List<AbstractTypeInfo> implementedInterfaces = convertImplementedInterfaces(typeElement);
        final DocumentedStaticMembers staticMembers = convertStaticMembers(typeElement, pageInfo);
        final List<DocumentedGenericParameter> genericParameters = convertGenericParameters(typeElement);
        
        return new TypePage((TypePageInfo) pageInfo, virtualMembers, superType, implementedInterfaces, staticMembers, genericParameters);
    }
    
    private DocumentedVirtualMembers convertVirtualMembers(TypeElement typeElement, DocumentationPageInfo pageInfo) {
        return virtualMemberConverter.convertFor(typeElement, pageInfo);
    }
    
    private AbstractTypeInfo convertSuperType(TypeElement typeElement) {
        final TypeElement nativeType = getNativeType(typeElement);
        return superTypeConverter.convertSuperTypeFor(nativeType);
    }
    
    private List<AbstractTypeInfo> convertImplementedInterfaces(TypeElement typeElement) {
        final TypeElement nativeType = getNativeType(typeElement);
        return implementationConverter.convertInterfacesFor(nativeType);
    }
    
    private DocumentedStaticMembers convertStaticMembers(TypeElement typeElement, DocumentationPageInfo pageInfo) {
        return staticMemberConverter.convertFor(typeElement, pageInfo);
    }
    
    private List<DocumentedGenericParameter> convertGenericParameters(TypeElement typeElement) {
        return typeElement.getTypeParameters()
                .stream()
                .map(genericParameterConverter::convertGenericParameter)
                .collect(Collectors.toList());
    }
}
