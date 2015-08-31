/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime;

import java.util.List;
import minetweaker.IUndoableAction;
import minetweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.IMineTweaker")
public interface ITweaker {
	/**
	 * Retrieves the script data to be loaded.
	 * 
	 * @return
	 */
	public byte[] getStagedScriptData();

	/**
	 * Executes a specified MineTweaker action. Will print a log message and
	 * adds the action to the undo list.
	 * 
	 * @param action action to execute
	 */
	public void apply(IUndoableAction action);

	/**
	 * Removes a specific item from all known recipes handlers.
	 * 
	 * @param items items to remove
	 */
	@ZenMethod
	public void remove(IIngredient items);

	/**
	 * Rolls back all actions performed by MineTweaker. Returns the list of
	 * actions that could not be rolled back (the "stuck" ones that are not
	 * undoable).
	 * 
	 * @return stuck action list
	 */
	public List<IUndoableAction> rollback();

	/**
	 * Sets the script provider.
	 * 
	 * @param provider
	 * @return
	 */
	public void setScriptProvider(IScriptProvider provider);

	/**
	 * Executes all scripts provided by the script provider.
	 */
	public void load();

	/**
	 * Retrieves the data from the scripts that were loaded last.
	 * 
	 * @return scripts data
	 */
	public byte[] getScriptData();
}
