package com.blamejared.crafttweaker.impl.preprocessor.onlyif;

import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;

import javax.annotation.Nullable;
import java.util.List;

@ZenRegister
@Preprocessor
public final class EndIfPreprocessor implements IPreprocessor {
    
    public static final EndIfPreprocessor INSTANCE = new EndIfPreprocessor();
    
    private EndIfPreprocessor() {}
    
    @Override
    public String name() {
        
        return "endif";
    }
    
    @Nullable
    @Override
    public String defaultValue() {
        
        return null;
    }
    
    @Override
    public boolean apply(final IScriptFile file, final List<String> preprocessedContents, final IMutableScriptRunInfo runInfo, final List<Match> matches) {
        
        return true;
    }
    
}
