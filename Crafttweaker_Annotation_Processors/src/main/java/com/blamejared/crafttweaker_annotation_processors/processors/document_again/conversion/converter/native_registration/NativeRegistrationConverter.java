package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.native_registration;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.DocumentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.expansion.member.ExpansionVirtualMemberConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.header.GenericParameterConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.static_member.StaticMemberConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.named_type.SuperTypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.mods.KnownModList;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypeName;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.DocumentedGenericParameter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.static_member.DocumentedStaticMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.DocumentedVirtualMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.page.DocumentationPage;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.page.TypePage;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.stream.Collectors;

public class NativeRegistrationConverter extends DocumentConverter {
    
    private final StaticMemberConverter staticMemberConverter;
    private final ExpansionVirtualMemberConverter virtualMemberConverter;
    private final TypeConverter typeConverter;
    private final SuperTypeConverter superTypeConverter;
    private final GenericParameterConverter genericParameterConverter;
    
    public NativeRegistrationConverter(KnownModList knownModList, CommentConverter commentConverter, StaticMemberConverter staticMemberConverter, ExpansionVirtualMemberConverter virtualMemberConverter, TypeConverter typeConverter, SuperTypeConverter superTypeConverter, GenericParameterConverter genericParameterConverter) {
        super(knownModList, commentConverter);
        this.staticMemberConverter = staticMemberConverter;
        this.virtualMemberConverter = virtualMemberConverter;
        this.typeConverter = typeConverter;
        this.superTypeConverter = superTypeConverter;
        this.genericParameterConverter = genericParameterConverter;
    }
    
    @Override
    public boolean canConvert(TypeElement typeElement) {
        return getNativeAnnotation(typeElement) != null;
    }
    
    private NativeTypeRegistration getNativeAnnotation(TypeElement typeElement) {
        return typeElement.getAnnotation(NativeTypeRegistration.class);
    }
    
    @Override
    protected DocumentationPageInfo prepareConversion(TypeElement element) {
        final DocumentationPageInfo documentationPageInfo = super.prepareConversion(element);
        
        final TypeName name = new TypeName(getNativeAnnotation(element).zenCodeName());
        final String declaringModId = documentationPageInfo.declaringModId;
        final String outputPath = documentationPageInfo.getOutputPath();
        final DocumentationComment classComment = documentationPageInfo.getClassComment();
        return new TypePageInfo(declaringModId, outputPath, classComment, name);
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
        return superTypeConverter.convertSuperTypeFor(typeElement);
    }
    
    private List<AbstractTypeInfo> convertImplementedInterfaces(TypeElement typeElement) {
        return typeElement.getInterfaces()
                .stream()
                .map(typeConverter::convertType)
                .collect(Collectors.toList());
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
