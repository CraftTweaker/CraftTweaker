package com.blamejared.crafttweaker.api.zencode.impl.registry;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class PreprocessorRegistry {
    
    private final List<IPreprocessor> preprocessors = new ArrayList<>();
    
    
    public void addClass(Class<?> cls) {
        
        if(!IPreprocessor.class.isAssignableFrom(cls)) {
            CraftTweakerAPI.logWarning("Preprocessor: \"%s\" does not implement IPreprocessor!", cls
                    .getCanonicalName());
            return;
        }
        
        //Cast okay because isAssignable
        final IPreprocessor preprocessor = (IPreprocessor) InstantiationUtil.getOrCreateInstance(cls);
        if(preprocessor == null) {
            CraftTweakerAPI.logWarning("Can not register Preprocessor: \"%s\"!", cls.getCanonicalName());
        } else {
            preprocessors.add(preprocessor);
        }
    }
    
    public List<IPreprocessor> getPreprocessors() {
        
        return ImmutableList.copyOf(preprocessors);
    }
    
}
