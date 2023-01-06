package com.blamejared.crafttweaker.impl.preprocessor;

import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;

import java.util.List;

@ZenRegister
@Preprocessor
public final class PriorityPreprocessor implements IPreprocessor {
    
    public static final PriorityPreprocessor INSTANCE = new PriorityPreprocessor();
    
    private static final int DEFAULT_PRIORITY = 0;
    
    private PriorityPreprocessor() {}
    
    @Override
    public String name() {
        
        return "priority";
    }
    
    @Override
    public String defaultValue() {
        
        return Integer.toString(DEFAULT_PRIORITY);
    }
    
    @Override
    public boolean apply(final IScriptFile file, final List<String> preprocessedContents, final IMutableScriptRunInfo runInfo, final List<Match> matches) {
        
        if(matches.size() > 1) {
            PREPROCESSOR_LOGGER.warn("Conflicting priorities in file {}: only the first will be used", file.name());
        }
        
        final String priority = matches.get(0).content().trim();
        
        try {
            Integer.parseInt(priority);
        } catch(final NumberFormatException e) {
            PREPROCESSOR_LOGGER.warn("Invalid priority value '{}' for file {}", priority, file.name());
        }
        
        return true;
    }
    
    @Override
    public int compare(final IScriptFile a, final IScriptFile b) {
        
        return Integer.compare(
                this.getIntSafely(a.matchesFor(this).get(0)),
                this.getIntSafely(b.matchesFor(this).get(0))
        );
    }
    
    @Override
    public int priority() {
        
        return 100;
    }
    
    private int getIntSafely(final Match match) {
        
        try {
            return Integer.parseInt(match.content().trim());
        } catch(final NumberFormatException e) {
            return DEFAULT_PRIORITY;
        }
    }
    
}
