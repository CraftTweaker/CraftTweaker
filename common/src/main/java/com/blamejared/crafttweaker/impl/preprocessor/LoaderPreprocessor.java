package com.blamejared.crafttweaker.impl.preprocessor;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@ZenRegister
@Preprocessor
public final class LoaderPreprocessor implements IPreprocessor {
    
    private static final String SPACE = Pattern.quote(" ");
    
    @Override
    public String name() {
        
        return "loader";
    }
    
    @Override
    public String defaultValue() {
        
        return CraftTweakerConstants.DEFAULT_LOADER_NAME;
    }
    
    @Override
    public boolean apply(final IScriptFile file, final List<String> preprocessedContents, final IMutableScriptRunInfo runInfo, final List<Match> matches) {
        
        final List<String> distinct = matches.stream()
                .map(Match::content)
                .map(it -> it.split(SPACE))
                .flatMap(Arrays::stream)
                .distinct()
                .toList();
        
        if(distinct.size() > 1) {
            PREPROCESSOR_LOGGER.warn("Multiple script loaders found for file {}: {}", file.name(), distinct);
        }
        
        return distinct.contains(runInfo.loader().name());
    }
    
}
