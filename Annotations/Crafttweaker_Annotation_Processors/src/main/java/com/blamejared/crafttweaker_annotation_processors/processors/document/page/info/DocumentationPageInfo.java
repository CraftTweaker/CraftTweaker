package com.blamejared.crafttweaker_annotation_processors.processors.document.page.info;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;

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
        
        final int i = outputPath.lastIndexOf("/");
        if(i < 1) {
            return outputPath;
        }
        return outputPath.substring(i + 1);
    }
    
    public String getOutputPath() {
        
        return outputPath;
    }
    
    
    public boolean shouldOutput() {
        
        return true;
    }
    
    public void setTypeComment(DocumentationComment typeComment) {
        
        this.classComment = typeComment;
    }
    
}
