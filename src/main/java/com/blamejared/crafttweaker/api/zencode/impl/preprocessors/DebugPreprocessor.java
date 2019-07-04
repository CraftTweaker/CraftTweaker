package com.blamejared.crafttweaker.api.zencode.impl.preprocessors;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.PreProcessor;
import com.blamejared.crafttweaker.api.zencode.*;
import com.blamejared.crafttweaker.api.zencode.impl.*;

import javax.annotation.*;
import java.util.*;

@PreProcessor
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
    public boolean apply(@Nonnull FileAccessSingle file, @Nonnull List<PreprocessorMatch> preprocessorMatches) {
        CraftTweakerAPI.logInfo("Debug preprocessor hit %d times in file %s", preprocessorMatches.size(), file.getFileName());
        return true;
    }
}
