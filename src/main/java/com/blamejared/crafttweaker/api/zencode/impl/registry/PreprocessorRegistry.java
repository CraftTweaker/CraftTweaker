package com.blamejared.crafttweaker.api.zencode.impl.registry;

import com.blamejared.crafttweaker.*;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.util.*;
import com.blamejared.crafttweaker.api.zencode.*;
import com.google.common.collect.*;
import org.objectweb.asm.*;

import java.util.*;

public class PreprocessorRegistry {
    
    private final List<IPreprocessor> preprocessors = new ArrayList<>();
    
    
    public void addType(Type type) {
        try {
            addClass(Class.forName(type.getClassName(), false, CraftTweaker.class.getClassLoader()));
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void addClass(Class<?> cls) {
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
