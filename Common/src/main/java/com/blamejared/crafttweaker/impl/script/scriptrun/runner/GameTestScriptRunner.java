package com.blamejared.crafttweaker.impl.script.scriptrun.runner;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import com.blamejared.crafttweaker.impl.logging.CraftTweakerLog4jEditor;
import net.minecraft.gametest.framework.GameTestAssertException;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.Collections;
import java.util.List;

final class GameTestScriptRunner extends ScriptRunner {
    
    GameTestScriptRunner(IScriptRunInfo runInfo, List<SourceFile> sources, ScriptingEngineLogger logger) {
        
        super(runInfo, sources, logger);
        this.engine().debug = true;
    }
    
    
    @Override
    protected void runScripts(final BracketExpressionParser parser) throws ParseException {
        
        CraftTweakerLog4jEditor.claimGameTestLogger();
        final SourceFile[] sources = this.sources().toArray(SourceFile[]::new);
        final SemanticModule module = this.engine()
                .createScriptedModule("scripts", sources, parser, FunctionParameter.NONE);
        
        if(!module.isValid()) {
            CraftTweakerCommon.logger().error("Scripts are invalid!");
            CraftTweakerLog4jEditor.queryGameTestLogger().dump();
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
