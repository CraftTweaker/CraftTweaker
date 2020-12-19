package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.page;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.DocumentedGenericParameter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.static_member.DocumentedStaticMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.DocumentedVirtualMembers;

import javax.annotation.Nullable;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public final class TypePage extends DocumentationPage {
    
    private final TypePageInfo pageInfo;
    
    @Nullable
    private final TypePage superType;
    private final List<TypePage> implementedTypes;
    private final List<DocumentedGenericParameter> genericParameters;
    
    public TypePage(DocumentationPageInfo pageInfo, DocumentedVirtualMembers members, TypePageInfo pageInfo1, @Nullable TypePage superType, List<TypePage> implementedTypes, DocumentedStaticMembers staticMembers, List<DocumentedGenericParameter> genericParameters) {
        super(pageInfo, members, staticMembers);
        this.pageInfo = pageInfo1;
        this.superType = superType;
        this.implementedTypes = implementedTypes;
        this.genericParameters = genericParameters;
    }
    
    @Override
    protected void writeTitle(PrintWriter writer) {
        if(genericParameters.isEmpty()) {
            writer.printf("# %s%n%n", pageInfo.zenCodeName.getSimpleName());
        } else {
            final String genericParameters = this.genericParameters.stream()
                    .map(DocumentedGenericParameter::formatForSignatureExample)
                    .collect(Collectors.joining(", "));
            writer.printf("# %s<%s>%n%n", pageInfo.zenCodeName.getSimpleName(), genericParameters);
        }
    }
    
    @Override
    protected void writeDescription(PrintWriter writer) {
        super.writeDescription(writer);
    }
    
    @Override
    protected void writeOwnerModId(PrintWriter writer) {
        writer.printf("This class was added by a mod with mod-id `%s`. So you need to have this mod installed if you want to use this feature.%n%n", pageInfo.declaringModId);
    }
    
    @Override
    protected void beforeWritingMembers(PrintWriter writer) {
        super.beforeWritingMembers(writer);
        writeImport(writer);
        writeSuperClass(writer);
        writeImplementedInterfaces(writer);
    }
    
    private void writeImport(PrintWriter writer) {
        writer.printf("## Importing the class%n%n");
        writer.println("It might be required for you to import the package if you encounter any issues (like casting an Array), so better be safe than sorry and add the import.");
        writer.println("```zenscript");
        writer.printf("import %s;%n", pageInfo.zenCodeName.getZenCodeName());
        writer.println("```");
        writer.println();
        writer.println();
    }
    
    private void writeSuperClass(PrintWriter writer) {
        if(superType == null) {
            return;
        }
        
        //TODO
        writer.println("TODO: Write superClass");
    }
    
    private void writeImplementedInterfaces(PrintWriter writer) {
        if(implementedTypes.isEmpty()) {
            return;
        }
        //TODO
        writer.println("TODO: Write Implemented interfaces");
    }
}
