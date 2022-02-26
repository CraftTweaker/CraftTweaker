package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.logger.CraftTweakerLogger;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRun;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.preprocessor.PriorityPreprocessor;
import com.blamejared.crafttweaker.impl.script.scriptrun.runner.ScriptRunner;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.SourceFile;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("ClassCanBeRecord")
final class ScriptRun implements IScriptRun {
    
    private final List<SourceFile> sources;
    private final RunInfo info;
    private final Consumer<RunInfo> runInfoSetter;
    private final Function<IScriptLoader, RunInfo> previousRunInfoGetter;
    
    ScriptRun(final List<SourceFile> sources, final RunInfo info, final Consumer<RunInfo> runInfoSetter, final Function<IScriptLoader, RunInfo> previousRunInfoGetter) {
        
        this.sources = sources;
        this.info = info;
        this.runInfoSetter = runInfoSetter;
        this.previousRunInfoGetter = previousRunInfoGetter;
    }
    
    @Override
    public void execute() throws Throwable {
        
        final IScriptLoader loader = this.info.loader();
        final String loaderName = loader.name();
        final RunInfo previousInfo = this.previousRunInfoGetter.apply(loader);
        this.info.isFirstRun(previousInfo == null);
        
        try {
            CraftTweakerAPI.LOGGER.info("Started loading scripts for loader '{}'", loaderName);
            this.undoPreviousRun(previousInfo);
            this.executeRun();
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
    
    private void undoPreviousRun(final RunInfo info) {
        
        CraftTweakerLogger.clearPreviousMessages(); // TODO("Move to internal method?")
        
        if(info == null) {
            return;
        }
        if(this.info.configuration().runKind() != ScriptRunConfiguration.RunKind.EXECUTE) {
            return;
        }
        
        CraftTweakerAPI.LOGGER.info("Undoing previous actions");
        this.info.appliedActions()
                .stream()
                .filter(IUndoableAction.class::isInstance)
                .filter(it -> it.shouldApplyOn(this.info.loadSource()))
                .map(IUndoableAction.class::cast)
                .peek(it -> CraftTweakerAPI.LOGGER.info(it.describeUndo()))
                .forEach(IUndoableAction::undo);
    }
    
    private void executeRun() throws Exception {
        
        try {
            this.runInfoSetter.accept(this.info);
            
            final ScriptingEngineLogger logger = new ScriptRunLogger(this::findPriorityIfPresent);
            final ScriptRunner runner = switch(this.info.configuration().runKind()) {
                case SYNTAX_CHECK -> this.syntax(logger);
                case FORMAT -> this.format(logger);
                case EXECUTE -> this.run(logger);
            };
            
            runner.run();
        } finally {
            this.runInfoSetter.accept(null);
        }
    }
    
    private ScriptRunner syntax(final ScriptingEngineLogger logger) {
        
        CraftTweakerAPI.LOGGER.info("Compiling scripts: this is only a syntax check, no actions will be applied");
        return ScriptRunner.of(this.info, this.sources, logger);
    }
    
    private ScriptRunner format(final ScriptingEngineLogger logger) {
        
        CraftTweakerAPI.LOGGER.info("Formatting scripts");
        return ScriptRunner.of(this.info, this.sources, logger);
    }
    
    private ScriptRunner run(final ScriptingEngineLogger logger) {
        
        CraftTweakerAPI.LOGGER.info("Compiling and executing scripts");
        return ScriptRunner.of(this.info, this.sources, logger);
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
