package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.Example;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.ExampleData;

import javax.annotation.Nullable;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;

public class CommentConverter {
    
    private final ProcessingEnvironment processingEnv;
    
    public CommentConverter(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }
    
    public DocumentationComment convertForType(TypeElement typeElement) {
        return convertElement(typeElement);
    }
    
    public DocumentationComment convertForMethod(ExecutableElement method, DocumentationPageInfo pageInfo) {
        final DocumentationComment comment = convertElement(method);
        fillExampleForThisParameterFromPageInfo(comment, pageInfo);
        return comment;
    }
    
    private void fillExampleForThisParameterFromPageInfo(DocumentationComment comment, DocumentationPageInfo pageInfo) {
        final ExampleData examples = comment.getExamples();
        if(!examples.hasExampleFor("this")) {
            final DocumentationComment classComment = pageInfo.getClassComment();
            final ExampleData pageInfoExampleData = classComment.getExamples();
            examples.addExample(pageInfoExampleData.getExampleFor("this"));
        }
    }
    
    private DocumentationComment convertElement(Element element) {
        final String docComment = processingEnv.getElementUtils().getDocComment(element);
        final String description = extractDescriptionFrom(docComment);
        final ExampleData exampleData = extractExampleDataFrom(docComment);
        
        return new DocumentationComment(description, exampleData);
    }
    
    @Nullable
    private String extractDescriptionFrom(@Nullable String docComment) {
        return docComment;
    }
    
    private ExampleData extractExampleDataFrom(String docComment) {
        return new ExampleData(new Example("this", "TODO"));
    }
    
    public DocumentationComment convertForParameter(VariableElement variableElement) {
        //1 Layer up -> Method
        return convertElement(variableElement.getEnclosingElement());
    }
    
    public DocumentationComment convertForTypeParameter(TypeParameterElement typeParameterElement) {
        //1 Layer up -> Method
        return convertElement(typeParameterElement.getEnclosingElement());
    }
}
