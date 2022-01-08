package com.blamejared.crafttweaker.api.action.base;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.zencode.impl.util.PositionUtil;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.shared.CodePosition;

import javax.annotation.Nonnull;

/**
 * Any and all {@link org.openzen.zencode.java.ZenCodeType.Method} methods should create an instance of this class or one of it's subclasses, this is to make reloading actually work without breaking everything.
 * <p>
 * <p>
 * If an action should be reloadable, use {@link IUndoableAction} instead of this class, if a class does not implement {@link IUndoableAction} in any way, it will NOT be reloaded.
 */
public interface IAction {
    
    /**
     * Executes what the action is supposed to do.
     */
    void apply();
    
    /**
     * Describes, in a single human-readable sentence, what this specific action
     * is doing. Used in logging messages, lists, ...
     * <p>
     * Try to be as descriptive as possible without being too verbose.
     * <p>
     * Example:
     * Removing a recipe for Iron Ore
     *
     * @return the description of this action
     */
    String describe();
    
    /**
     * Used to validate the state of the action. This is called before the action is applied, and allows you to properly handle errors when things are not proper. For example if an input is null, or an ID does not exist.
     * <p>
     * Return false if the action is not in a valid state. This will prevent the apply method from being invoked and will allow the script to continue execution. It is essential that you log errors using the provided logger, this will help the end user diagnose issues with their scripts.
     *
     * @param logger ILogger object to log errors or warnings with.
     *
     * @return true if the action should run, false otherwise.
     */
    default boolean validate(Logger logger) {
        
        return true;
    }
    
    
    /**
     * Determines if an action should be applied.
     *
     * By default, actions will only be applied if the script was loaded in a reload-listener.
     * This ensures that scripts are only loaded on the server thread.
     *
     * If you need an action to apply when joining a server, you can reference {@link ScriptLoadingOptions#CLIENT_RECIPES_UPDATED_SCRIPT_SOURCE}
     *
     * @param source The current {@link ScriptLoadingOptions.ScriptLoadSource}.
     *
     * @return True if the action should be applied. False otherwise.
     */
    default boolean shouldApplyOn(ScriptLoadingOptions.ScriptLoadSource source) {
        
        return source.equals(ScriptLoadingOptions.RELOAD_LISTENER_SCRIPT_SOURCE);
    }
    
    
    /**
     * Used to retrieve the script file an line that this action was created on.
     * Uses the Stacktrace, so will not work if the action is staged an this method is called from another context.
     *
     * The created CodePosition's file will always be a virtual one, so its content cannot be accessed!
     *
     * @return The found CodePosition, or {@link CodePosition#UNKNOWN}, never {@code null}.
     */
    @Nonnull
    default CodePosition getDeclaredScriptPosition() {
        
        return PositionUtil.getZCScriptPositionFromStackTrace();
    }
    
    /**
     * Ensures that an action is only applied on a certain loader. This should be used in {@link IAction#shouldApplyOn(ScriptLoadingOptions.ScriptLoadSource)}.
     *
     * Will return true if the action should run, otherwise will log a warning of where the actions failed (if it can find it in the script, see {@link IAction#getDeclaredScriptPosition()}) and what loader it is meant to be used on, as well as the current loader.
     *
     * @param loader the name of the loader this action should run on.
     *
     * @return true if it is the correct loader.
     */
    default boolean assertLoader(String loader) {
        
        String currentLoader = CraftTweakerAPI.getCurrentRun().getLoaderActions().getLoaderName();
        if(currentLoader.equals(loader)) {
            
            return true;
        }
        
        CraftTweakerAPI.LOGGER.warn("Action '{}' ({}) can only be invoked on loader '{}'. You tried to run it on loader '{}'.", this
                .getClass()
                .getName(), getDeclaredScriptPosition().toString(), loader, currentLoader);
        return false;
    }
    
}
