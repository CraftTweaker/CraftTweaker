package com.blamejared.crafttweaker.impl.script.scriptrun.runner;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.logger.CraftTweakerLogger;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import net.minecraft.gametest.framework.GameTestAssertException;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

final class GameTestScriptRunner extends ScriptRunner {
    
    GameTestScriptRunner(IScriptRunInfo runInfo, List<SourceFile> sources, ScriptingEngineLogger logger) {
        
        super(runInfo, sources, logger);
        this.engine().debug = true;
    }
    
    
    @Override
    protected void runScripts(final BracketExpressionParser parser) throws ParseException {
    
        CraftTweakerLogger.GAMETEST_APPENDER.claim();
        final SourceFile[] sources = this.sources().toArray(SourceFile[]::new);
        final SemanticModule module = this.engine()
                .createScriptedModule("scripts", sources, parser, FunctionParameter.NONE);
    
        if(!module.isValid()) {
            Stream.of(CraftTweakerAPI.LOGGER, CraftTweakerCommon.LOG).forEach(it -> it.error("Scripts are invalid!"));
            CraftTweakerLogger.GAMETEST_APPENDER.query().dump();
            throw new GameTestAssertException("Scripts are invalid!");
        }
    
        this.executeRunAction(module);
    }
    
    @Override
    protected void executeRunAction(SemanticModule module) {
        
        this.engine().registerCompiled(module);
        this.engine().run(Collections.emptyMap(), ScriptRunner.class.getClassLoader());
    }
    
}
