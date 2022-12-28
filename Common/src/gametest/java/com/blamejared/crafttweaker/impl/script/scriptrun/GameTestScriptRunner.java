package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRun;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.gametest.GameTestPlugin;
import com.blamejared.crafttweaker.gametest.framework.DelegatingGameTestAssertException;
import com.blamejared.crafttweaker.gametest.framework.ScriptBuilder;
import com.blamejared.crafttweaker.gametest.logging.appender.GameTestLoggerAppender;
import com.blamejared.crafttweaker.impl.logging.CraftTweakerLog4jEditor;
import com.google.common.base.Suppliers;
import net.minecraft.gametest.framework.GameTestHelper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class GameTestScriptRunner {
    
    private static final Supplier<List<IPreprocessor>> PREPROCESSORS = Suppliers.memoize(() -> CraftTweakerAPI.getRegistry()
            .getPreprocessors());
    
    public static GameTestLoggerAppender.QueryableLog runScripts(GameTestHelper helper, ScriptBuilder builder) {
        
        final ScriptRunConfiguration configuration = new ScriptRunConfiguration(
                CraftTweakerConstants.DEFAULT_LOADER_NAME,
                CraftTweakerConstants.RELOAD_LISTENER_SOURCE_ID,
                ScriptRunConfiguration.RunKind.GAME_TEST
        );
        
        
        IScriptRun scriptRun = CraftTweakerAPI.getScriptRunManager()
                .createScriptRun(builder.build()
                        .stream()
                        .map(GameTestScriptRunner::getFile)
                        .map(IScriptFile::toSourceFile)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList(), configuration);
        try {
            scriptRun.execute();
        } catch(Throwable e) {
            throw new DelegatingGameTestAssertException(e.getMessage(), e);
        }
        
        return CraftTweakerLog4jEditor.queryGameTestLogger();
    }
    
    private static IScriptFile getFile(final ScriptBuilder.Script script) {
        
        return getFile(script.name(), script.content(), PREPROCESSORS.get());
    }
    
    public static IScriptFile getFile(final String name, final String content, final Collection<IPreprocessor> preprocessors) {
        
        ScriptRunConfiguration configuration = new ScriptRunConfiguration(CraftTweakerConstants.DEFAULT_LOADER_NAME, GameTestPlugin.GAMETEST_SOURCE_ID, ScriptRunConfiguration.RunKind.EXECUTE);
        RunInfo info = RunInfo.create(configuration);
        return ScriptFile.of(name, Arrays.stream(content.split(System.lineSeparator())), info, preprocessors);
    }
    
}
