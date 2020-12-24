package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.examples.Example;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.examples.ExampleData;

import java.util.HashMap;
import java.util.Map;

public class CommentMerger {
    
    public DocumentationComment merge(DocumentationComment childComment, DocumentationComment parentComment) {
        final String description = mergeDescription(childComment);
        final ExampleData exampleData = mergeCommentExamples(childComment, parentComment);
        return new DocumentationComment(description, exampleData);
    }
    
    private String mergeDescription(DocumentationComment childComment) {
        return childComment.getOptionalDescription().orElse(null);
    }
    
    private ExampleData mergeCommentExamples(DocumentationComment childComment, DocumentationComment parentComment) {
        final ExampleData childExamples = childComment.getExamples();
        final ExampleData parentExamples = parentComment.getExamples();
        
        return mergeExampleData(childExamples, parentExamples);
    }
    
    private ExampleData mergeExampleData(ExampleData childExamples, ExampleData parentExamples) {
        final Map<String, Example> childExampleMap = childExamples.getExampleMap();
        final Map<String, Example> parentExampleMap = parentExamples.getExampleMap();
        final HashMap<String, Example> resultExampleMap = mergeExampleDataMap(childExampleMap, parentExampleMap);
        return new ExampleData(resultExampleMap);
    }
    
    private HashMap<String, Example> mergeExampleDataMap(Map<String, Example> childExampleMap, Map<String, Example> parentExampleMap) {
        final HashMap<String, Example> result = new HashMap<>(parentExampleMap);
        result.putAll(childExampleMap);
        
        return result;
    }
}
