/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime;

import java.io.Closeable;
import java.util.List;
import minetweaker.IUndoableAction;
import minetweaker.minecraft.item.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.IMineTweaker")
public interface IMineTweaker {
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
	 * Registers a script provider.
	 * 
	 * @param provider
	 * @return 
	 */
	public Closeable registerScriptProvider(IScriptProvider provider);
	
	public void load();
}
