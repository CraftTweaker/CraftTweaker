package com.blamejared.crafttweaker.api.zencode.impl.preprocessors;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.zencode.*;
import com.blamejared.crafttweaker.api.zencode.impl.*;

import javax.annotation.*;
import java.util.*;

/**
 * {@code #replace toReplace replaceWith}
 */
public class ReplacePreprocessor implements IPreprocessor {
    
    @Override
    public String getName() {
        return "replace";
    }
    
    @Nullable
    @Override
    public String getDefaultValue() {
        return null;
    }
    
    @Override
    public boolean apply(@Nonnull FileAccessSingle file, @Nonnull List<PreprocessorMatch> preprocessorMatches) {
        for(PreprocessorMatch preprocessorMatch : preprocessorMatches) {
            final String[] split = preprocessorMatch.getContent().split(" ", 2);
            if(split.length != 2) {
                CraftTweakerAPI.logWarning("[%s:%d] Invalid Preprocessor line: #replace %s", file.getFileName(), preprocessorMatch.getLine(), preprocessorMatch.getContent());
                continue;
            }
            file.getFileContents().replaceAll(s -> s.replaceAll(split[0], split[1]));
        }
        
        return true;
    }
}
