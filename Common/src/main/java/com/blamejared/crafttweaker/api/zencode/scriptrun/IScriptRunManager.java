package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.action.base.IAction;
import org.openzen.zencode.shared.SourceFile;

import java.nio.file.Path;
import java.util.List;

public interface IScriptRunManager {
    
    IScriptRun createScriptRun(final ScriptRunConfiguration configuration);
    
    IScriptRun createScriptRun(final Path root, final List<Path> files, final ScriptRunConfiguration configuration);
    
    IScriptRun createScriptRun(final List<SourceFile> sources, final ScriptRunConfiguration configuration);
    
    IScriptRunInfo currentRunInfo();
    
    void applyAction(final IAction action);
    
}
