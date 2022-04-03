package com.blamejared.crafttweaker.impl.script.scriptrun.runner;

import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.SemanticModule;

import java.util.Collections;
import java.util.List;

final class ExecutingScriptRunner extends ScriptRunner {
    
    ExecutingScriptRunner(final IScriptRunInfo runInfo, final List<SourceFile> sources, final ScriptingEngineLogger logger) {
        
        super(runInfo, sources, logger);
    }
    
    @Override
    protected void executeRunAction(final SemanticModule module) {
        
        this.engine().registerCompiled(module);
        this.engine().run(Collections.emptyMap(), ScriptRunner.class.getClassLoader());
    }
    
}
