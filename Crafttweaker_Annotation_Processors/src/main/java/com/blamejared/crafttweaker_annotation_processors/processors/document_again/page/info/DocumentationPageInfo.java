package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;

import java.io.File;

public class DocumentationPageInfo {
    
    public final String declaringModId;
    private final String outputPath;
    private DocumentationComment classComment;
    
    public DocumentationPageInfo(String declaringModId, String outputPath) {
        this.declaringModId = declaringModId;
        this.outputPath = outputPath;
        this.classComment = DocumentationComment.empty();
    }
    
    public DocumentationComment getClassComment() {
        return classComment;
    }
    
    public String getOutputPathWithExtension(String fileExtension) {
        if(outputPath.endsWith(fileExtension)) {
            return outputPath;
        }
        return outputPath + fileExtension;
    }
    
    public String getSimpleName() {
        final int i = outputPath.lastIndexOf(File.separator);
        return outputPath.substring(i);
    }
    
    public String getOutputPath() {
        return outputPath;
    }
    
    public void setTypeComment(DocumentationComment typeComment) {
        this.classComment = typeComment;
    }
}
