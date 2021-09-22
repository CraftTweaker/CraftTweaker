package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.event.EventDataConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.example.ExampleDataConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.meta.MetaConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.header.ParameterDescriptionConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.MetaData;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.examples.ExampleData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;

public class CommentConverter {
    
    private final ProcessingEnvironment processingEnv;
    private final CommentMerger commentMerger;
    private final ExampleDataConverter exampleDataConverter;
    private final MetaConverter metaDataConverter;
    private final DescriptionConverter descriptionConverter;
    private final DeprecationFinder deprecationFinder;
    private final SinceInformationIdentifier sinceIdentifier;
    private final ParameterDescriptionConverter parameterDescriptionConverter;
    private final EventDataConverter eventDataConverter;
    
    public CommentConverter(final ProcessingEnvironment processingEnv, final CommentMerger commentMerger,
                            final ExampleDataConverter exampleDataConverter, final MetaConverter metaConverter,
                            final DescriptionConverter descriptionConverter,
                            final ParameterDescriptionConverter parameterDescriptionConverter,
                            final EventDataConverter eventDataConverter, final DeprecationFinder deprecationFinder,
                            final SinceInformationIdentifier sinceIdentifier) {
        
        this.processingEnv = processingEnv;
        this.commentMerger = commentMerger;
        this.exampleDataConverter = exampleDataConverter;
        this.metaDataConverter = metaConverter;
        this.descriptionConverter = descriptionConverter;
        this.parameterDescriptionConverter = parameterDescriptionConverter;
        this.eventDataConverter = eventDataConverter;
        this.deprecationFinder = deprecationFinder;
        this.sinceIdentifier = sinceIdentifier;
    }
    
    public DocumentationComment convertForType(TypeElement typeElement) {
        
        DocumentationComment documentationComment = convertElement(typeElement);
        if(typeElement.getSimpleName().toString().endsWith("Event")) {
            return fastMergeComments(documentationComment, covertEvent(typeElement));
        }
        return documentationComment;
    }
    
    public DocumentationComment convertForConstructor(ExecutableElement constructor, DocumentationPageInfo pageInfo) {
        
        final DocumentationComment comment = convertElement(constructor, pageInfo.getClassComment());
        fillExampleForThisParameterFromPageInfo(comment, pageInfo);
        return comment;
    }
    
    public DocumentationComment convertForMethod(ExecutableElement method, DocumentationPageInfo pageInfo) {
        
        final DocumentationComment comment = convertElement(method, pageInfo.getClassComment());
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
        
        return convertElement(element, DocumentationComment.empty());
    }
    
    public DocumentationComment convertElement(Element element, DocumentationComment parent) {
        
        DocumentationComment comment = getCommentForElement(element);
        comment = mergeComments(comment, parent);
        
        final DocumentationComment enclosingElementComment = getCommentFromEnclosingElement(element);
        return mergeComments(comment, enclosingElementComment);
    }
    
    private DocumentationComment covertEvent(Element element) {
        
        return eventDataConverter.getDocumentationComment(processingEnv.getElementUtils()
                .getDocComment(element), element);
    }
    
    @Nonnull
    private DocumentationComment getCommentForElement(Element element) {
        
        final String docComment = processingEnv.getElementUtils().getDocComment(element);
        final String description = extractDescriptionFrom(docComment, element);
        // TODO: Handle @apiNote
        final String deprecation = extractDeprecationFrom(docComment, element);
        final String sinceVersion = extractSinceFrom(docComment, element);
        final ExampleData exampleData = extractExampleDataFrom(docComment, element);
        final MetaData metaData = extractMetaDataFrom(docComment, element);
        return new DocumentationComment(description, deprecation, sinceVersion, exampleData, metaData);
    }
    
    @Nullable
    private String extractDescriptionFrom(@Nullable String docComment, Element element) {
        
        return descriptionConverter.convertFromCommentString(docComment, element);
    }
    
    @Nullable
    private String extractDeprecationFrom(@Nullable final String docComment, final Element element) {
        
        return this.deprecationFinder.findInCommentString(docComment, element);
    }
    
    @Nullable
    private String extractSinceFrom(@Nullable final String docComment, final Element element) {
        
        return this.sinceIdentifier.findInCommentString(docComment, element);
    }
    
    private ExampleData extractExampleDataFrom(String docComment, Element element) {
        
        return exampleDataConverter.convertFromCommentString(docComment, element);
    }
    
    private MetaData extractMetaDataFrom(String docComment, Element element) {
        
        return metaDataConverter.convertFromCommentString(docComment, element);
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
    
    private DocumentationComment fastMergeComments(DocumentationComment firstWithExamples, DocumentationComment second) {
        
        return fastMergeWithDescription(
                firstWithExamples,
                second,
                firstWithExamples.getOptionalDescription().map(it -> it + "\n\n").orElse("") + second.getDescription()
        );
    }
    
    @SuppressWarnings("unused") // second may be used later
    private DocumentationComment fastMergeWithDescription(final DocumentationComment first, final DocumentationComment second, final String description) {
        
        return new DocumentationComment(description, first.getDeprecationMessage(), first.getSinceVersion(), first.getExamples(), first.getMetaData());
    }
    
    private DocumentationComment fillExampleForThisParameterFromPageInfo(DocumentationComment comment, DocumentationPageInfo pageInfo) {
        
        final DocumentationComment classComment = pageInfo.getClassComment();
        return mergeComments(comment, classComment);
    }
    
}
