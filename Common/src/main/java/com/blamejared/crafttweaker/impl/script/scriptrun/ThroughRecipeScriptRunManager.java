package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRun;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.script.ScriptRecipe;
import org.openzen.zencode.shared.SourceFile;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public final class ThroughRecipeScriptRunManager {
    
    private ThroughRecipeScriptRunManager() {}
    
    public static IScriptRun createScriptRunFromRecipes(final Collection<ScriptRecipe> recipes, final ScriptRunConfiguration configuration) {
        
        // TODO("Use custom file system to leverage IScriptRunManager#createScriptRun")
        final List<IPreprocessor> preprocessors = CraftTweakerAPI.getRegistry().getPreprocessors();
        final RunInfo info = RunInfo.create(configuration);
        final List<SourceFile> sources = recipes
                .stream()
                .map(it -> ScriptFile.of(
                        it.getFileName(),
                        Arrays.stream(it.getContent().split("\n")),
                        info,
                        preprocessors
                ))
                .sorted(ScriptRunManager.FILE_COMPARATOR.get())
                .map(ScriptFile::toSourceFile)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        return ScriptRunManager.get().createScriptRun(sources, info);
    }
    
}
