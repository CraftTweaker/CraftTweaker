package com.blamejared.crafttweaker.api.zencode.impl;

import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.PreprocessorMatch;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessor.LoadFirstPreprocessor;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessor.LoadLastPreprocessor;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessor.PriorityPreprocessor;
import org.openzen.zencode.shared.SourceFile;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SourceFilePreprocessed implements SourceFile {
    
    private final String fileName;
    private List<String> fileContent;
    private final Map<IPreprocessor, List<PreprocessorMatch>> matches;
    
    public SourceFilePreprocessed(String fileName, List<String> fileContent, Map<IPreprocessor, List<PreprocessorMatch>> matches) {
        
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.matches = matches;
    }
    
    @Override
    public String getFilename() {
        
        return fileName;
    }
    
    @Override
    public Reader open() {
        
        return new StringReader(String.join(System.lineSeparator(), fileContent) + System.lineSeparator());
    }
    
    @Override
    public void update(String content) {
        
        fileContent = Arrays.asList(content.split(System.lineSeparator()));
    }
    
    public Map<IPreprocessor, List<PreprocessorMatch>> getMatches() {
        
        return matches;
    }
    
    @Override
    public int getOrder() {
        
        if(getMatches().containsKey(LoadFirstPreprocessor.INSTANCE)) {
            return Integer.MAX_VALUE;
        } else if(getMatches().containsKey(LoadLastPreprocessor.INSTANCE)) {
            return Integer.MIN_VALUE;
        }
        
        return Integer.parseInt(getMatches()
                .get(PriorityPreprocessor.INSTANCE)
                .get(0)
                .getContent());
    }
    
}
