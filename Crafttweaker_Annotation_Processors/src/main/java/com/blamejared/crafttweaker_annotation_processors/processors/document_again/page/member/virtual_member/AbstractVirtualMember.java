package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.MemberHeader;

public abstract class AbstractVirtualMember {
    
    public final MemberHeader header;
    private final DocumentationComment comment;
    
    protected AbstractVirtualMember(MemberHeader header, DocumentationComment comment) {
        this.header = header;
        this.comment = comment;
    }
    
    
    public DocumentationComment getComment() {
        return comment;
    }
}
