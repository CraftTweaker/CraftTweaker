package com.blamejared.crafttweaker.impl.preprocessor;

import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;

import javax.annotation.Nullable;
import java.util.List;

@ZenRegister
@Preprocessor // TODO("Does it make sense to keep it given we have priority?")
public final class LoadLastPreprocessor implements IPreprocessor {
    
    public static final LoadLastPreprocessor INSTANCE = new LoadLastPreprocessor();
    
    private LoadLastPreprocessor() {}
    
    @Override
    public String name() {
        
        return "loadlast";
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
    
    @Override
    public int priority() {
        
        return 11;
    }
    
    @Override
    public int compare(final IScriptFile a, final IScriptFile b) {
        
        return Boolean.compare(a.hasMatchesFor(this), b.hasMatchesFor(this));
    }
    
}
