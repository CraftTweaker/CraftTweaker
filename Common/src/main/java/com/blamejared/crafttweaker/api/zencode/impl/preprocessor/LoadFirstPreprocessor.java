package com.blamejared.crafttweaker.api.zencode.impl.preprocessor;

import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.PreprocessorMatch;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Preprocessor
public class LoadFirstPreprocessor implements IPreprocessor {
    
    @Override
    public String getName() {
        
        return "loadfirst";
    }
    
    @Nullable
    @Override
    public String getDefaultValue() {
        
        return null;
    }
    
    @Override
    public boolean apply(@Nonnull FileAccessSingle file, ScriptLoadingOptions scriptLoadingOptions, @Nonnull List<PreprocessorMatch> preprocessorMatches) {
        
        return true;
    }
    
    @Override
    public int getPriority() {
        
        return 13;
    }
    
    @Override
    public int compare(FileAccessSingle o1, FileAccessSingle o2) {
        
        return Boolean.compare(o2.hasMatchFor(this), o1.hasMatchFor(this));
    }
    
}
