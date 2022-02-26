package com.blamejared.crafttweaker.api.zencode.scriptrun;

public interface IScriptRun {
    
    IScriptRunInfo specificRunInfo();
    
    void execute() throws Throwable;
    
}
