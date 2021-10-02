package com.blamejared.crafttweaker_annotation_processors.processors.document.page.page;

import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageOutputWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.DocumentMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.IFillMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member.DocumentedStaticMembers;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member.DocumentedVirtualMembers;
import com.google.gson.Gson;

import java.util.Optional;

public abstract class DocumentationPage implements IFillMeta {
    
    public final DocumentationPageInfo pageInfo;
    protected final DocumentedVirtualMembers virtualMembers;
    protected final DocumentedStaticMembers staticMembers;
    
    protected DocumentationPage(DocumentationPageInfo pageInfo, DocumentedVirtualMembers virtualMembers, DocumentedStaticMembers staticMembers) {
        
        this.pageInfo = pageInfo;
        this.virtualMembers = virtualMembers;
        this.staticMembers = staticMembers;
    }
    
    public void write(PageOutputWriter writer) {
        
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
    
    public void writeMeta(PageOutputWriter writer, Gson gson) {
        
        DocumentMeta meta = new DocumentMeta();
        meta.setSince(this.pageInfo.getClassComment().getSinceVersion());
        meta.setDeprecation(this.pageInfo.getClassComment().getDeprecationMessage());
        meta.setPath(this.pageInfo.getOutputPathWithExtension(PageWriter.MARKDOWN_EXTENSION));
        meta.setShortDescription(pageInfo.getClassComment().getMetaData().getShortDescription());
        fillMeta(meta);
        virtualMembers.fillMeta(meta);
        staticMembers.fillMeta(meta);
        writer.println(gson.toJson(meta));
    }
    
    protected void writeSince(final PageOutputWriter writer) {
        
        writer.pageSince(this.pageInfo.getClassComment().getSinceVersion());
    }
    
    protected abstract void writeTitle(PageOutputWriter writer);
    
    protected void writeDeprecation(final PageOutputWriter writer) {
        
        writer.deprecationMessage(this.pageInfo.getClassComment().getDeprecationMessage());
    }
    
    protected void writeDescription(PageOutputWriter writer) {
        
        final Optional<String> description = pageInfo.getClassComment().getOptionalDescription();
        
        if(description.isPresent()) {
            writer.println(description.get());
            writer.println();
        }
    }
    
    protected abstract void writeOwnerModId(PageOutputWriter writer);
    
    protected void beforeWritingMembers(PageOutputWriter writer) {
    
    }
    
    protected void writeMembers(PageOutputWriter writer) {
        
        staticMembers.write(writer);
        virtualMembers.write(writer);
    }
    
}
