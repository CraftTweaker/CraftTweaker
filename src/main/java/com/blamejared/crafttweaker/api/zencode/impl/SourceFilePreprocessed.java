package com.blamejared.crafttweaker.api.zencode.impl;

import org.openzen.zencode.shared.*;

import java.io.*;
import java.util.*;

public class SourceFilePreprocessed implements SourceFile {
    
    private final String fileName;
    private List<String> fileContent;
    
    public SourceFilePreprocessed(String fileName, List<String> fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
    }
    
    @Override
    public String getFilename() {
        return fileName;
    }
    
    @Override
    public Reader open() {
        return new StringReader(String.join("\n", fileContent));
    }
    
    @Override
    public void update(String content) {
        fileContent = Arrays.asList(content.split("\n"));
    }
}
