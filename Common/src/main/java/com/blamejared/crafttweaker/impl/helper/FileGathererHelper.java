package com.blamejared.crafttweaker.impl.helper;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.function.Consumer;

public final class FileGathererHelper extends SimpleFileVisitor<Path> {
    
    private final PathMatcher matcher;
    private final Consumer<Path> onFileDiscovered;
    
    private FileGathererHelper(final PathMatcher matcher, final Consumer<Path> onFileDiscovered) {
        
        this.matcher = matcher;
        this.onFileDiscovered = onFileDiscovered;
    }
    
    public static FileVisitor<Path> of(final PathMatcher matcher, final Consumer<Path> onFileDiscovered) {
        
        Objects.requireNonNull(matcher, "Unable to create a visitor without a path matcher");
        Objects.requireNonNull(onFileDiscovered, "Unable to create visitor without consumer");
        return new FileGathererHelper(matcher, onFileDiscovered);
    }
    
    public static FileVisitor<Path> of(final Consumer<Path> onFileDiscovered) {
        
        return of(it -> true, onFileDiscovered);
    }
    
    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
        
        final FileVisitResult parent = super.visitFile(file, attrs);
        
        if(attrs.isRegularFile() && this.matcher.matches(file)) {
            this.onFileDiscovered.accept(file);
        }
        
        return parent;
    }
    
}
