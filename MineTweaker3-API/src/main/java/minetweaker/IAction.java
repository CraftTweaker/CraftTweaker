package minetweaker;


/**
 * Defines a single use action.
 * <p>
 * Every modification made by a command must be registered to the Tweaker with
 * the Tweaker.apply method. This enables MineTweaker to keep track of actions
 * that have been performed, and enables them to be possibly rolled back later on.
 * <p>
 * There also exist semi-permanent actions. Those actions can return an override
 * key; if a newer action has the same override key as an old action, the new
 * action is considered to override the old one.
 * <p>
 * Likewise, actions can implement hashCode and equals methods to indicate that
 * they are equal. If an action is equal to a non-undoable stuck action, it will
 * be omitted from execution.
 *
 * @author Jared
 */
public interface IAction {
    
    /**
     * Executes what the action is supposed to do. This method can be called
     * again if undo() has been called in between.
     */
    void apply();
    
    
    /**
     * Describes, in a single human-readable sentence, what this specific action
     * is doing. Used in logging messages, lists, ...
     * <p>
     * Try to be as descriptive as possible without being too verbose.
     * <p>
     * Examples: - Adding Peach planks to the woodPlanks ore dictionary entry -
     * Removing a recipe for Iron Ore
     *
     * @return the description of this action
     */
    String describe();
    
    /**
     * Returns the override key. Two actions are considered to override each
     * other if their override key is equal. You can return null to indicate
     * that an action can never be overridden.
     * <p>
     * This value only makes sense for recipes that are not undoable. For
     * undoable recipes, you should return null.
     *
     * @return override key (null for actions that are undoable or which can
     * never be overridden by another action)
     */
    default Object getOverrideKey() {
        return null;
    }
}
