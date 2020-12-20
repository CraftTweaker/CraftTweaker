package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.named_type;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.DocumentRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.DocumentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.header.GenericParameterConverter;
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
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.stream.Collectors;

public class NamedTypeConverter extends DocumentConverter {
    
    private final GenericParameterConverter genericParameterConverter;
    
    public NamedTypeConverter(KnownModList knownModList, CommentConverter commentConverter, DocumentRegistry documentRegistry, TypeConverter typeConverter, GenericParameterConverter genericParameterConverter) {
        super(knownModList, commentConverter, documentRegistry, typeConverter);
    
        this.genericParameterConverter = genericParameterConverter;
    }
    
    @Override
    public boolean canConvert(TypeElement typeElement) {
        return typeElement.getAnnotation(ZenCodeType.Name.class) != null;
    }
    
    @Override
    protected DocumentationPageInfo prepareConversion(TypeElement element) {
        final DocumentationPageInfo documentationPageInfo = super.prepareConversion(element);
        
        final TypeName name = new TypeName(element.getAnnotation(ZenCodeType.Name.class).value());
        final String declaringModId = documentationPageInfo.declaringModId;
        final String outputPath = documentationPageInfo.getOutputPath();
        final DocumentationComment classComment = documentationPageInfo.getClassComment();
        return new TypePageInfo(declaringModId, outputPath, classComment, name);
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
        return null;
    }
    
    private DocumentedStaticMembers convertStaticMembers(TypeElement typeElement, TypePageInfo typePageInfo) {
        return null;
    }
    
    private AbstractTypeInfo convertSuperType(TypeElement typeElement) {
        return null;
    }
    
    private List<AbstractTypeInfo> convertImplementations(TypeElement typeElement) {
        return null;
    }
    
    private List<DocumentedGenericParameter> convertGenericParameters(TypeElement typeElement) {
        return typeElement.getTypeParameters()
                .stream()
                .map(genericParameterConverter::convertGenericParameter)
                .collect(Collectors.toList());
    }
}
