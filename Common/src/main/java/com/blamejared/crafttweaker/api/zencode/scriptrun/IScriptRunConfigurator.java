package com.blamejared.crafttweaker.api.zencode.scriptrun;

import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.shared.CompileException;

@FunctionalInterface
public interface IScriptRunConfigurator {
    
    void configure(
            final IBepRegistrationHandler registrationHandler,
            final IBepToModuleAdder moduleAdder,
            final ScriptingEngine engine,
            final IScriptLoadingOptionsView options
    ) throws CompileException;
    
}
