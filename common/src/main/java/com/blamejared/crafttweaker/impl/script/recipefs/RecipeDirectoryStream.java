package com.blamejared.crafttweaker.impl.script.recipefs;

import com.blamejared.crafttweaker.impl.script.ScriptRecipe;

import java.io.IOException;
import java.nio.file.ClosedFileSystemException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

final class RecipeDirectoryStream implements DirectoryStream<Path> {
    
    private static final class RecipeDirectoryStreamIterator implements Iterator<Path> {
        
        private RecipeFileSystem fs;
        private Iterator<ScriptRecipe> children;
        private Filter<? super Path> filter;
        private RecipePath nextEntry;
        private boolean closed;
        
        RecipeDirectoryStreamIterator(final RecipeFileSystem fs, final Iterator<ScriptRecipe> children, final Filter<? super Path> filter) {
            
            this.fs = fs;
            this.children = children;
            this.filter = filter;
            this.nextEntry = null;
            this.closed = false;
        }
        
        @Override
        public boolean hasNext() {
            
            if(this.nextEntry != null) {
                return true;
            }
            try {
                this.computeNext();
            } catch(final IOException e) {
                throw new DirectoryIteratorException(e);
            }
            return this.nextEntry != null;
        }
        
        @Override
        public Path next() {
            
            if(!this.hasNext()) {
                throw new NoSuchElementException();
            }
            final Path next = this.nextEntry;
            this.nextEntry = null;
            return next;
        }
        
        void close() {
            
            this.fs = null;
            this.children = null;
            this.filter = null;
            this.nextEntry = null;
            this.closed = true;
        }
        
        private void computeNext() throws IOException {
            
            this.nextEntry = null;
            if(this.closed) {
                return;
            }
            while(this.children.hasNext()) {
                final ScriptRecipe next = this.children.next();
                final RecipePath path = (RecipePath) this.fs.getPath(next.getFileName()).toAbsolutePath().normalize();
                if(this.filter.accept(path)) {
                    this.nextEntry = path;
                    break;
                }
            }
        }
        
    }
    
    private RecipeDirectoryStreamIterator iterator;
    private boolean gaveIterator;
    private boolean closed;
    
    private RecipeDirectoryStream(final RecipeDirectoryStreamIterator iterator) {
        
        this.iterator = iterator;
        this.gaveIterator = false;
        this.closed = false;
    }
    
    static DirectoryStream<Path> of(final RecipeFileSystem fs, final RecipePath dir, final Collection<ScriptRecipe> recipes, final Filter<? super Path> filter) throws IOException {
        
        Objects.requireNonNull(fs);
        Objects.requireNonNull(dir);
        Objects.requireNonNull(recipes);
        Objects.requireNonNull(filter);
        
        final String dirName = dir.toAbsolutePath().normalize().toString();
        final Collection<ScriptRecipe> satisfactory = new ArrayList<>();
        
        for(final ScriptRecipe recipe : recipes) {
            final String name;
            if(dirName.equals(name = '/' + recipe.getFileName())) { // Recipe names are "normal", but we use absolute here, so we begin with /
                throw new NotDirectoryException(dirName);
            }
            if(name.startsWith(dirName)) {
                satisfactory.add(recipe);
            }
        }
        
        if(satisfactory.isEmpty()) {
            throw new NotDirectoryException(dirName);
        }
        
        final RecipeDirectoryStreamIterator iterator = new RecipeDirectoryStreamIterator(fs, satisfactory.iterator(), filter);
        return new RecipeDirectoryStream(iterator);
    }
    
    @Override
    public Iterator<Path> iterator() {
        
        if(this.closed) {
            throw new ClosedFileSystemException();
        }
        if(this.gaveIterator) {
            throw new IllegalStateException();
        }
        this.gaveIterator = true;
        return this.iterator;
    }
    
    @Override
    public void close() {
        
        if(this.closed) {
            return;
        }
        this.iterator.close();
        this.iterator = null;
        this.closed = true;
    }
    
}
