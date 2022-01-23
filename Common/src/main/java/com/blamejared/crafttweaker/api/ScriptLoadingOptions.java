package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.zencode.bracket.IgnorePrefixCasingBracketParser;
import com.blamejared.crafttweaker.api.zencode.impl.CraftTweakerDefaultScriptRunConfiguration;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.parser.BracketExpressionParser;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ScriptLoadingOptions {
    
    public static final ScriptLoadSource RELOAD_LISTENER_SCRIPT_SOURCE = new ScriptLoadSource(CraftTweakerConstants.rl("reload_listener"));
    public static final ScriptLoadSource CLIENT_RECIPES_UPDATED_SCRIPT_SOURCE = new ScriptLoadSource(CraftTweakerConstants.rl("client_recipes_updated"));
    
    private boolean format;
    private boolean execute;
    private String loaderName = CraftTweakerConstants.DEFAULT_LOADER_NAME;
    private ScriptLoadSource source = RELOAD_LISTENER_SCRIPT_SOURCE;
    private LegacyRunConfig runConfiguration = this::legacyConfig;
    
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
    
    public ScriptLoadingOptions setRunConfiguration(final ScriptRunConfiguration runConfiguration) {
        
        this.runConfiguration = LegacyRunConfig.adapt(runConfiguration);
        return this;
    }
    
    @Deprecated(forRemoval = true) // Replace with equivalent of LegacyRunConfig#adapt
    public void configureRun(final IgnorePrefixCasingBracketParser bep, final ScriptingEngine engine) throws CompileException {
        
        this.runConfiguration.configureRun(bep, engine);
    }
    
    @Deprecated(forRemoval = true)
    private void legacyConfig(final IgnorePrefixCasingBracketParser bep, final ScriptingEngine engine) throws CompileException {
        
        CraftTweakerAPI.LOGGER.info("The loader '{}' is using the legacy script run configuration: this will not survive a 1.19 update", this.loaderName);
        Services.EVENT.fireRegisterBEPEvent(bep); // Backwards compatibility event, should not be used anymore
        LegacyRunConfig.adapt(CraftTweakerDefaultScriptRunConfiguration.DEFAULT_CONFIGURATION)
                .configureRun(bep, engine);
    }
    
    public record ScriptLoadSource(ResourceLocation id) {}
    
    @FunctionalInterface
    public interface ScriptRunConfiguration {
        
        void configureRun(
                final BiConsumer<String, BracketExpressionParser> registrationFunction,
                final Consumer<JavaNativeModule> addingFunction,
                final ScriptingEngine engine
        ) throws CompileException;
        
    }
    
    @Deprecated(forRemoval = true)
    @FunctionalInterface
    private interface LegacyRunConfig {
        
        static LegacyRunConfig adapt(final ScriptRunConfiguration config) {
            
            return (bep, engine) -> config.configureRun(bep::register, it -> it.registerBEP(bep), engine);
        }
        
        void configureRun(final IgnorePrefixCasingBracketParser parser, final ScriptingEngine engine) throws CompileException;
        
    }
    
}
