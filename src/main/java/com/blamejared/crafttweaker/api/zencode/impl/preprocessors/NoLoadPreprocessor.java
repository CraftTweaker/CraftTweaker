package com.blamejared.crafttweaker.api.zencode.impl.preprocessors;

import com.blamejared.crafttweaker.api.zencode.*;
import com.blamejared.crafttweaker.api.zencode.impl.*;

import javax.annotation.*;
import java.util.*;

public class NoLoadPreprocessor implements IPreprocessor {
    
    @Override
    public String getName() {
        return "noload";
    }
    
    @Nullable
    @Override
    public String getDefaultValue() {
        return null;
    }
    
    @Override
    public boolean apply(@Nonnull FileAccessSingle file, @Nonnull List<PreprocessorMatch> preprocessorMatches) {
        return false;
    }
}
