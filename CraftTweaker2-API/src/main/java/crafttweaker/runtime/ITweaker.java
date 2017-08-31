package crafttweaker.runtime;

import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.preprocessor.PreprocessorManager;
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
     * Loads all scripts, choose whether to execute or not.
     * @param isSyntaxCommand if it is a syntax command it will ignore stuff like the loader group and not execute it
     * @param loaderName Name of the loader, affects whether a file gets loaded or not
     * @return Whether it was successful at loading or not
     */
    boolean loadScript(boolean isSyntaxCommand, String loaderName);


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
    
    PreprocessorManager getPreprocessorManager();
}
