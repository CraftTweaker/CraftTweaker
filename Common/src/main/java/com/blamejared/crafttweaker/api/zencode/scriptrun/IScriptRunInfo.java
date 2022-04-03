package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;

import java.util.List;

/**
 * Gives information relative to a specific {@link IScriptRun}.
 *
 * @since 9.1.0
 */
public interface IScriptRunInfo {
    
    /**
     * Obtains the configuration used to execute the script run.
     *
     * @return The configuration used to execute the script run.
     *
     * @since 9.1.0
     */
    ScriptRunConfiguration configuration();
    
    /**
     * Gets a list of all {@link IAction}s that have been applied during this run.
     *
     * <p>The returned list is not modifiable and acts as a simple log.</p>
     *
     * @return A list containing all applied actions.
     *
     * @since 9.1.0
     */
    List<IAction> appliedActions();
    
    /**
     * Gets a list of all {@link IAction}s that could not have been applied due to them being invalid.
     *
     * <p>The returned list is not modifiable and acts as a simple log.</p>
     *
     * @return A list containing all invalid actions.
     *
     * @since 9.1.0
     */
    List<IAction> invalidActions();
    
    /**
     * Indicates whether a branding message should be printed to the logs during the script run.
     *
     * <p>The branding message acts as a referral to CraftTweaker's Patreon.</p>
     *
     * @return Whether a branding message should be printed.
     *
     * @since 9.1.0
     */
    boolean displayBranding();
    
    /**
     * Indicates whether the generated classes should be dumped for debugging purposes.
     *
     * @return Whether the generated classes should be dumped.
     *
     * @since 9.1.0
     */
    boolean dumpClasses();
    
    /**
     * Indicates whether the targeted run will be or has been the first for the targeted loader.
     *
     * @return Whether this was a first run or not.
     *
     * @since 9.1.0
     */
    boolean isFirstRun();
    
    /**
     * Gets the {@link IScriptLoader} that will be used to execute this script run.
     *
     * @return The loader used to execute this script run.
     *
     * @implSpec This is equivalent to calling {@link ScriptRunConfiguration#loader()} on {@link #configuration()}.
     * @since 9.1.0
     */
    default IScriptLoader loader() {
        
        return this.configuration().loader();
    }
    
    /**
     * Gets the {@link IScriptLoadSource} that is responsible for this script run.
     *
     * @return The load source responsible for this run.
     *
     * @implSpec This is equivalent to calling {@link ScriptRunConfiguration#loadSource()} on {@link #configuration()}.
     * @since 9.1.0
     */
    default IScriptLoadSource loadSource() {
        
        return this.configuration().loadSource();
    }
    
}
