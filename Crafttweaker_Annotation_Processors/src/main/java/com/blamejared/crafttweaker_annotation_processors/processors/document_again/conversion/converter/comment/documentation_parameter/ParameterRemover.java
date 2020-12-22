package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.documentation_parameter;

public class ParameterRemover {
    
    public String removeDocumentationParametersFrom(String docComment) {
        if(!hasDocumentationParameters(docComment)) {
            return docComment;
        }
        
        final int index = getIndexOfFirstDocumentationParameter(docComment);
        return docComment.substring(0, index);
    }
    
    private boolean hasDocumentationParameters(String docComment) {
        final int index = getIndexOfFirstDocumentationParameter(docComment);
        return index > 0;
    }
    
    private int getIndexOfFirstDocumentationParameter(String docComment) {
        return docComment.indexOf('@');
    }
}
