package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import net.minecraft.resources.ResourceLocation;

/**
 * Holds information relative to the configuration for a specific {@link IScriptRun}.
 *
 * @param loader     The {@link IScriptLoader} that will be used to execute the run.
 * @param loadSource The {@link IScriptLoadSource} responsible for creating the run.
 * @param runKind    The {@linkplain RunKind kind of run} that will be executed.
 *
 * @since 9.1.0
 */
public record ScriptRunConfiguration(IScriptLoader loader, IScriptLoadSource loadSource, RunKind runKind) {
    
    /**
     * Indicates the kind of run that the {@link IScriptRun} will perform.
     *
     * @since 9.1.0
     */
    public enum RunKind {
        /**
         * Indicates that the scripts will not be executed, rather only their syntax will be checked.
         *
         * @since 9.1.0
         */
        SYNTAX_CHECK,
        /**
         * Indicates that no script will be executed, but an automatic formatter will be applied, bringing them in line
         * with the suggested coding style.
         *
         * @since 9.1.0
         */
        FORMAT,
        /**
         * Indicates that all scripts will be run and the various actions will be applied.
         *
         * @since 9.1.0
         */
        EXECUTE,
        GAME_TEST
    }
    
    /**
     * Creates a {@link ScriptRunConfiguration} with the given info, performing lookups if necessary.
     *
     * @param loader     The name of the loader that will be used to execute the run.
     * @param loadSource The {@link ResourceLocation} identifying the load source responsible for creating the run.
     * @param runKind    The {@linkplain RunKind kind of run} that will be executed.
     *
     * @throws IllegalArgumentException If the loader or the load source are not registered.
     * @since 9.1.0
     */
    public ScriptRunConfiguration(final String loader, final ResourceLocation loadSource, final RunKind runKind) {
        
        this(IScriptLoader.find(loader), IScriptLoadSource.find(loadSource), runKind);
    }
    
}
