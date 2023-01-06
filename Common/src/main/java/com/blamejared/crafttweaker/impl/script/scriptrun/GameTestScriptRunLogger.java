package com.blamejared.crafttweaker.impl.script.scriptrun;

import org.apache.logging.log4j.Logger;
import org.openzen.zencode.shared.SourceFile;

import java.util.OptionalInt;
import java.util.function.Function;

final class GameTestScriptRunLogger extends ScriptRunLogger {
    
    GameTestScriptRunLogger(final Logger logger, Function<SourceFile, OptionalInt> priorityGetter) {
        
        super(logger, priorityGetter);
    }
    
    @Override
    public void logSourceFile(SourceFile file) {
        //NO-OP
    }
    
}
