package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.page;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.static_member.DocumentedStaticMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member.DocumentedVirtualMembers;

import java.io.PrintWriter;

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
        writeTitle(writer);
        writeDescription(writer);
        writeOwnerModId(writer);
        beforeWritingMembers(writer);
        writeMembers(writer);
    }
    
    protected abstract void writeTitle(PrintWriter writer);
    
    protected void writeDescription(PrintWriter writer) {
        pageInfo.getClassComment().ifPresent(writer::println);
    }
    
    protected abstract void writeOwnerModId(PrintWriter writer);
    
    protected void beforeWritingMembers(PrintWriter writer) {
    }
    
    protected void writeMembers(PrintWriter writer) {
        staticMembers.write(writer);
        virtualMembers.write(writer);
    }
}
