package com.blamejared.crafttweaker_annotation_processors.processors.util.files;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class DeletingPathVisitor extends SimpleFileVisitor<Path> {
    
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        
        Files.delete(file);
        return super.visitFile(file, attrs);
    }
    
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        
        Files.delete(dir);
        return super.postVisitDirectory(dir, exc);
    }
    
}
