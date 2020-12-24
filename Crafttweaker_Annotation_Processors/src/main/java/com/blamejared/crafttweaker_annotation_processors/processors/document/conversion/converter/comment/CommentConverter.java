package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.example.ExampleDataConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.header.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.examples.ExampleData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;

public class CommentConverter {
    
    private final ProcessingEnvironment processingEnv;
    private final CommentMerger commentMerger;
    private final ExampleDataConverter exampleDataConverter;
    private final DescriptionConverter descriptionConverter;
    private final ParameterDescriptionConverter parameterDescriptionConverter;
    
    public CommentConverter(ProcessingEnvironment processingEnv, CommentMerger commentMerger, ExampleDataConverter exampleDataConverter, DescriptionConverter descriptionConverter, ParameterDescriptionConverter parameterDescriptionConverter) {
        this.processingEnv = processingEnv;
        this.commentMerger = commentMerger;
        this.exampleDataConverter = exampleDataConverter;
        this.descriptionConverter = descriptionConverter;
        this.parameterDescriptionConverter = parameterDescriptionConverter;
    }
    
    public DocumentationComment convertForType(TypeElement typeElement) {
        return convertElement(typeElement);
    }
    
    public DocumentationComment convertForConstructor(ExecutableElement constructor, DocumentationPageInfo pageInfo) {
        final DocumentationComment comment = convertElement(constructor);
        fillExampleForThisParameterFromPageInfo(comment, pageInfo);
        return comment;
    }
    
    public DocumentationComment convertForMethod(ExecutableElement method, DocumentationPageInfo pageInfo) {
        final DocumentationComment comment = convertElement(method);
        return fillExampleForThisParameterFromPageInfo(comment, pageInfo);
    }
    
    public DocumentationComment convertForParameter(VariableElement variableElement) {
        final DocumentationComment parameterDescription = convertParameterDescription(variableElement);
        final DocumentationComment comment = convertElement(variableElement.getEnclosingElement());
        return mergeComments(parameterDescription, comment);
    }
    
    private DocumentationComment convertParameterDescription(Element element) {
        return parameterDescriptionConverter.convertDescriptionOf(element);
    }
    
    public DocumentationComment convertForTypeParameter(TypeParameterElement typeParameterElement) {
        final DocumentationComment parameterDescription = convertParameterDescription(typeParameterElement);
        final DocumentationComment comment = convertElement(typeParameterElement.getEnclosingElement());
        return mergeComments(parameterDescription, comment);
    }
    
    private DocumentationComment convertElement(Element element) {
        final DocumentationComment comment = getCommentForElement(element);
        final DocumentationComment enclosingElementComment = getCommentFromEnclosingElement(element);
        return mergeComments(comment, enclosingElementComment);
    }
    
    @Nonnull
    private DocumentationComment getCommentForElement(Element element) {
        final String docComment = processingEnv.getElementUtils().getDocComment(element);
        final String description = extractDescriptionFrom(docComment, element);
        final ExampleData exampleData = extractExampleDataFrom(docComment, element);
        
        return new DocumentationComment(description, exampleData);
    }
    
    @Nullable
    private String extractDescriptionFrom(@Nullable String docComment, Element element) {
        return descriptionConverter.convertFromCommentString(docComment, element);
    }
    
    private ExampleData extractExampleDataFrom(String docComment, Element element) {
        return exampleDataConverter.convertFromCommentString(docComment, element);
    }
    
    private DocumentationComment getCommentFromEnclosingElement(Element element) {
        final Element enclosingElement = element.getEnclosingElement();
        if(enclosingElement == null) {
            return DocumentationComment.empty();
        }
        
        return convertElement(enclosingElement);
    }
    
    private DocumentationComment mergeComments(DocumentationComment comment, DocumentationComment enclosingElementComment) {
        return commentMerger.merge(comment, enclosingElementComment);
    }
    
    private DocumentationComment fillExampleForThisParameterFromPageInfo(DocumentationComment comment, DocumentationPageInfo pageInfo) {
        final DocumentationComment classComment = pageInfo.getClassComment();
        return mergeComments(comment, classComment);
    }
}
