package com.blamejared.crafttweaker.impl.preprocessor.onlyif;

import org.openzen.zencode.shared.SourceFile;

import java.io.IOException;
import java.io.Reader;

@SuppressWarnings("ClassCanBeRecord")
final class FakeSourceFile implements SourceFile {
    
    private final String fileName;
    
    FakeSourceFile(final String fileName) {
        
        this.fileName = fileName;
    }
    
    @Override
    public String getFilename() {
        
        return this.fileName;
    }
    
    @Override
    public Reader open() throws IOException {
        
        throw new IOException();
    }
    
    @Override
    public void update(final String content) throws IOException {
        
        throw new IOException();
    }
    
}
