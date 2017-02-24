/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime;

import minetweaker.IUndoableAction;
import minetweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

/**
 * @author Stan
 */
@ZenClass("minetweaker.IMineTweaker")
public interface ITweaker {
    /**
     * Retrieves the script data to be loaded.
     *
     * @return byte[] of the script data
     */
    byte[] getStagedScriptData();

    /**
     * Executes a specified MineTweaker action. Will print a log message and
     * adds the action to the undo list.
     *
     * @param action action to execute
     */
    void apply(IUndoableAction action);

    /**
     * Removes a specific item from all known recipes handlers.
     *
     * @param items items to remove
     */
    @ZenMethod
    void remove(IIngredient items);

    /**
     * Rolls back all actions performed by MineTweaker. Returns the list of
     * actions that could not be rolled back (the "stuck" ones that are not
     * undoable).
     *
     * @return stuck action list
     */
    List<IUndoableAction> rollback();

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
     * Retrieves the data from the scripts that were loaded last.
     *
     * @return scripts data
     */
    byte[] getScriptData();

    /**
     * Retrieves all actions that have been performed.
     *
     * @return actions performed
     */
    List<IUndoableAction> getActions();
    
    /**
     * Enables debug class generations
     */
    void enableDebug();
}
