package com.blamejared.crafttweaker.impl.script.scriptrun.runner;

import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import com.blamejared.crafttweaker.platform.Services;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.SemanticModule;

import java.nio.file.Path;
import java.util.List;

final class SyntaxCheckScriptRunner extends ScriptRunner {
    
    SyntaxCheckScriptRunner(final IScriptRunInfo runInfo, final List<SourceFile> sources, final ScriptingEngineLogger logger) {
        
        super(runInfo, sources, logger);
    }
    
    @Override
    protected void executeRunAction(final SemanticModule module) {
        
        if(!this.runInfo().dumpClasses()) {
            return;
        }
        
        final Path classes = Services.PLATFORM.getPathFromGameDirectory("classes");
        this.engine().createRunUnit().dump(classes.toFile());
    }
    
}
