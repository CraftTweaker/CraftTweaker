package com.blamejared.crafttweaker_annotation_processors.processors.document.page.page;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member.DocumentedStaticMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.DocumentedVirtualMembers;

import java.io.PrintWriter;
import java.util.Optional;

public abstract class DocumentationPage {
    
    public final DocumentationPageInfo pageInfo;
    protected final DocumentedVirtualMembers virtualMembers;
    protected final DocumentedStaticMembers staticMembers;
    
    protected DocumentationPage(DocumentationPageInfo pageInfo, DocumentedVirtualMembers virtualMembers, DocumentedStaticMembers staticMembers) {
        this.pageInfo = pageInfo;
        this.virtualMembers = virtualMembers;
        this.staticMembers = staticMembers;
    }
    
    public void write(PrintWriter writer) {
        writeSince(writer);
        writeTitle(writer);
        writeDeprecation(writer);
        writeDescription(writer);
        if(!pageInfo.declaringModId.equals("crafttweaker")) {
            writeOwnerModId(writer);
        }
        beforeWritingMembers(writer);
        writeMembers(writer);
    }
    
    protected void writeSince(final PrintWriter writer) {
        if (!this.pageInfo.getClassComment().getOptionalSince().isPresent()) return;
        
        writer.printf("::since{version=%s}%n", this.pageInfo.getClassComment().getSinceVersion());
    }
    
    protected abstract void writeTitle(PrintWriter writer);
    
    protected void writeDeprecation(final PrintWriter writer) {
        if (!this.pageInfo.getClassComment().isDeprecated()) return;
        
        writer.printf(":::warnBox%n%n");
        writer.write(this.pageInfo.getClassComment().getDeprecationMessage());
        writer.printf("%n:::%n%n");
    }
    
    protected void writeDescription(PrintWriter writer) {
        final Optional<String> description = pageInfo.getClassComment().getOptionalDescription();
        
        if(description.isPresent()) {
            writer.println(description.get());
            writer.println();
        }
    }
    
    protected abstract void writeOwnerModId(PrintWriter writer);
    
    protected void beforeWritingMembers(PrintWriter writer) {
    }
    
    protected void writeMembers(PrintWriter writer) {
        staticMembers.write(writer);
        virtualMembers.write(writer);
    }
}
