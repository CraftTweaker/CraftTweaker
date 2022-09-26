package com.blamejared.crafttweaker.impl.script.recipefs;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

final class RecipeFileAttributeView implements BasicFileAttributeView {
    
    private final RecipePath path;
    private final String name;
    
    RecipeFileAttributeView(final RecipePath path) {
        
        this.name = (this.path = path).toAbsolutePath().normalize().toString();
    }
    
    @Override
    public String name() {
        
        return "basic";
    }
    
    @Override
    public BasicFileAttributes readAttributes() throws IOException {
        
        return Files.readAttributes(this.path, BasicFileAttributes.class);
    }
    
    @Override
    public void setTimes(final FileTime lastModifiedTime, final FileTime lastAccessTime, final FileTime createTime) throws IOException {
        
        throw new AccessDeniedException(this.name, null, "Read only");
    }
    
}
