package minetweaker;

/**
 * Defines a (possibly) undoable action.
 * 
 * Every modification made by a command must be registered to the Tweaker with
 * the Tweaker.apply method. This enables MineTweaker to keep track of actions
 * that have been performed, and enables them to be rolled back later on.
 * 
 * Actions exist in two flavors: undoable and permanent. If an action returns
 * canUndo(), the action is undoable, otherwise it is permanent. Permanent
 * actions cannot be used in server scripts.
 * 
 * @author Stan Hebben
 */
public interface IUndoableAction {
	/**
	 * Executes what the action is supposed to do. This method can be called
	 * again if undo() has been called in between.
	 */
	public void apply();
	
	/**
	 * Checks if this action can be undone. If this method returns true, it
	 * must implement undo() properly. If this method returns false, the action
	 * is considered permanent, undo() will never be called, and the action
	 * cannot be executed as part of a server script. Additionally, the
	 * minetweaker.clear() function will not work and minetweaker.canClear
	 * will return false.
	 * 
	 * @return undoable status
	 */
	public boolean canUndo();
	
	/**
	 * Undoes the action. Will only be called after apply() has been executed.
	 * After an undo, apply may be called again. They could be done multiple
	 * times in certain scenarios.
	 */
	public void undo();
	
	/**
	 * Describes, in a single human-readable sentence, what this specific action
	 * is doing. Used in logging messages, lists, ...
	 * 
	 * Try to be as descriptive as possible without being too verbose.
	 * 
	 * Examples:
	 * - Adding Peach planks to the woodPlanks ore dictionary entry
	 * - Removing a recipe for Iron Ore
	 * 
	 * @return the description of this action
	 */
	public String describe();
	
	/**
	 * Describes what this action does if it is undone. Similar to the describe()
	 * method. No implementation is needed if the action cannot be undone.
	 * 
	 * @return the description of this action, when undone
	 */
	public String describeUndo();
}
