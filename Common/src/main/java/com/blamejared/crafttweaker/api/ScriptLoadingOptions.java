package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.zencode.impl.CraftTweakerDefaultScriptRunConfiguration;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IBepRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IBepToModuleAdder;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptLoadingOptionsView;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunConfigurator;
import com.blamejared.crafttweaker.api.zencode.scriptrun.WrappingBracketParser;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.shared.CompileException;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

// TODO("Move to zencode/scriptrun package in 1.19")
public class ScriptLoadingOptions implements IScriptLoadingOptionsView {
    
    public static final ScriptLoadSource RELOAD_LISTENER_SCRIPT_SOURCE = new ScriptLoadSource(CraftTweakerConstants.rl("reload_listener"));
    public static final ScriptLoadSource CLIENT_RECIPES_UPDATED_SCRIPT_SOURCE = new ScriptLoadSource(CraftTweakerConstants.rl("client_recipes_updated"));
    
    private final Set<String> inheritedLoaders = new LinkedHashSet<>(List.of(CraftTweakerConstants.DEFAULT_LOADER_NAME)); // TODO("Empty set in 1.19")
    private boolean format;
    private boolean execute;
    private String loaderName = CraftTweakerConstants.DEFAULT_LOADER_NAME;
    private ScriptLoadSource source = RELOAD_LISTENER_SCRIPT_SOURCE;
    private IScriptRunConfigurator runConfigurator = LegacyConfig.INSTANCE;
    
    public ScriptLoadingOptions() {
    
    }
    
    /**
     * Causes the scripts to be formatted and outputted in the "formatted" folder
     * Used e.g by {@code /ct format}
     */
    public ScriptLoadingOptions format() {
        
        this.format = true;
        return this;
    }
    
    /**
     * Causes the scripts to be executed
     * If not set, then this will stop after checking the syntax
     */
    public ScriptLoadingOptions execute() {
        
        return setExecute(true);
    }
    
    /**
     * @see #format()
     */
    @Override
    public boolean isFormat() {
        
        return format;
    }
    
    /**
     * @see #format()
     */
    public ScriptLoadingOptions setFormat(boolean format) {
        
        this.format = format;
        return this;
    }
    
    /**
     * @see #execute()
     */
    @Override
    public boolean isExecute() {
        
        return execute;
    }
    
    /**
     * @see #execute()
     */
    public ScriptLoadingOptions setExecute(boolean execute) {
        
        this.execute = execute;
        return this;
    }
    
    /**
     * The current loader name
     */
    @Override
    public String getLoaderName() {
        
        return loaderName;
    }
    
    /**
     * Sets the loader name.
     * Loader names are case insensitive, but are <strong>not</strong> Regular Expressions
     */
    public ScriptLoadingOptions setLoaderName(@Nonnull String loaderName) {
        
        this.loaderName = loaderName;
        return this;
    }
    
    /**
     * Gets the source type of this script load.
     *
     * @return The source type of this script load.
     */
    @Override
    public ScriptLoadSource getSource() {
        
        return source;
    }
    
    /**
     * Sets the source of this script load.
     *
     * @param source The source of this script load.
     */
    public ScriptLoadingOptions setSource(ScriptLoadSource source) {
        
        this.source = source;
        return this;
    }
    
    @Override
    public Collection<String> getInheritedLoaders() {
        
        return Collections.unmodifiableCollection(this.inheritedLoaders);
    }
    
    @Deprecated(forRemoval = true) // TODO("Remove in 1.19")
    public ScriptLoadingOptions noInherit() {
        
        this.inheritedLoaders.clear();
        return this;
    }
    
    public ScriptLoadingOptions inheritFromLoader(final String... otherLoaders) {
        
        this.inheritedLoaders.addAll(Arrays.asList(otherLoaders));
        return this;
    }
    
    public ScriptLoadingOptions setRunConfigurator(final IScriptRunConfigurator runConfigurator) {
        
        this.runConfigurator = runConfigurator;
        return this;
    }
    
    @Deprecated(forRemoval = true) // Replace with equivalent of LegacyRunConfig#adapt
    public void configureRun(final IBepRegistrationHandler bepRegistrationHandler, final IBepToModuleAdder moduleAdder, final ScriptingEngine engine) throws CompileException {
        
        this.runConfigurator.configure(bepRegistrationHandler, moduleAdder, engine, this);
    }
    
    public record ScriptLoadSource(ResourceLocation id) {}
    
    @SuppressWarnings("ClassCanBeRecord")
    private static final class LegacyConfig implements IScriptRunConfigurator {
        
        static final IScriptRunConfigurator INSTANCE = new LegacyConfig(CraftTweakerDefaultScriptRunConfiguration.INSTANCE);
        
        private final IScriptRunConfigurator delegate;
        
        private LegacyConfig(final IScriptRunConfigurator delegate) {
            
            this.delegate = delegate;
        }
        
        @Override
        public void configure(
                final IBepRegistrationHandler registrationHandler,
                final IBepToModuleAdder moduleAdder,
                final ScriptingEngine engine,
                final IScriptLoadingOptionsView options
        ) throws CompileException {
            
            CraftTweakerAPI.LOGGER.info("The loader '{}' is using the legacy script run configuration: this will not survive a 1.19 update", options.getLoaderName());
            Services.EVENT.fireRegisterBEPEvent(new WrappingBracketParser(registrationHandler)); // Backwards compatibility event, should not be used anymore
            this.delegate.configure(registrationHandler, moduleAdder, engine, options);
        }
        
    }
    
}
