package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.enum_constant;

import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.DocumentMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.IFillMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;

public class EnumConstant implements IFillMeta {
    private final String name;
    private final DocumentationComment comment;

    public EnumConstant(String name, DocumentationComment comment) {
        this.name = name;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public DocumentationComment getComment() {
        return comment;
    }

    @Override
    public void fillMeta(DocumentMeta meta) {
        meta.addSearchTerms(name);
    }
}
