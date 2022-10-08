package com.blamejared.crafttweaker.impl.script.recipefs;

import com.blamejared.crafttweaker.impl.script.ScriptRecipe;

import java.nio.file.FileStore;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;
import java.util.Collection;
import java.util.Objects;

final class RecipeFileStore extends FileStore {
    
    private final long size;
    
    RecipeFileStore(final Collection<ScriptRecipe> recipeList) {
        
        this.size = recipeList.stream().map(ScriptRecipe::getContent).mapToLong(String::length).sum();
    }
    
    @Override
    public String name() {
        
        return "JSON Recipes";
    }
    
    @Override
    public String type() {
        
        return "json";
    }
    
    @Override
    public boolean isReadOnly() {
        
        return true;
    }
    
    @Override
    public long getTotalSpace() {
        
        return this.size;
    }
    
    @Override
    public long getUsableSpace() {
        
        return this.size;
    }
    
    @Override
    public long getUnallocatedSpace() {
        
        return 0;
    }
    
    @Override
    public boolean supportsFileAttributeView(final Class<? extends FileAttributeView> type) {
        
        return Objects.requireNonNull(type) == BasicFileAttributeView.class;
    }
    
    @Override
    public boolean supportsFileAttributeView(final String name) {
        
        return "basic".equals(Objects.requireNonNull(name));
    }
    
    @Override
    public <V extends FileStoreAttributeView> V getFileStoreAttributeView(final Class<V> type) {
        
        Objects.requireNonNull(type);
        return null;
    }
    
    @Override
    public Object getAttribute(final String attribute) {
        
        Objects.requireNonNull(attribute);
        return null;
    }
    
}
