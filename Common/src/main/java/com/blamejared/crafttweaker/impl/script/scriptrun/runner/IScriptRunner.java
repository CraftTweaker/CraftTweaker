package com.blamejared.crafttweaker.impl.script.scriptrun.runner;

import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.SourceFile;

import java.util.List;

@FunctionalInterface
public interface IScriptRunner {
    
    static IScriptRunner of(final IScriptRunInfo info, final List<SourceFile> sources, final ScriptingEngineLogger logger) {
        
        return ScriptRunner.of(info, sources, logger);
    }
    
    void run() throws Exception;
    
}
