package com.blamejared.crafttweaker_annotation_processors.processors.document.file;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.page.DocumentationPage;

import java.util.*;

public class TableOfContent {
    
    private final SortedMap<String, TableOfContent> subEntries = new TreeMap<>();
    private final SortedSet<DocumentationPage> pagesAtThisLevel = new TreeSet<>(comparingOutputPath());
    
    private static Comparator<DocumentationPage> comparingOutputPath() {
        return Comparator.comparing(page -> page.pageInfo.getOutputPath());
    }
    
    public void add(DocumentationPage page) {
        final String outputPath = page.pageInfo.getOutputPathWithExtension(PageWriter.MARKDOWN_EXTENSION);
        final List<String> split = new LinkedList<>(Arrays.asList(outputPath.split("/")));
        add(split, page);
    }
    
    public void add(List<String> levels, DocumentationPage page) {
        if(isAtThisLevel(levels)) {
            addAtThisLevel(page);
        } else {
            addAtNextLevel(levels, page);
        }
    }
    
    private boolean isAtThisLevel(List<String> levels) {
        return levels.size() <= 1;
    }
    
    private void addAtNextLevel(List<String> levels, DocumentationPage page) {
        final String nextLevel = capitalize(levels.remove(0));
        final TableOfContent nextLevelTableOfContent = this.subEntries.computeIfAbsent(nextLevel, ignored -> new TableOfContent());
        nextLevelTableOfContent.add(levels, page);
    }
    
    private String capitalize(String name) {
        if(name.isEmpty()) {
            return name;
        }
        
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
    
    private void addAtThisLevel(DocumentationPage page) {
        this.pagesAtThisLevel.add(page);
    }
    
    public SortedMap<String, TableOfContent> getSubEntries() {
        return subEntries;
    }
    
    public SortedSet<DocumentationPage> getPagesAtThisLevel() {
        return pagesAtThisLevel;
    }
}
