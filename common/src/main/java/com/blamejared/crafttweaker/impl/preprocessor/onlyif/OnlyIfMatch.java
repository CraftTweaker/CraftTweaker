package com.blamejared.crafttweaker.impl.preprocessor.onlyif;

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
    
    OnlyIfMatch(final CodePosition start, final String name, final OnlyIfMatch parent, final OnlyIfParameterHit parameterHit) {
        
        this.start = start;
        this.name = name;
        this.parameterHit = parameterHit;
        this.children = new ArrayList<>();
        this.parent = parent;
        
        if(parent != null) {
            parent.addChild(this);
        }
    }
    
    private static String removeInLine(final String lineContent, final int startColumn, final int endColumn) {
        
        final char[] chars = lineContent.toCharArray();
        for(int i = startColumn; i < endColumn; i++) {
            chars[i] = ' ';
        }
        return new String(chars);
    }
    
    public CodePosition start() {
        
        return this.start;
    }
    
    public CodePosition end() {
        
        return this.end;
    }
    
    public void end(final CodePosition end) {
        
        this.end = end;
    }
    
    public OnlyIfMatch parent() {
        
        return this.parent;
    }
    
    private void addChild(final OnlyIfMatch child) {
        
        this.children.add(child);
    }
    
    public String name() {
        
        return this.name;
    }
    
    public void remove(final List<String> fileContents) {
        
        if(!this.parameterHit.conditionMet()) {
            
            this.removeComplete(fileContents);
        } else {
            
            this.removePreprocessorCallsOnly(fileContents);
            this.removeChildren(fileContents);
        }
    }
    
    private void removePreprocessorCallsOnly(final List<String> fileContents) {
        
        {
            final int position = this.start.fromLine - 1;
            final int onlyIfStart = this.start.fromLineOffset;
            final int onlyIfEnd = this.start.toLineOffset;
            
            final String toOnlyIf = fileContents.get(position);
            final String removed = removeInLine(toOnlyIf, onlyIfStart, onlyIfEnd);
            fileContents.set(position, removed);
        }
        {
            final int position = this.end.fromLine - 1;
            final int onlyIfStart = this.end.fromLineOffset;
            final int onlyIfend = this.end.toLineOffset;
            
            final String toOnlyIf = fileContents.get(position);
            final String removed = removeInLine(toOnlyIf, onlyIfStart, onlyIfend);
            fileContents.set(position, removed);
        }
        
    }
    
    private void removeComplete(final List<String> fileContents) {
        
        for(int i = this.start.fromLine; i <= this.end.toLine; i++) {
            removeLine(i, fileContents);
        }
    }
    
    private void removeLine(final int lineNumber, final List<String> fileContents) {
        
        final int position = lineNumber - 1;
        final String toOnlyIf = fileContents.get(position);
        
        final int onlyIfStart = lineNumber != this.start.fromLine ? 0 : this.start.fromLineOffset;
        final int onlyIfEnd = lineNumber != this.end.toLine ? toOnlyIf.length() : this.end.toLineOffset;
        final String removed = removeInLine(toOnlyIf, onlyIfStart, onlyIfEnd);
        fileContents.set(position, removed);
    }
    
    private void removeChildren(final List<String> fileContents) {
        
        this.children.forEach(it -> it.remove(fileContents));
    }
    
}
