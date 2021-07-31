package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageOutputWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.IFillMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.TypePageMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members.CasterMemberMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CasterMember extends AbstractVirtualMember implements Comparable<CasterMember>, IFillMeta<CasterMemberMeta> {
    
    private final boolean isImplicit;
    
    public CasterMember(MemberHeader header, @Nullable DocumentationComment description, boolean isImplicit) {
        super(header, description);
        this.isImplicit = isImplicit;
    }
    
    @Override
    public int compareTo(@Nonnull CasterMember o) {
        return this.header.returnType.compareTo(o.header.returnType);
    }
    
    public void writeTableRow(PageOutputWriter writer) {
        writer.printf("| %s | %s |%n", header.returnType.getClickableMarkdown(), isImplicit);
    }
    
    @Override
    public void fillMeta(CasterMemberMeta meta) {
        meta.setImplicit(isImplicit);
        meta.setReturnType(new TypePageMeta(header.returnType));
    }
    
}
