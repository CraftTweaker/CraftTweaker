package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRun;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunManager;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.google.common.base.Suppliers;
import org.openzen.zencode.shared.SourceFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public final class ScriptRunManager implements IScriptRunManager {
    
    private static final Supplier<ScriptRunManager> INSTANCE = Suppliers.memoize(ScriptRunManager::new);
    private static final Supplier<Comparator<IScriptFile>> FILE_COMPARATOR = Suppliers.memoize(() -> {
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
        
        final List<IPreprocessor> preprocessors = CraftTweakerAPI.getRegistry().getPreprocessors();
        final RunInfo info = RunInfo.create(configuration);
        final List<SourceFile> sources = lookupScriptFiles()
                .stream()
                .map(it -> ScriptFile.of(CraftTweakerAPI.getScriptsDirectory(), it, info, preprocessors))
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
    
    private List<Path> lookupScriptFiles() {
        
        try {
            final ScriptRunGathererVisitor visitor = new ScriptRunGathererVisitor();
            Files.walkFileTree(CraftTweakerAPI.getScriptsDirectory(), visitor);
            return visitor.files();
        } catch(final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    private IScriptRun createScriptRun(final List<SourceFile> sources, final RunInfo info) {
        
        return new ScriptRun(sources, info, this::updateCurrentRunInfo, this.previousRunInfo::get);
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
            throw new IllegalStateException("Unable to terminate a never-started script run");
        }
        
        this.previousRunInfo.put(this.currentRunInfo.loader(), this.currentRunInfo);
        this.currentRunInfo = null;
    }
    
}
