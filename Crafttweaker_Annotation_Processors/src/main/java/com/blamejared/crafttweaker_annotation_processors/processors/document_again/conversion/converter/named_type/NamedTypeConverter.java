package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.named_type;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.DocumentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.header.GenericParameterConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.static_member.StaticMemberConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.named_type.member.NamedTypeVirtualMemberConverter;
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
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.stream.Collectors;

public class NamedTypeConverter extends DocumentConverter {
    
    private final GenericParameterConverter genericParameterConverter;
    private final SuperTypeConverter superTypeConverter;
    private final NamedTypeVirtualMemberConverter namedTypeVirtualMemberConverter;
    private final ImplementationConverter implementationConverter;
    private final StaticMemberConverter staticMemberConverter;
    
    public NamedTypeConverter(KnownModList knownModList, CommentConverter commentConverter, GenericParameterConverter genericParameterConverter, SuperTypeConverter superTypeConverter, NamedTypeVirtualMemberConverter namedTypeVirtualMemberConverter, ImplementationConverter implementationConverter, StaticMemberConverter staticMemberConverter) {
        super(knownModList, commentConverter);
        
        this.genericParameterConverter = genericParameterConverter;
        this.superTypeConverter = superTypeConverter;
        this.namedTypeVirtualMemberConverter = namedTypeVirtualMemberConverter;
        this.implementationConverter = implementationConverter;
        this.staticMemberConverter = staticMemberConverter;
    }
    
    @Override
    public boolean canConvert(TypeElement typeElement) {
        return typeElement.getAnnotation(ZenCodeType.Name.class) != null;
    }
    
    @Override
    protected DocumentationPageInfo prepareConversion(TypeElement element) {
        final DocumentationPageInfo documentationPageInfo = super.prepareConversion(element);
        
        final TypeName name = getName(element);
        final String declaringModId = documentationPageInfo.declaringModId;
        final String outputPath = documentationPageInfo.getOutputPath();
        return new TypePageInfo(declaringModId, outputPath, name);
    }
    
    @Nonnull
    private TypeName getName(TypeElement element) {
        return new TypeName(element.getAnnotation(ZenCodeType.Name.class).value());
    }
    
    @Override
    protected Example getFallbackThisInformationFor(TypeElement typeElement) {
        final String text = "my" + getName(typeElement).getSimpleName();
        return new Example("this", text);
    }
    
    @Override
    public DocumentationPage convert(TypeElement typeElement, DocumentationPageInfo pageInfo) {
        final TypePageInfo typePageInfo = (TypePageInfo) pageInfo;
        final DocumentedVirtualMembers virtualMembers = convertVirtualMembers(typeElement, typePageInfo);
        final DocumentedStaticMembers staticMembers = convertStaticMembers(typeElement, typePageInfo);
        final AbstractTypeInfo superType = convertSuperType(typeElement);
        final List<AbstractTypeInfo> implementations = convertImplementations(typeElement);
        final List<DocumentedGenericParameter> genericParameters = convertGenericParameters(typeElement);
        
        return new TypePage(typePageInfo, virtualMembers, superType, implementations, staticMembers, genericParameters);
    }
    
    private DocumentedVirtualMembers convertVirtualMembers(TypeElement typeElement, TypePageInfo typePageInfo) {
        return namedTypeVirtualMemberConverter.convertFor(typeElement, typePageInfo);
    }
    
    private DocumentedStaticMembers convertStaticMembers(TypeElement typeElement, TypePageInfo typePageInfo) {
        return staticMemberConverter.convertFor(typeElement, typePageInfo);
    }
    
    private AbstractTypeInfo convertSuperType(TypeElement typeElement) {
        return superTypeConverter.convertSuperTypeFor(typeElement);
    }
    
    private List<AbstractTypeInfo> convertImplementations(TypeElement typeElement) {
        return implementationConverter.convertInterfacesFor(typeElement);
    }
    
    private List<DocumentedGenericParameter> convertGenericParameters(TypeElement typeElement) {
        return typeElement.getTypeParameters()
                .stream()
                .map(genericParameterConverter::convertGenericParameter)
                .collect(Collectors.toList());
    }
}
