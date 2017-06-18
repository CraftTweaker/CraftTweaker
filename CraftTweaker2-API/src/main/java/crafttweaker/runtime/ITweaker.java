package crafttweaker.runtime;

import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.ICraftTweaker")
@ZenRegister
public interface ITweaker {
    
    /**
     * Executes a specified CraftTweaker action. Will print a log message and
     * adds the action to the undo list.
     *
     * @param action action to execute
     */
    void apply(IAction action);
    
    
    /**
     * Sets the script provider.
     *
     * @param provider provider to be set
     */
    void setScriptProvider(IScriptProvider provider);
    
    /**
     * Executes all scripts provided by the script provider.
     */
    void load();
    
    /**
     * Retrieves all actions that have been performed.
     *
     * @return actions performed
     */
    List<IAction> getActions();
    
    /**
     * Enables debug class generations
     */
    void enableDebug();
}
