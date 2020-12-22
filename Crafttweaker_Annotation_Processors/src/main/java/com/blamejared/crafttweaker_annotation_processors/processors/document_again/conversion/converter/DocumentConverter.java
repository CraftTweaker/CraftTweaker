package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.mods.KnownModList;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.Example;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.ExampleData;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.page.DocumentationPage;
import com.blamejared.crafttweaker_annotations.annotations.Document;

import javax.lang.model.element.TypeElement;

public abstract class DocumentConverter {
    
    protected final KnownModList knownModList;
    protected final CommentConverter commentConverter;
    
    protected DocumentConverter(KnownModList knownModList, CommentConverter commentConverter) {
        this.knownModList = knownModList;
        this.commentConverter = commentConverter;
    }
    
    public abstract boolean canConvert(TypeElement typeElement);
    
    public abstract DocumentationPage convert(TypeElement typeElement, DocumentationPageInfo pageInfo);
    
    protected DocumentationPageInfo prepareConversion(TypeElement element) {
        final Document annotation = element.getAnnotation(Document.class);
        if(annotation == null) {
            throw new IllegalStateException("No Document annotation present!");
        }
        
        final String outputPath = annotation.value();
        final String declaringModId = knownModList.getModIdForPackage(element);
        return new DocumentationPageInfo(declaringModId, outputPath);
    }
    
    
    public void setDocumentationCommentTo(TypeElement typeElement, DocumentationPageInfo pageInfo) {
        final DocumentationComment typeComment = commentConverter.convertForType(typeElement);
        addThisInformationIfNotPresent(typeComment, typeElement);
        pageInfo.setTypeComment(typeComment);
    }
    
    private void addThisInformationIfNotPresent(DocumentationComment commentFor, TypeElement typeElement) {
        final ExampleData examples = commentFor.getExamples();
        if(!examples.hasExampleFor("this")) {
            final Example expandedTypeThisExample = getFallbackThisInformationFor(typeElement);
            examples.addExample(expandedTypeThisExample);
        }
    }
    
    protected abstract Example getFallbackThisInformationFor(TypeElement typeElement);
}
