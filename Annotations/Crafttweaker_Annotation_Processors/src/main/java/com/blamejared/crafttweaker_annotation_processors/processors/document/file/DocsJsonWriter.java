package com.blamejared.crafttweaker_annotation_processors.processors.document.file;

import com.blamejared.crafttweaker_annotation_processors.processors.document.DocumentRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.page.DocumentationPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class DocsJsonWriter {
    
    private final File outputFile;
    private final boolean multiSourceProject;
    private final DocumentRegistry registry;
    private final Navigation navigation = new Navigation();
    
    public DocsJsonWriter(File outputFile, boolean multiSourceProject, DocumentRegistry registry) {
        
        this.outputFile = outputFile;
        this.multiSourceProject = multiSourceProject;
        this.registry = registry;
    }
    
    public void write() throws IOException {
        
        fillTableOfContent();
        writeTableOfContent();
    }
    
    private void writeTableOfContent() throws IOException {
        
        final File outputFile = new File(this.outputFile, "docs.json");
        final Gson gson = getGson();
        Optional<JsonObject> originalNav = Optional.empty();
        if(multiSourceProject && outputFile.exists()) {
            try(final BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
                
                originalNav = Optional.of(gson.fromJson(reader, JsonObject.class));
            }
        }
        try(final BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            JsonObject jsonNav = gson.toJsonTree(navigation).getAsJsonObject();
            originalNav.ifPresent(jsonObject -> deepMerge(jsonObject, jsonNav));
            writer.write(jsonNav.toString());
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
    
    
    /**
     * Merge "source" into "target". If fields have equal name, merge them recursively.
     * Null values in source will remove the field from the target.
     * Override target values with source values
     * Keys not supplied in source will remain unchanged in target
     *
     * @return the merged object (target).
     */
    public static JsonObject deepMerge(JsonObject source, JsonObject target) {
        
        for(Map.Entry<String, JsonElement> sourceEntry : source.entrySet()) {
            String key = sourceEntry.getKey();
            JsonElement value = sourceEntry.getValue();
            if(!target.has(key)) {
                //target does not have the same key, so perhaps it should be added to target
                if(!value.isJsonNull()) //well, only add if the source value is not null
                {
                    target.add(key, value);
                }
            } else {
                if(!value.isJsonNull()) {
                    if(value.isJsonObject()) {
                        //source value is json object, start deep merge
                        deepMerge(value.getAsJsonObject(), target.get(key).getAsJsonObject());
                    } else {
                        target.add(key, value);
                    }
                } else {
                    target.remove(key);
                }
            }
        }
        return target;
    }
    
    
}
