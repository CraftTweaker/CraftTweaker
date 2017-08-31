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
     * @param loaderName Name of the loader, affects whether a file gets loaded or not
     * @return Whether it was successful at loading or not
     */
    boolean loadScript(boolean executeScripts, String loaderName);


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
    
    /**
     * No errors will be created for brackethandlers in these files
     * @param filename
     */
    void addFileToIgnoreBracketErrors(String filename);
    
    PreprocessorManager getPreprocessorManager();
}
