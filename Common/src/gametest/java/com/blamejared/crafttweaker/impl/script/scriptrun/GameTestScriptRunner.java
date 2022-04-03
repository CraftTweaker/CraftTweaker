package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.gametest.GameTestPlugin;

import java.util.Arrays;
import java.util.Collection;

public class GameTestScriptRunner {
    
    public static ScriptFile getFile(final String content, final Collection<IPreprocessor> preprocessors) {
    
        ScriptRunConfiguration configuration = new ScriptRunConfiguration(CraftTweakerConstants.DEFAULT_LOADER_NAME, GameTestPlugin.GAMETEST_SOURCE_ID, ScriptRunConfiguration.RunKind.EXECUTE);
        RunInfo info = RunInfo.create(configuration);
        return ScriptFile.of("test.zs", Arrays.stream(content.split(System.lineSeparator())), info, preprocessors);
    }
    
}
