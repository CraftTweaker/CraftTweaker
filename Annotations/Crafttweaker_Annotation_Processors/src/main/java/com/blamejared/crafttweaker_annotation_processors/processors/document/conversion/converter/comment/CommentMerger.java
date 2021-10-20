package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment;

import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.MetaData;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.examples.Example;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.examples.ExampleData;

import java.util.HashMap;
import java.util.Map;

public class CommentMerger {
    
    public DocumentationComment merge(DocumentationComment childComment, DocumentationComment parentComment) {
        
        final String description = mergeDescription(childComment);
        final String deprecation = mergeDeprecation(childComment, parentComment);
        final String sinceVersion = mergeSince(childComment, parentComment);
        final ExampleData exampleData = mergeCommentExamples(childComment, parentComment);
        // We don't care about the parent description.
        final MetaData metaData = childComment.getMetaData();
        
        return new DocumentationComment(description, deprecation, sinceVersion, exampleData, metaData);
    }
    
    private String mergeDescription(DocumentationComment childComment) {
        
        return childComment.getOptionalDescription().orElse(null);
    }
    
    private String mergeDeprecation(final DocumentationComment child, final DocumentationComment parent) {
        // TODO("Does it make sense to mark the child as deprecated if the parent is?")
        return child.getOptionalDeprecationMessage().orElseGet(parent::getDeprecationMessage);
    }
    
    private String mergeSince(final DocumentationComment child, final DocumentationComment parent) {
        
        return child.getOptionalSince().orElseGet(parent::getSinceVersion);
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
