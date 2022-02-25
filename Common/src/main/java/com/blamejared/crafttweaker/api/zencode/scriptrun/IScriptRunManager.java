package com.blamejared.crafttweaker.api.zencode.scriptrun;

import org.openzen.zencode.shared.SourceFile;

import java.util.List;

public interface IScriptRunManager {
    
    IScriptRun createScriptRun(final ScriptRunConfiguration configuration);
    
    IScriptRun createScriptRun(final List<SourceFile> sources, final ScriptRunConfiguration configuration);
    
    IScriptRunInfo currentRunInfo();
    
}
