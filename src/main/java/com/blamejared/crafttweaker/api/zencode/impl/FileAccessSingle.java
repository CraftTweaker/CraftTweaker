package com.blamejared.crafttweaker.api.zencode.impl;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.zencode.*;
import org.openzen.zencode.shared.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class FileAccessSingle {
    
    private final String fileName;
    private final Map<IPreprocessor, List<PreprocessorMatch>> matches = new HashMap<>();
    private final List<String> fileContents;
    private final Map<String, IPreprocessor> registeredPreprocessors;
    private boolean shouldBeLoaded;
    
    public FileAccessSingle(File file, Map<String, IPreprocessor> registeredPreprocessors) {
        this.registeredPreprocessors = registeredPreprocessors;
        this.fileName = file.getName();
        this.fileContents = new ArrayList<>();
        readFile(file);
        fillInMissingPreprocessors();
        applyPreprocessors();
    }
    
    private void fillInMissingPreprocessors() {
        registeredPreprocessors.forEach((key, prep) -> {
            final String defaultValue = prep.getDefaultValue();
            
            if(defaultValue != null && !matches.containsKey(prep)) {
                matches.put(prep, Collections.singletonList(new PreprocessorMatch(prep, -1, defaultValue)));
            }
        });
    }
    
    private void applyPreprocessors() {
        final ArrayList<Map.Entry<IPreprocessor, List<PreprocessorMatch>>> entries = new ArrayList<>(matches.entrySet());
        entries.sort(Comparator.comparingInt((Map.Entry<IPreprocessor, ?> e) -> e.getKey().getPriority()).reversed());
        
        for(Map.Entry<IPreprocessor, List<PreprocessorMatch>> entry : entries) {
            shouldBeLoaded = entry.getKey().apply(this, entry.getValue());
            if(!shouldBeLoaded)
                return;
        }
    }
    
    private void readFile(File file) {
        try(final BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            int i = 0;
            while(bufferedReader.ready()) {
                final String line = bufferedReader.readLine();
                this.checkPreprocessor(line, ++i);
                this.fileContents.add(line);
            }
        } catch(IOException e) {
            CraftTweakerAPI.logThrowing("Could not load file %s", e, file);
        }
    }
    
    private void checkPreprocessor(String line, int lineNumber) {
        final Matcher matcher = IPreprocessor.preprocessorPattern.matcher(line);
        if(!matcher.find())
            return;
        
        final String g = matcher.group();
        final String preprocessorName = g.substring(1);
        if(!registeredPreprocessors.containsKey(preprocessorName))
            return;
        
        final IPreprocessor preprocessor = registeredPreprocessors.get(preprocessorName);
        final List<PreprocessorMatch> matches = this.matches.computeIfAbsent(preprocessor, p -> new ArrayList<>(1));
        matches.add(new PreprocessorMatch(preprocessor, lineNumber, line.substring(g.length()).trim()));
    }
    
    public Map<IPreprocessor, List<PreprocessorMatch>> getMatches() {
        return matches;
    }
    
    public List<String> getFileContents() {
        return fileContents;
    }
    
    public SourceFile getSourceFile() {
        return new SourceFilePreprocessed(fileName, fileContents);
    }
    
    public boolean shouldBeLoaded() {
        return shouldBeLoaded;
    }
    
    public String getFileName() {
        return fileName;
    }
}
