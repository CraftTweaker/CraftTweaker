package com.blamejared.crafttweaker.impl.preprocessor;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.blamejared.crafttweaker.platform.Services;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@ZenRegister
@Preprocessor
public final class ModLoadedPreprocessor implements IPreprocessor {
    
    private static final String SPACE = Pattern.quote(" ");
    
    @Override
    public String name() {
        
        return "modloaded";
    }
    
    @Nullable
    @Override
    public String defaultValue() {
        
        return null;
    }
    
    @Override
    public boolean apply(final IScriptFile file, final List<String> preprocessedContents, final IMutableScriptRunInfo runInfo, final List<Match> matches) {
        
        return matches.stream()
                .map(Match::content)
                .map(it -> it.split(SPACE))
                .flatMap(Arrays::stream)
                .peek(this::verifyLowercase)
                .distinct()
                .allMatch(Services.PLATFORM::isModLoaded);
    }
    
    private void verifyLowercase(final String id) {
        
        if(id.toLowerCase(Locale.ENGLISH).equals(id)) {
            return;
        }
        
        CraftTweakerAPI.LOGGER.error("Mod IDs cannot have uppercase letters: the ID " + id + " will never match");
    }
    
}