package com.blamejared.crafttweaker_annotation_processors.processors.document.file;

import com.blamejared.crafttweaker_annotation_processors.processors.document.DocumentRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.page.DocumentationPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DocsJsonWriter {
    
    private final File outputFile;
    private final DocumentRegistry registry;
    private final Navigation navigation = new Navigation();
    
    public DocsJsonWriter(File outputFile, DocumentRegistry registry) {
        this.outputFile = outputFile;
        this.registry = registry;
    }
    
    public void write() throws IOException {
        fillTableOfContent();
        writeTableOfContent();
    }
    
    private void writeTableOfContent() throws IOException {
        final File outputFile = new File(this.outputFile, "docs.json");
        try(final BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            final Gson gson = getGson();
            writer.write(gson.toJson(navigation));
        }
    }
    
    private Gson getGson() {
        return new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(TableOfContent.class, new TableOfContentTypeAdapter())
                .create();
    }
    
    private void fillTableOfContent() {
        for(DocumentationPage page : registry.getAllPages()) {
            navigation.add(page);
        }
    }
}
