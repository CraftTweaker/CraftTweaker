package com.blamejared.crafttweaker.api.zencode.impl.preprocessors.snipping;

import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;
import org.openzen.zencode.shared.CodePosition;

import java.util.ArrayList;
import java.util.List;

final class SnippingMatch {
    
    private final CodePosition start;
    private final SnippingMatch parent;
    private final List<SnippingMatch> children;
    private final String name;
    private final SnippingParameterHit parameterHit;
    private CodePosition end;
    
    SnippingMatch(CodePosition start, String name, SnippingMatch parent, SnippingParameterHit parameterHit) {
        
        this.start = start;
        this.name = name;
        this.parameterHit = parameterHit;
        children = new ArrayList<>();
        this.parent = parent;
        
        if(parent != null) {
            parent.addChild(this);
        }
    }
    
    private static String snipInLine(String lineContent, int startColumn, int endColumn) {
        
        final char[] chars = lineContent.toCharArray();
        for(int i = startColumn; i < endColumn; i++) {
            chars[i] = ' ';
        }
        return new String(chars);
    }
    
    public CodePosition getStart() {
        
        return start;
    }
    
    public CodePosition getEnd() {
        
        return end;
    }
    
    public void setEnd(CodePosition end) {
        
        this.end = end;
    }
    
    public SnippingMatch getParent() {
        
        return parent;
    }
    
    private void addChild(SnippingMatch child) {
        
        children.add(child);
    }
    
    public String getName() {
        
        return name;
    }
    
    public void snip(FileAccessSingle file) {
        
        if(parameterHit.shouldSnip) {
            snipComplete(file);
        } else {
            snipPreprocessorCallsOnly(file);
            snipChildren(file);
        }
    }
    
    private void snipPreprocessorCallsOnly(FileAccessSingle file) {
        
        final List<String> fileContents = file.getFileContents();
        {
            final int position = start.fromLine - 1;
            final int snipStart = start.fromLineOffset;
            final int snipEnd = start.toLineOffset;
            
            final String toSnip = fileContents.get(position);
            final String snipped = snipInLine(toSnip, snipStart, snipEnd);
            fileContents.set(position, snipped);
        }
        {
            final int position = end.fromLine - 1;
            final int snipStart = end.fromLineOffset;
            final int snipEnd = end.toLineOffset;
            
            final String toSnip = fileContents.get(position);
            final String snipped = snipInLine(toSnip, snipStart, snipEnd);
            fileContents.set(position, snipped);
        }
        
    }
    
    private void snipComplete(FileAccessSingle file) {
        
        for(int i = start.fromLine; i <= end.toLine; i++) {
            snipLine(i, file);
        }
    }
    
    private void snipLine(int lineNumber, FileAccessSingle file) {
        
        final List<String> fileContents = file.getFileContents();
        final int position = lineNumber - 1;
        final String toSnip = fileContents.get(position);
        
        final int snipStart = lineNumber != start.fromLine ? 0 : start.fromLineOffset;
        final int snipEnd = lineNumber != end.toLine ? toSnip.length() : end.toLineOffset;
        final String snipped = snipInLine(toSnip, snipStart, snipEnd);
        fileContents.set(position, snipped);
    }
    
    private void snipChildren(FileAccessSingle file) {
        
        for(SnippingMatch child : children) {
            child.snip(file);
        }
    }
    
}
