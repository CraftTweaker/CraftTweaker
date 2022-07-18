package com.blamejared.crafttweaker.impl.script.scriptrun;

import org.openzen.zencode.shared.SourceFile;

import java.util.OptionalInt;
import java.util.function.Function;

final class GameTestScriptRunLogger extends ScriptRunLogger {
    
    GameTestScriptRunLogger(Function<SourceFile, OptionalInt> priorityGetter) {
        
        super(priorityGetter);
    }
    
    @Override
    public void logSourceFile(SourceFile file) {
        //NO-OP
    }
    
}
