package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.expansion;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.DocumentRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.DocumentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.expansion.member.ExpansionVirtualMemberConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.static_member.StaticMemberConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.mods.KnownModList;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypeName;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.static_member.DocumentedStaticMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.DocumentedVirtualMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.page.DocumentationPage;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.page.ExpansionPage;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.TypePageTypeInfo;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.TypeElement;
import java.util.Optional;

public class ExpansionConverter extends DocumentConverter {
    
    private final StaticMemberConverter staticMemberConverter;
    private final ExpansionVirtualMemberConverter virtualMemberConverter;
    private final TypeConverter typeConverter;
    private final DocumentRegistry documentRegistry;
    
    public ExpansionConverter(KnownModList knownModList, CommentConverter commentConverter, DocumentRegistry documentRegistry, TypeConverter typeConverter, StaticMemberConverter staticMemberConverter, ExpansionVirtualMemberConverter virtualMemberConverter, DocumentRegistry documentRegistry1) {
        super(knownModList, commentConverter);
        this.staticMemberConverter = staticMemberConverter;
        this.virtualMemberConverter = virtualMemberConverter;
        this.typeConverter = typeConverter;
        this.documentRegistry = documentRegistry;
    }
    
    @Override
    public boolean canConvert(TypeElement typeElement) {
        return typeElement.getAnnotation(ZenCodeType.Expansion.class) != null;
    }
    
    @Override
    public DocumentationPage convert(TypeElement typeElement, DocumentationPageInfo pageInfo) {
        final AbstractTypeInfo expandedType = getExpandedType(typeElement);
        
        final DocumentedVirtualMembers virtualMembers = getVirtualMembers(typeElement, expandedType);
        final DocumentedStaticMembers staticMembers = getStaticMembers(typeElement, pageInfo);
        
        return new ExpansionPage(expandedType, prepareConversion(typeElement), virtualMembers, staticMembers);
    }
    
    private AbstractTypeInfo getExpandedType(TypeElement typeElement) {
        final ZenCodeType.Expansion expansion = typeElement.getAnnotation(ZenCodeType.Expansion.class);
        
        final TypeName expandedName = new TypeName(expansion.value());
        return typeConverter.convertByName(expandedName);
    }
    
    private DocumentedVirtualMembers getVirtualMembers(TypeElement typeElement, AbstractTypeInfo expandedType) {
        final DocumentationPageInfo pageInfo = getPageInfoForType(expandedType);
        return virtualMemberConverter.convertFor(typeElement, pageInfo);
    }
    
    private DocumentationPageInfo getPageInfoForType(AbstractTypeInfo expandedType) {
        if(expandedType instanceof TypePageTypeInfo) {
            return getPageInfo((TypePageTypeInfo)expandedType);
        }
        throw new IllegalArgumentException("Invalid expanded type! " + expandedType);
    }
    
    private DocumentationPageInfo getPageInfo(TypePageTypeInfo expandedType) {
        final TypeName zenCodeName = expandedType.getZenCodeName();
        final Optional<TypePageInfo> pageInfoByName = documentRegistry.getPageInfoByName(zenCodeName);
        return pageInfoByName.orElseThrow(() -> new IllegalArgumentException("Invalid Expanded Type! " +zenCodeName));
    }
    
    private DocumentedStaticMembers getStaticMembers(TypeElement typeElement, DocumentationPageInfo pageInfo) {
        return staticMemberConverter.convertFor(typeElement, pageInfo);
    }
}
