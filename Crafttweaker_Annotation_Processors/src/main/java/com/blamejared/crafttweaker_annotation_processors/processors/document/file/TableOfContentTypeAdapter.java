package com.blamejared.crafttweaker_annotation_processors.processors.document.file;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.page.DocumentationPage;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;

public class TableOfContentTypeAdapter extends TypeAdapter<TableOfContent> {
    
    
    @Override
    public void write(JsonWriter out, TableOfContent value) throws IOException {
        out.beginObject();
        writeSameLevelFiles(out, value);
        writeNextLevel(out, value);
        out.endObject();
    }
    
    private void writeNextLevel(JsonWriter out, TableOfContent value) throws IOException {
        final SortedMap<String, TableOfContent> subEntries = value.getSubEntries();
        for(Map.Entry<String, TableOfContent> nextLevelEntry : subEntries.entrySet()) {
            final String name = nextLevelEntry.getKey();
            final TableOfContent nextLevel = nextLevelEntry.getValue();
            
            out.name(name);
            write(out, nextLevel);
        }
    }
    
    private void writeSameLevelFiles(JsonWriter out, TableOfContent value) throws IOException {
        final SortedSet<DocumentationPage> pagesAtThisLevel = value.getPagesAtThisLevel();
        for(DocumentationPage documentationPage : pagesAtThisLevel) {
            writePage(out, documentationPage);
        }
    }
    
    private void writePage(JsonWriter out, DocumentationPage documentationPage) throws IOException {
        final String name = documentationPage.pageInfo.getSimpleName();
        final String path = documentationPage.pageInfo.getOutputPathWithExtension(PageWriter.MARKDOWN_EXTENSION);
        
        out.name(name);
        out.value(path);
    }
    
    @Override
    public TableOfContent read(JsonReader in) {
        throw new UnsupportedOperationException("Not needed here");
    }
}
