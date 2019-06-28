package com.blamejared.crafttweaker.api.zencode.impl.preprocessors;

import com.blamejared.crafttweaker.*;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.zencode.*;
import com.blamejared.crafttweaker.api.zencode.impl.*;

import javax.annotation.*;
import java.util.*;

public class PrintPreprocessor implements IPreprocessor {
    
    @Override
    public String getName() {
        return "print";
    }
    
    @Nullable
    @Override
    public String getDefaultValue() {
        return "Hello";
    }
    
    @Override
    public boolean apply(@Nonnull FileAccessSingle file, @Nonnull List<PreprocessorMatch> preprocessorMatches) {
        for(PreprocessorMatch match : preprocessorMatches) {
            CraftTweakerAPI.logInfo("[%s:%d] %s", file.getFileName(), match.getLine(), match.getContent());
        }
        
        return true;
    }
}
