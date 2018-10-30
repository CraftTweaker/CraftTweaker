package crafttweaker.runtime;

import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.network.NetworkSide;
import crafttweaker.preprocessor.PreprocessorManager;
import crafttweaker.runtime.events.*;
import crafttweaker.util.IEventHandler;
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
    
    
    void loadScript(boolean isSyntaxCommand, ScriptLoader loader);


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
     * Gets the preprocessor manager which deals with adding and removing preprocessors
     */
    PreprocessorManager getPreprocessorManager();
    
    /**
     * Gets the network side on which the game is currently running on
     */
    NetworkSide getNetworkSide();
    
    /**
     * Used to set the side the game is running on, is set in the construction event of the Main package
     */
    void setNetworkSide(NetworkSide networkSide);
    
    /**
     * Gets called as soon as the script start getting loaded (before the zs files are getting loaded)
     */
    void registerLoadStartedEvent(IEventHandler<CrTLoaderLoadingEvent.Started> eventHandler);
    
    /**
     * Gets called once the loader has finished loading
     */
    void registerLoadFinishedEvent(IEventHandler<CrTLoaderLoadingEvent.Finished> eventHandler);
    
    /**
     * Gets called if the loader was aborted for any reason
     */
    void registerLoadAbortedEvent(IEventHandler<CrTLoaderLoadingEvent.Aborted> eventHandler);
    
    
    /**
     * Gets called just before the script file is loaded
     */
    void registerScriptLoadPreEvent(IEventHandler<CrTScriptLoadingEvent.Pre> eventHandler);
    
    
    /**
     * Gets called as soon as the script file is done loading.
     */
    void registerScriptLoadPostEvent(IEventHandler<CrTScriptLoadingEvent.Post> eventHandler);
    
    /**
     * Gets all registered loaders
     */
    List<ScriptLoader> getLoaders();
    
    /**
     * Gets or adds a new loader
     * Be careful as this loader might become invalidated if merged with another loader so always call this method anew if possible.
     * @param nameAndAliases the Names the loader will be callable under, if an existing loader already has any of the names, they will be merged
     * @return The added, retrieved or changed loader
     */
    ScriptLoader getOrCreateLoader(String... nameAndAliases);
}
