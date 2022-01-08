package com.blamejared.crafttweaker.api.zencode.impl.preprocessor;

import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.PreprocessorMatch;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;
import com.blamejared.crafttweaker.platform.Services;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

@Preprocessor
public class ModNotLoadedPreprocessor implements IPreprocessor {
    
    @Override
    public String getName() {
        
        return "modnotloaded";
    }
    
    @Nullable
    @Override
    public String getDefaultValue() {
        
        return null;
    }
    
    @Override
    public boolean apply(@Nonnull FileAccessSingle file, ScriptLoadingOptions scriptLoadingOptions, @Nonnull List<PreprocessorMatch> preprocessorMatches) {
        
        return preprocessorMatches.stream()
                .map(PreprocessorMatch::getContent)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .map(String::toLowerCase)
                .distinct()
                .noneMatch(Services.PLATFORM::isModLoaded);
    }
    
}