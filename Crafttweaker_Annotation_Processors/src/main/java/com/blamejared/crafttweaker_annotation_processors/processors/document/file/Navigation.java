package com.blamejared.crafttweaker_annotation_processors.processors.document.file;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.page.DocumentationPage;
import com.google.gson.annotations.SerializedName;

public class Navigation {
    @SerializedName("nav")
    private final TableOfContent tableOfContent = new TableOfContent();
    
    public void add(DocumentationPage page) {
        tableOfContent.add(page);
    }
}
