package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.impl.preprocessor.LoadFirstPreprocessor;
import com.blamejared.crafttweaker.impl.preprocessor.LoadLastPreprocessor;
import com.blamejared.crafttweaker.impl.preprocessor.PriorityPreprocessor;
import org.openzen.zencode.shared.SourceFile;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SuppressWarnings("ClassCanBeRecord")
final class PreprocessedSourceFile implements SourceFile {
    
    private final String fileName;
    private final List<String> contents;
    private final Map<IPreprocessor, List<IPreprocessor.Match>> matches;
    
    PreprocessedSourceFile(final String fileName, final List<String> contents, final Map<IPreprocessor, List<IPreprocessor.Match>> matches) {
        
        this.fileName = fileName;
        this.contents = new ArrayList<>(contents);
        this.matches = Map.copyOf(matches);
    }
    
    @Override
    public String getFilename() {
        
        return this.fileName;
    }
    
    @Override
    public Reader open() {
        
        final String contents = this.contents.stream()
                .collect(Collectors.joining(System.lineSeparator(), "", System.lineSeparator()));
        return new StringReader(contents);
    }
    
    @Override
    public void update(final String content) {
        
        this.contents.clear();
        this.contents.addAll(Arrays.asList(content.split(Pattern.quote(System.lineSeparator()))));
    }
    
    Map<IPreprocessor, List<IPreprocessor.Match>> matches() {
        
        return this.matches;
    }
    
    @Override
    public int getOrder() {
        
        if(matches().containsKey(LoadFirstPreprocessor.INSTANCE)) {
            return Integer.MAX_VALUE;
        } else if(matches().containsKey(LoadLastPreprocessor.INSTANCE)) {
            return Integer.MIN_VALUE;
        }
        
        return Integer.parseInt(matches()
                .get(PriorityPreprocessor.INSTANCE)
                .get(0)
                .content());
    }
    
}
