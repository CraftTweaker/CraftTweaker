package com.blamejared.crafttweaker.api.zencode.impl.preprocessor.onlyif;

import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;
import org.openzen.zencode.shared.CodePosition;

import java.util.ArrayList;
import java.util.List;

final class OnlyIfMatch {
    
    private final CodePosition start;
    private final OnlyIfMatch parent;
    private final List<OnlyIfMatch> children;
    private final String name;
    private final OnlyIfParameterHit parameterHit;
    private CodePosition end;
    
    OnlyIfMatch(CodePosition start, String name, OnlyIfMatch parent, OnlyIfParameterHit parameterHit) {
        
        this.start = start;
        this.name = name;
        this.parameterHit = parameterHit;
        children = new ArrayList<>();
        this.parent = parent;
        
        if(parent != null) {
            parent.addChild(this);
        }
    }
    
    private static String removeInLine(String lineContent, int startColumn, int endColumn) {
        
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
    
    public OnlyIfMatch getParent() {
        
        return parent;
    }
    
    private void addChild(OnlyIfMatch child) {
        
        children.add(child);
    }
    
    public String getName() {
        
        return name;
    }
    
    public void remove(FileAccessSingle file) {
        
        if(!parameterHit.conditionMet) {
            removeComplete(file);
        } else {
            removePreprocessorCallsOnly(file);
            removeChildren(file);
        }
    }
    
    private void removePreprocessorCallsOnly(FileAccessSingle file) {
        
        final List<String> fileContents = file.getFileContents();
        {
            final int position = start.fromLine - 1;
            final int onlyIfStart = start.fromLineOffset;
            final int onlyIfEnd = start.toLineOffset;
            
            final String toOnlyIf = fileContents.get(position);
            final String removed = removeInLine(toOnlyIf, onlyIfStart, onlyIfEnd);
            fileContents.set(position, removed);
        }
        {
            final int position = end.fromLine - 1;
            final int onlyIfStart = end.fromLineOffset;
            final int onlyIfend = end.toLineOffset;
            
            final String toOnlyIf = fileContents.get(position);
            final String removed = removeInLine(toOnlyIf, onlyIfStart, onlyIfend);
            fileContents.set(position, removed);
        }
        
    }
    
    private void removeComplete(FileAccessSingle file) {
        
        for(int i = start.fromLine; i <= end.toLine; i++) {
            removeLine(i, file);
        }
    }
    
    private void removeLine(int lineNumber, FileAccessSingle file) {
        
        final List<String> fileContents = file.getFileContents();
        final int position = lineNumber - 1;
        final String toOnlyIf = fileContents.get(position);
        
        final int onlyIfStart = lineNumber != start.fromLine ? 0 : start.fromLineOffset;
        final int onlyIfEnd = lineNumber != end.toLine ? toOnlyIf.length() : end.toLineOffset;
        final String removed = removeInLine(toOnlyIf, onlyIfStart, onlyIfEnd);
        fileContents.set(position, removed);
    }
    
    private void removeChildren(FileAccessSingle file) {
        
        for(OnlyIfMatch child : children) {
            child.remove(file);
        }
    }
    
}
