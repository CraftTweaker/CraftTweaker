package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRun;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.logging.CraftTweakerLog4jEditor;
import com.blamejared.crafttweaker.impl.preprocessor.PriorityPreprocessor;
import com.blamejared.crafttweaker.impl.script.scriptrun.runner.IScriptRunner;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.SourceFile;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Predicate;

final class ScriptRun implements IScriptRun {
    
    private final List<SourceFile> sources;
    private final RunInfo info;
    private final Consumer<RunInfo> runInfoSetter;
    private final Predicate<IScriptLoader> isFirstRunPredicate;
    private final Consumer<IScriptLoader> actionUndoExecutor;
    
    ScriptRun(
            final List<SourceFile> sources,
            final RunInfo info,
            final Consumer<RunInfo> runInfoSetter,
            final Predicate<IScriptLoader> isFirstRunPredicate,
            final Consumer<IScriptLoader> actionUndoExecutor
    ) {
        
        this.sources = sources;
        this.info = info;
        this.runInfoSetter = runInfoSetter;
        this.isFirstRunPredicate = isFirstRunPredicate;
        this.actionUndoExecutor = actionUndoExecutor;
    }
    
    @Override
    public void execute() throws Throwable {
        
        final IScriptLoader loader = this.info.loader();
        final String loaderName = loader.name();
        this.info.isFirstRun(this.isFirstRunPredicate.test(loader));
        
        try {
            CraftTweakerAPI.LOGGER.info("Started loading scripts for loader '{}'", loaderName);
            this.undoPreviousRun(loader, this.info.configuration().runKind());
            this.executeRun();
            CraftTweakerCommon.getPluginManager().broadcastRunExecution(this.info.configuration());
            CraftTweakerAPI.LOGGER.info("Execution for loader '{}' completed successfully", loaderName);
        } catch(final Throwable t) {
            CraftTweakerAPI.LOGGER.error("Execution for loader '" + loaderName + "' completed with an error", t);
            throw t;
        }
    }
    
    @Override
    public IScriptRunInfo specificRunInfo() {
        
        return this.info;
    }
    
    private void undoPreviousRun(final IScriptLoader loader, final ScriptRunConfiguration.RunKind runKind) {
        
        CraftTweakerLog4jEditor.clearPreviousMessages(); // TODO("Move to internal method?")
        
        if(runKind != ScriptRunConfiguration.RunKind.EXECUTE) {
            return;
        }
        
        this.actionUndoExecutor.accept(loader);
    }
    
    private void executeRun() throws Exception {
        
        try {
            this.runInfoSetter.accept(this.info);
            
            final DecoratedRunKind runKind = DecoratedRunKind.decorate(this.info.configuration().runKind());
            final ScriptingEngineLogger logger = runKind.kind() != ScriptRunConfiguration.RunKind.GAME_TEST ? new ScriptRunLogger(this::findPriorityIfPresent) : new GameTestScriptRunLogger(this::findPriorityIfPresent);
            final IScriptRunner runner = runKind.runner(this.info, this.sources, logger);
            runner.run();
        } finally {
            this.runInfoSetter.accept(null);
        }
    }
    
    private OptionalInt findPriorityIfPresent(final SourceFile file) {
        
        if(!(file instanceof PreprocessedSourceFile preprocessedFile)) {
            return OptionalInt.empty();
        }
        
        // TODO("A better way, maybe?")
        final String priority = preprocessedFile.matches()
                .get(PriorityPreprocessor.INSTANCE)
                .get(0)
                .content();
        try {
            return OptionalInt.of(Integer.parseInt(priority));
        } catch(final NumberFormatException e) {
            return OptionalInt.empty();
        }
    }
    
}
