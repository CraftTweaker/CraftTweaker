package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.script.scriptrun.runner.IScriptRunner;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.SourceFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

enum DecoratedRunKind {
    SYNTAX(ScriptRunConfiguration.RunKind.SYNTAX_CHECK, "Compiling scripts: this is only a syntax check, no actions will be applied"),
    FORMAT(ScriptRunConfiguration.RunKind.FORMAT, "Formatting scripts"),
    EXECUTE(ScriptRunConfiguration.RunKind.EXECUTE, "Compiling and executing scripts"),
    GAME_TEST(ScriptRunConfiguration.RunKind.GAME_TEST, "Running game test scripts");
    
    private record DecoratedScriptRunner(IScriptRunner runner, ScriptingEngineLogger logger,
                                         String message) implements IScriptRunner {
        
        @Override
        public void run() throws Exception {
            
            this.logger().info(this.message());
            this.runner().run();
        }
        
    }
    
    private static final Map<ScriptRunConfiguration.RunKind, DecoratedRunKind> VALUES = Arrays.stream(values())
            .collect(Collectors.toMap(DecoratedRunKind::kind, Function.identity()));
    
    private final ScriptRunConfiguration.RunKind kind;
    private final String logMessage;
    
    DecoratedRunKind(final ScriptRunConfiguration.RunKind kind, final String logMessage) {
        
        this.kind = kind;
        this.logMessage = logMessage;
    }
    
    static DecoratedRunKind decorate(final ScriptRunConfiguration.RunKind kind) {
        
        return VALUES.get(kind);
    }
    
    ScriptRunConfiguration.RunKind kind() {
        
        return this.kind;
    }
    
    IScriptRunner runner(final RunInfo info, final List<SourceFile> sources, final ScriptingEngineLogger logger) {
        
        return new DecoratedScriptRunner(IScriptRunner.of(info, sources, logger), logger, this.logMessage);
    }
}
