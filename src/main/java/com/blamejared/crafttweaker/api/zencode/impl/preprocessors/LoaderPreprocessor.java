package com.blamejared.crafttweaker.api.zencode.impl.preprocessors;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.annotations.Preprocessor;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.PreprocessorMatch;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Preprocessor
public class LoaderPreprocessor implements IPreprocessor {
    @Override
    public String getName() {
        return "loader";
    }

    @Nullable
    @Override
    public String getDefaultValue() {
        return "crafttweaker";
    }

    @Override
    public boolean apply(@Nonnull FileAccessSingle file, ScriptLoadingOptions scriptLoadingOptions, @Nonnull List<PreprocessorMatch> preprocessorMatches) {
        final List<String> distinct = preprocessorMatches.stream()
                .map(PreprocessorMatch::getContent)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .map(String::toLowerCase)
                .distinct()
                .collect(Collectors.toList());

        if(distinct.size() > 1) {
            CraftTweakerAPI.logWarning("Multiple Loaders found for file %s: %s", file.getFileName(), distinct);
        }

        return distinct.contains(scriptLoadingOptions.getLoaderName().toLowerCase());

    }
}
