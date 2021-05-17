package com.blamejared.crafttweaker.api.zencode.impl.preprocessors;

import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.annotations.Preprocessor;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.PreprocessorMatch;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

@Preprocessor
public class ModLoadedPreprocessor implements IPreprocessor {
    
    @Override
    public String getName() {
        
        return "modloaded";
    }
    
    @Nullable
    @Override
    public String getDefaultValue() {
        
        return null;
    }
    
    @Override
    public boolean apply(@Nonnull FileAccessSingle file, ScriptLoadingOptions scriptLoadingOptions, @Nonnull List<PreprocessorMatch> preprocessorMatches) {
        
        return preprocessorMatches.stream()
                .map(PreprocessorMatch::getContent)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .map(String::toLowerCase)
                .distinct()
                .allMatch(ModList.get()::isLoaded);
    }
    
}