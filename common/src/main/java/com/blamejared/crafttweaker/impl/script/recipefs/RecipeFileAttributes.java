package com.blamejared.crafttweaker.impl.script.recipefs;

import com.blamejared.crafttweaker.api.util.GenericUtil;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

final class RecipeFileAttributes implements BasicFileAttributes {
    
    private final FileTime fsCreationMoment;
    private final RecipeFsResolver.Bound resolver;
    private Map<String, Object> attributes;
    
    RecipeFileAttributes(final FileTime fsCreationMoment, final RecipeFsResolver.Bound resolver) {
        
        this.fsCreationMoment = fsCreationMoment;
        this.resolver = resolver;
        this.attributes = null;
    }
    
    @Override
    public FileTime lastModifiedTime() {
        
        return this.fsCreationMoment;
    }
    
    @Override
    public FileTime lastAccessTime() {
        
        return this.fsCreationMoment;
    }
    
    @Override
    public FileTime creationTime() {
        
        return this.fsCreationMoment;
    }
    
    @Override
    public boolean isRegularFile() {
        
        try {
            this.resolver.resolve();
            return true;
        } catch(final IOException e) {
            return false;
        }
    }
    
    @Override
    public boolean isDirectory() {
        
        return !this.isRegularFile();
    }
    
    @Override
    public boolean isSymbolicLink() {
        
        return false;
    }
    
    @Override
    public boolean isOther() {
        
        return false;
    }
    
    @Override
    public long size() {
        
        try {
            return this.resolver.resolveContents().length();
        } catch(final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    @Override
    public Object fileKey() {
        
        return null;
    }
    
    Map<String, Object> asMap() {
        
        if(this.attributes == null) {
            this.attributes = this.compute();
        }
        return this.attributes;
    }
    
    private Map<String, Object> compute() {
        
        return Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(it -> Modifier.isPublic(it.getModifiers()))
                .collect(Collectors.toMap(Method::getName, this::invoke));
    }
    
    private <T> T invoke(final Method it) {
        
        try {
            return GenericUtil.uncheck(it.invoke(this));
        } catch(final ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
    
}
