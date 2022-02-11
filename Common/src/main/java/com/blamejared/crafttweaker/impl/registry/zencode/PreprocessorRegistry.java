package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.zencode.IPreprocessor;

import java.util.ArrayList;
import java.util.List;

public final class PreprocessorRegistry {
    
    private final List<IPreprocessor> preprocessors = new ArrayList<>();
    
    public void register(final IPreprocessor preprocessor) {
        
        this.preprocessors.add(preprocessor);
    }
    
    public List<IPreprocessor> getPreprocessors() {
        
        return this.preprocessors;
    }
    
}
