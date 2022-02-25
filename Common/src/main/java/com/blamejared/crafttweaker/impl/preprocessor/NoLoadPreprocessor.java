package com.blamejared.crafttweaker.impl.preprocessor;

import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;

import javax.annotation.Nullable;
import java.util.List;

@Preprocessor
public final class NoLoadPreprocessor implements IPreprocessor {
    
    @Override
    public String name() {
        
        return "noload";
    }
    
    @Nullable
    @Override
    public String defaultValue() {
        
        return null;
    }
    
    @Override
    public boolean apply(final IScriptFile file, final List<String> preprocessedContents, final IMutableScriptRunInfo runInfo, final List<Match> matches) {
        
        return false;
    }
    
}
