package com.blamejared.crafttweaker_annotation_processors.processors.document.page.page;

import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageOutputWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.DocumentMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.DocumentedGenericParameter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member.DocumentedStaticMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.DocumentedVirtualMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.TypePageTypeInfo;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public final class TypePage extends DocumentationPage {
    
    final TypePageInfo typePageInfo;
    
    @Nullable
    private final AbstractTypeInfo superType;
    private final List<AbstractTypeInfo> implementedTypes;
    private final List<DocumentedGenericParameter> genericParameters;
    
    public TypePage(TypePageInfo typePageInfo, DocumentedVirtualMembers members, @Nullable AbstractTypeInfo superType, List<AbstractTypeInfo> implementedTypes, DocumentedStaticMembers staticMembers, List<DocumentedGenericParameter> genericParameters) {
        
        super(typePageInfo, members, staticMembers);
        this.typePageInfo = typePageInfo;
        this.superType = superType;
        this.implementedTypes = implementedTypes;
        this.genericParameters = genericParameters;
    }
    
    @Override
    protected void writeTitle(PageOutputWriter writer) {
        
        if(genericParameters.isEmpty()) {
            writer.printf("# %s%n%n", getSimpleName());
        } else {
            final String genericParameters = this.genericParameters.stream()
                    .map(DocumentedGenericParameter::formatForSignatureExample)
                    .collect(Collectors.joining(", "));
            writer.printf("# %s&LT;%s&GT;%n%n", getSimpleName(), genericParameters);
        }
    }
    
    @Override
    protected void writeOwnerModId(PageOutputWriter writer) {
        
        writer.printf("This class was added by a mod with mod-id `%s`. So you need to have this mod installed if you want to use this feature.%n%n", typePageInfo.declaringModId);
    }
    
    @Override
    protected void beforeWritingMembers(PageOutputWriter writer) {
        
        super.beforeWritingMembers(writer);
        writeImport(writer);
        writeSuperClass(writer);
        writeImplementedInterfaces(writer);
    }
    
    private void writeImport(PageOutputWriter writer) {
        
        writer.printf("## Importing the class%n%n");
        writer.println("It might be required for you to import the package if you encounter any issues (like casting an Array), so better be safe than sorry and add the import at the very top of the file.");
        writer.zenBlock(() -> writer.printf("import %s;%n", typePageInfo.zenCodeName.getZenCodeName()));
        writer.println();
        writer.println();
    }
    
    private void writeSuperClass(PageOutputWriter writer) {
        
        if(superType == null) {
            return;
        }
        
        writer.printf("## Extending %s%n%n", superType.getSimpleMarkdown());
        final String simpleName = getSimpleName();
        final String superTypeMarkDown = superType.getClickableMarkdown();
        
        final String format = "%1$s extends %2$s. That means all methods available in %2$s are also available in %1$s%n%n";
        writer.printf(format, simpleName, superTypeMarkDown);
    }
    
    private void writeImplementedInterfaces(PageOutputWriter writer) {
        
        if(implementedTypes.isEmpty()) {
            return;
        }
        
        writer.println("## Implemented Interfaces");
        final String format = "%1$s implements the following interfaces. That means all methods defined in these interfaces are also available in %1$s%n%n";
        writer.printf(format, getSimpleName());
        
        for(AbstractTypeInfo implementedType : implementedTypes) {
            writer.printf("- %s%n", implementedType.getClickableMarkdown());
        }
        writer.println();
    }
    
    
    @Override
    public void fillMeta(DocumentMeta meta) {
        
        meta.setZenCodeName(typePageInfo.zenCodeName.getZenCodeName());
        meta.setOwnerModId(typePageInfo.declaringModId);
        if(superType instanceof TypePageTypeInfo) {
            String zenCodeName = ((TypePageTypeInfo) superType).getZenCodeName().getZenCodeName();
            meta.addSearchTerms(zenCodeName);
            meta.setZenCodeName(zenCodeName);
        }
        if(!implementedTypes.isEmpty()) {
            for(AbstractTypeInfo implementedType : implementedTypes) {
                if(implementedType instanceof TypePageTypeInfo) {
                    // Determine if we should include parents in the search terms.
                    meta.addSearchTerms(((TypePageTypeInfo) implementedType).getZenCodeName().getZenCodeName());
                }
            }
        }
    }
    
    private String getSimpleName() {
        
        return typePageInfo.getSimpleName();
    }
    
}
