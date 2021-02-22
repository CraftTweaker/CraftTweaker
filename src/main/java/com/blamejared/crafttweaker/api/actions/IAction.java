package com.blamejared.crafttweaker.api.actions;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.util.ClientHelper;
import com.blamejared.crafttweaker.api.util.ServerHelper;
import com.blamejared.crafttweaker.api.zencode.impl.util.PositionUtil;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
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
     * Special helper method to handle actions that are always applied on the server, but are only applied on the client when connected to a server.
     *
     * This is mainly used when you have to deal with reloading singleton values, since the old cached value on the client, will be the value set on the server.
     *
     * An example of where this is used is in {@link com.blamejared.crafttweaker.impl.actions.items.ActionSetFood},
     * Since the food value of an Item in single player is shared on the client and the server (threads),
     * When it gets the old food value (so it can undo the action on /reload) on the client, it gets the value that was set by the server.
     *
     * @return true on the server distribution (and threads). true when the client is connected to a server (not in singleplayer). false if the client is in single player.
     */
    default boolean shouldApplySingletons() {
        
        return DistExecutor.safeRunForDist(() -> ClientHelper::shouldApplyServerActionOnClient, () -> ServerHelper::alwaysTrue);
    }
    
    /**
     * Ensures that an action is only applied on a certain loader. This should be used in {@link IAction#shouldApplyOn(LogicalSide)}.
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
        
        CraftTweakerAPI.logWarning("Action '%s' (%s) can only be invoked on loader '%s'. You tried to run it on loader '%s'.", this
                .getClass()
                .getName(), getDeclaredScriptPosition().toString(), loader, currentLoader);
        return false;
    }
    
}
