package com.blamejared.crafttweaker.api.zencode.scriptrun;

import org.openzen.zencode.shared.SourceFile;

import java.util.List;
import java.util.Optional;

public interface IScriptFile {
    
    String name();
    
    List<String> fileContents();
    
    List<String> preprocessedContents();
    
    Optional<SourceFile> toSourceFile();
    
}
