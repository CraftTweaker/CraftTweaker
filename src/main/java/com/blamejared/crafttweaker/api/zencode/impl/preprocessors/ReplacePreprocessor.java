package com.blamejared.crafttweaker.api.zencode.impl.preprocessors;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.PreProcessor;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.PreprocessorMatch;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * {@code #replace toReplace replaceWith}
 */
@PreProcessor
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
