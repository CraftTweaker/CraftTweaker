package com.blamejared.crafttweaker.api.actions;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.logger.ILogger;
import net.minecraftforge.fml.LogicalSide;

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
    default boolean validate(ILogger logger) {
        return true;
    }
    
    
    /**
     * Used to determine which side this IAction should be applied on, defaults to {@link LogicalSide#SERVER}.
     *
     * If you need your IAction to run on the client, make this method return {@link LogicalSide#CLIENT}.
     *
     * Examples of client only actions:
     * Any action that changes translations
     *
     * If the IAction should be ran on both sides, just return {@code true}
     *
     * @param side Side to check if this IAction should be applied on.
     *
     * @return true if this IAction be applied on the given side.
     */
    default boolean shouldApplyOn(LogicalSide side) {
        return CraftTweaker.serverOverride || side.isServer();
    }
    
}
