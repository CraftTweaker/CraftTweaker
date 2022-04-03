package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRun;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunManager;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.helper.FileGathererHelper;
import com.google.common.base.Suppliers;
import org.openzen.zencode.shared.SourceFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public final class ScriptRunManager implements IScriptRunManager {
    
    private static final Supplier<ScriptRunManager> INSTANCE = Suppliers.memoize(ScriptRunManager::new);
    
    // Package-private to allow ThroughRecipeScriptRunManager to access it TODO("Remove")
    static final Supplier<Comparator<IScriptFile>> FILE_COMPARATOR = Suppliers.memoize(() -> {
        final Comparator<IScriptFile> noop = (a, b) -> 0; // Required to allow for type inference
        return CraftTweakerAPI.getRegistry()
                .getPreprocessors()
                .stream()
                .sorted(Comparator.comparingInt(IPreprocessor::priority).reversed())
                .reduce(noop, Comparator::thenComparing, Comparator::thenComparing)
                .thenComparing(IScriptFile::name);
    });
    
    private final Map<IScriptLoader, RunInfo> previousRunInfo;
    private RunInfo currentRunInfo;
    
    private ScriptRunManager() {
        
        this.previousRunInfo = new HashMap<>();
        this.currentRunInfo = null;
    }
    
    public static ScriptRunManager get() {
        
        return INSTANCE.get();
    }
    
    @Override
    public IScriptRun createScriptRun(final ScriptRunConfiguration configuration) {
        
        return this.createScriptRun(CraftTweakerAPI.getScriptsDirectory(), configuration);
    }
    
    @Override
    public IScriptRun createScriptRun(final Path root, final ScriptRunConfiguration configuration) {
        
        return this.createScriptRun(root, this.lookupScriptFiles(root), configuration);
    }
    
    @Override
    public IScriptRun createScriptRun(final Path root, final List<Path> files, final ScriptRunConfiguration configuration) {
        
        final List<IPreprocessor> preprocessors = CraftTweakerAPI.getRegistry().getPreprocessors();
        final RunInfo info = RunInfo.create(configuration);
        final List<SourceFile> sources = files
                .stream()
                .map(it -> ScriptFile.of(root, it, info, preprocessors))
                .sorted(FILE_COMPARATOR.get())
                .map(ScriptFile::toSourceFile)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        return this.createScriptRun(sources, info);
    }
    
    @Override
    public IScriptRun createScriptRun(final List<SourceFile> sources, final ScriptRunConfiguration configuration) {
        
        return this.createScriptRun(sources, RunInfo.create(configuration));
    }
    
    @Override
    public IScriptRunInfo currentRunInfo() {
        
        return Objects.requireNonNull(this.currentRunInfo, "Unable to get current run info outside a script run");
    }
    
    @Override
    public void applyAction(final IAction action) {
        
        if(this.currentRunInfo == null) {
            this.applyActionOutsideRun(action);
            return;
        }
        
        if(!(action instanceof IRuntimeAction) && !this.currentRunInfo.isFirstRun()) {
            return;
        }
        
        this.applyActionInRun(action);
    }
    
    // Package-private to allow ThroughRecipeScriptRunManager to access it TODO("Remove")
    IScriptRun createScriptRun(final List<SourceFile> sources, final RunInfo info) {
        
        return new ScriptRun(sources, info, this::updateCurrentRunInfo, this.previousRunInfo::get);
    }
    
    private List<Path> lookupScriptFiles(final Path root) {
        
        try {
            final List<Path> files = new ArrayList<>();
            final PathMatcher matcher = root.getFileSystem().getPathMatcher("glob:**.zs");
            Files.walkFileTree(root, FileGathererHelper.of(matcher, files::add));
            return files;
        } catch(final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    private void updateCurrentRunInfo(final RunInfo current) {
        
        if(current != null) {
            this.attemptRunStart(current);
        } else {
            this.attemptRunStop();
        }
    }
    
    private void attemptRunStart(final RunInfo current) {
        
        if(this.currentRunInfo != null) {
            throw new IllegalStateException("Unable to run a script run while another is in progress");
        }
        
        this.currentRunInfo = current;
    }
    
    private void attemptRunStop() {
        
        if(this.currentRunInfo == null) {
            throw new IllegalStateException("Unable to terminate a script run that never started");
        }
        
        this.previousRunInfo.put(this.currentRunInfo.loader(), this.currentRunInfo);
        this.currentRunInfo = null;
    }
    
    private void applyActionOutsideRun(@SuppressWarnings("unused") final IAction action) {
        
        // TODO("Should this be supported? Custom IAction impl if so?")
        throw new UnsupportedOperationException("Unable to apply an action outside of a script run");
    }
    
    private void applyActionInRun(final IAction action) {
        
        final RunInfo info = Objects.requireNonNull(this.currentRunInfo);
        
        try {
            
            if(!action.shouldApplyOn(info.loadSource())) {
                return;
            }
            
            if(!action.validate(CraftTweakerAPI.LOGGER)) {
                info.enqueueAction(action, false);
                return;
            }
            
            CraftTweakerAPI.LOGGER.info(this.makeDescription(action));
            action.apply();
            info.enqueueAction(action, true);
            
        } catch(final Exception e) {
            CraftTweakerAPI.LOGGER.error("Unable to run action due to an error", e);
        }
    }
    
    private String makeDescription(final IAction action) {
        
        final String description = action.describe();
        if(description == null || description.isEmpty()) {
            return "Applying unknown action '" + action + "': tell the mod author to properly implement describe";
        }
        
        return description;
    }
    
}
