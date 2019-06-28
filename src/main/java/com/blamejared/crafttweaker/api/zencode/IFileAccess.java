package com.blamejared.crafttweaker.api.zencode;

import org.openzen.zencode.shared.*;

import java.util.*;

public interface IFileAccess {
    
    Map<IPreprocessor, List<PreprocessorMatch>> getMatches();
    
    SourceFile getSourceFile();
}
