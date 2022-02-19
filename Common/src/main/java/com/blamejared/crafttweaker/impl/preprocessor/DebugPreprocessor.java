package com.blamejared.crafttweaker.impl.preprocessor;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.PreprocessorMatch;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Preprocessor
public class DebugPreprocessor implements IPreprocessor {
    
    @Override
    public String getName() {
        
        return "debug";
    }
    
    @Nullable
    @Override
    public String getDefaultValue() {
        
        return null;
    }
    
    @Override
    public boolean apply(@Nonnull FileAccessSingle file, ScriptLoadingOptions scriptLoadingOptions, @Nonnull List<PreprocessorMatch> preprocessorMatches) {
        
        CraftTweakerAPI.DEBUG_MODE = true;
        return true;
    }
    
}
