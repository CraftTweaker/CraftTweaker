package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import org.openzen.zencode.shared.SourceFile;

import java.util.List;
import java.util.Optional;

public interface IScriptFile {
    
    String name();
    
    List<String> fileContents();
    
    List<String> preprocessedContents();
    
    Optional<SourceFile> toSourceFile();
    
    List<IPreprocessor.Match> matchesFor(final IPreprocessor preprocessor);
    
    default boolean hasMatchesFor(final IPreprocessor preprocessor) {
        
        return !this.matchesFor(preprocessor).isEmpty();
    }
    
}
