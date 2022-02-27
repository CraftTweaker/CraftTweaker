package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public final class PreprocessorRegistry {
    
    private final List<IPreprocessor> preprocessors = new ArrayList<>();
    
    public void addClass(final Class<?> cls) {
        
        if(!IPreprocessor.class.isAssignableFrom(cls)) {
            CraftTweakerAPI.LOGGER.warn("Preprocessor '{}' does not implement IPreprocessor!", cls
                    .getCanonicalName());
            return;
        }
        
        //Cast okay because isAssignable
        final IPreprocessor preprocessor = (IPreprocessor) InstantiationUtil.getOrCreateInstance(cls);
        if(preprocessor == null) {
            CraftTweakerAPI.LOGGER.warn("Can not register Preprocessor '{}'!", cls.getCanonicalName());
        } else {
            this.preprocessors.add(preprocessor);
        }
    }
    
    public List<IPreprocessor> getPreprocessors() {
        
        return this.preprocessors;
    }
    
}
