package com.blamejared.crafttweaker.api.zencode.impl.preprocessors;

import com.blamejared.crafttweaker.api.annotations.Preprocessor;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.PreprocessorMatch;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Preprocessor
public class LoadLastPreprocessor implements IPreprocessor {
    
    @Override
    public String getName() {
        return "loadlast";
    }
    
    @Nullable
    @Override
    public String getDefaultValue() {
        return null;
    }
    
    @Override
    public boolean apply(@Nonnull FileAccessSingle file, @Nonnull List<PreprocessorMatch> preprocessorMatches) {
        return true;
    }
    
    @Override
    public int getPriority() {
        return 11;
    }
    
    @Override
    public int compare(FileAccessSingle o1, FileAccessSingle o2) {
        return Boolean.compare(o1.hasMatchFor(this), o2.hasMatchFor(this));
    }
}
