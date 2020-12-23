package com.blamejared.crafttweaker_annotation_processors.processors.document.file;

import com.blamejared.crafttweaker_annotation_processors.processors.document.DocumentRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.page.DocumentationPage;
import com.blamejared.crafttweaker_annotation_processors.processors.util.files.DeletingPathVisitor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class PageWriter {
    
    private static final String extension = ".md";
    
    private final DocumentRegistry documentRegistry;
    private final File outputDirectory;
    
    public PageWriter(DocumentRegistry documentRegistry, File outputDirectory) {
        this.documentRegistry = documentRegistry;
        this.outputDirectory = outputDirectory;
    }
    
    public void write() throws IOException {
        clearOutputDirectory();
        writePages();
    }
    
    private void clearOutputDirectory() throws IOException {
        ensureOutputDirectoryExists();
        Files.walkFileTree(outputDirectory.getAbsoluteFile().toPath(), new DeletingPathVisitor());
    }
    
    private void ensureOutputDirectoryExists() {
        if(!outputDirectory.exists() && !outputDirectory.mkdirs()) {
            throw new IllegalStateException("Could not create output directory");
        }
    }
    
    private void writePages() throws IOException {
        for(DocumentationPage page : documentRegistry.getAllPages()) {
            writePage(page);
        }
    }
    
    private void writePage(DocumentationPage page) throws IOException {
        final File file = new File(outputDirectory, page.pageInfo.getOutputPathWithExtension(extension));
        ensureDirectoryExistsFor(file);
        writePageInfoAt(page, file);
    }
    
    private void ensureDirectoryExistsFor(File file) {
        final File parentFile = file.getParentFile();
        if(!parentFile.exists() && !parentFile.mkdirs()) {
            throw new IllegalArgumentException("Could not create parent file for " + file);
        }
    }
    
    private void writePageInfoAt(DocumentationPage page, File file) throws IOException {
        try(final PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            page.write(writer);
        }
    }
}
