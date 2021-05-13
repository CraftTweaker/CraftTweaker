package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment;

public class ParagraphMarkdownConverter {
    
    public String convertParagraphToMarkdown(final String docComment) {
        return docComment.replace("</p>", "").replace("<p>", "");
    }
    
}
