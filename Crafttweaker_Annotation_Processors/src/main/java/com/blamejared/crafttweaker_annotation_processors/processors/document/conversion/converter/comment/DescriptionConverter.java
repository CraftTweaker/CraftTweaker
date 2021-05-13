package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter.CodeTagReplacer;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.documentation_parameter.ParameterRemover;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.linktag.LinkTagReplacer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;

public class DescriptionConverter {
    
    private final LinkTagReplacer linkTagReplacer;
    private final CodeTagReplacer codeTagReplacer;
    private final ParagraphMarkdownConverter paragraphMarkdownConverter;
    private final ParameterRemover parameterRemover;
    
    public DescriptionConverter(LinkTagReplacer linkTagReplacer, CodeTagReplacer codeTagReplacer,
                                ParagraphMarkdownConverter paragraphMarkdownConverter, ParameterRemover parameterRemover) {
        this.linkTagReplacer = linkTagReplacer;
        this.codeTagReplacer = codeTagReplacer;
        this.paragraphMarkdownConverter = paragraphMarkdownConverter;
        this.parameterRemover = parameterRemover;
    }
    
    @Nullable
    public String convertFromCommentString(@Nullable String docComment, Element element) {
        if(docComment == null) {
            return null;
        }
        
        return convertNonNullCommentString(docComment, element);
    }
    
    @Nonnull
    private String convertNonNullCommentString(String docComment, Element element) {
        docComment = replaceLinkTagsWithClickableMarkdown(docComment, element);
        docComment = replaceCodeTagsWithCodeSections(docComment);
        docComment = convertParagraphsToMarkdownFormatFromHtml(docComment);
        // TODO: Convert tables
        docComment = removeDocumentationParameters(docComment);
        return docComment.trim();
    }
    
    private String replaceLinkTagsWithClickableMarkdown(String docComment, Element element) {
        return linkTagReplacer.replaceLinkTagsFrom(docComment, element);
    }
    
    private String replaceCodeTagsWithCodeSections(String docComment) {
        return this.codeTagReplacer.replaceCodeTags(docComment);
    }
    
    private String convertParagraphsToMarkdownFormatFromHtml(String docComment) {
        return this.paragraphMarkdownConverter.convertParagraphToMarkdown(docComment);
    }
    
    private String removeDocumentationParameters(String docComment) {
        return parameterRemover.removeDocumentationParametersFrom(docComment);
    }
    
    
}
