package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRun;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunManager;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptDiscoveryConfiguration;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.helper.FileGathererHelper;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Suppliers;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.shared.SourceFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
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
    
    private static final boolean DEVELOPMENT = Services.PLATFORM.isDevelopmentEnvironment();
    
    private final Logger logger;
    private final Map<IScriptLoader, RunInfoQueue> previousRunQueues;
    private final ThreadLocal<Integer> nestingLevel;
    private RunInfo currentRunInfo;
    
    private ScriptRunManager() {
        
        this.logger = CommonLoggers.zenCode();
        this.previousRunQueues = new HashMap<>();
        this.nestingLevel = ThreadLocal.withInitial(() -> 0);
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
        
        return this.createScriptRun(root, new ScriptDiscoveryConfiguration(ScriptDiscoveryConfiguration.SuspiciousNamesBehavior.WARN), configuration);
    }
    
    @Override
    public IScriptRun createScriptRun(final Path root, final ScriptDiscoveryConfiguration discoveryConfiguration, final ScriptRunConfiguration runConfiguration) {
        
        return this.createScriptRun(root, this.lookupScriptFiles(root, discoveryConfiguration), runConfiguration);
    }
    
    @Override
    public IScriptRun createScriptRun(final Path root, final List<Path> files, final ScriptRunConfiguration configuration) {
        
        final List<IPreprocessor> preprocessors = CraftTweakerAPI.getRegistry().getPreprocessors();
        final RunInfo info = RunInfo.create(configuration);
        final List<SourceFile> sources = files
                .stream()
                .map(it -> ScriptFile.of(this.logger, root, it, info, preprocessors))
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
    
    private IScriptRun createScriptRun(final List<SourceFile> sources, final RunInfo info) {
        
        this.previousRunQueues.computeIfAbsent(info.loader(), it -> this.makeRunInfoQueue());
        return new ScriptRun(
                sources,
                info,
                this.logger,
                this::updateCurrentRunInfo,
                loader -> this.previousRunQueues.get(loader).isFirstRun(),
                loader -> this.previousRunQueues.get(loader).undoActions()
        );
    }
    
    private RunInfoQueue makeRunInfoQueue() {
        
        return new RunInfoQueue(
                () -> this.logger.info("Undoing previous actions"),
                this::determineLoggerForAction
        );
    }
    
    private List<Path> lookupScriptFiles(final Path root, final ScriptDiscoveryConfiguration discoveryConfiguration) {
        
        try {
            final FileSystem fs = root.getFileSystem();
            
            final PathMatcher correctMatcher = fs.getPathMatcher("glob:**.zs");
            final PathMatcher suspiciousMatcher = fs.getPathMatcher("glob:**.zs.txt");
            final PathMatcher combinedMatcher = it -> correctMatcher.matches(it) || suspiciousMatcher.matches(it);
            
            final List<Path> files = SuspiciousAwarePathList.of(suspiciousMatcher, this.makeSuspiciousConsumer(root, discoveryConfiguration));
            Files.walkFileTree(root, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, FileGathererHelper.of(combinedMatcher, files::add));
            
            discoveryConfiguration.retainer().retain(root, files);
            
            return files;
        } catch(final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    private Consumer<Path> makeSuspiciousConsumer(final Path root, final ScriptDiscoveryConfiguration configuration) {
        
        return switch(configuration.suspiciousNamesBehavior()) {
            case IGNORE -> it -> {};
            case WARN -> it -> this.logger.warn(
                    "Identified file with suspicious name '{}': ignoring; if this is supposed to be a script, please correct the name",
                    root.toAbsolutePath().relativize(it.toAbsolutePath())
            );
        };
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
        
        this.previousRunQueues.get(this.currentRunInfo.loader()).offer(this.currentRunInfo);
        this.currentRunInfo = null;
    }
    
    private void applyActionOutsideRun(@SuppressWarnings("unused") final IAction action) {
        
        // TODO("Should this be supported? Custom IAction impl if so?")
        throw new UnsupportedOperationException("Unable to apply an action outside of a script run");
    }
    
    private void applyActionInRun(final IAction action) {
        
        final RunInfo info = Objects.requireNonNull(this.currentRunInfo);
        
        final Logger logger = this.determineLoggerForAction(action);
        
        try {
            
            if(!action.shouldApplyOn(info.loadSource(), logger)) {
                return;
            }
            
            if(!action.validate(logger)) {
                info.enqueueAction(action, false);
                return;
            }
            
            final int nestLevel = this.nestingLevel.get();
            try {
                
                this.nestingLevel.set(nestLevel + 1);
                logger.info(this.makeNestedDescription(action, nestLevel));
                action.apply();
                info.enqueueAction(action, true);
            } finally {
                
                this.nestingLevel.set(nestLevel);
            }
            
        } catch(final Exception e) {
            logger.error("Unable to run action due to an error", e);
        }
    }
    
    private Logger determineLoggerForAction(final IAction action) {
        
        // Do not use action.logger(), as malicious mods might override it to make stuff not show up in our logger
        final Logger logger = CraftTweakerAPI.getLogger(this.checkSystemName(action));
        if(DEVELOPMENT && logger != action.logger()) {
            final String message = "Action %s attempted to hijack the logger instance: expected %s, got %s".formatted(action, logger, action.logger());
            throw new IllegalStateException(message);
        }
        return logger;
    }
    
    private String checkSystemName(final IAction action) {
        
        final String systemName = action.systemName();
        if(systemName == null || systemName.isEmpty()) {
            throw new IllegalStateException("Action " + action + " does not specify a valid system name");
        }
        return systemName;
    }
    
    private String makeNestedDescription(final IAction action, final int nestLevel) {
        
        return "-".repeat(nestLevel) + (nestLevel > 0 ? " " : "") + this.makeDescription(action);
    }
    
    private String makeDescription(final IAction action) {
        
        final String description = action.describe();
        if(description == null || description.isEmpty()) {
            return "Applying unknown action '" + action + "': tell the mod author to properly implement describe"; // TODO("Should this be an exception?")
        }
        
        return description;
    }
    
}