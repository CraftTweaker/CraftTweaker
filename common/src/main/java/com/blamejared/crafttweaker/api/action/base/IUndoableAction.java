package com.blamejared.crafttweaker.api.action.base;

/**
 * Represents an action that should be run on every game reload and that requires additional cleanup code.
 *
 * <p>Refer to {@link IRuntimeAction} and {@link IAction} for more information.</p>
 *
 * @since 9.1.0
 */
public interface IUndoableAction extends IRuntimeAction {
    
    /**
     * Undoes all changes carried out by the action.
     *
     * @since 9.1.0
     */
    void undo();
    
    /**
     * Gets a human-readable description of the action rollback.
     *
     * <p>This message is used for logging and to surface information to the user when something goes wrong. It is thus
     * customary to describe the rollback as accurately as possible without being too verbose.</p>
     *
     * <p>It is not allowed to return a {@code null} or otherwise empty description for the rollback: doing so will
     * raise an error at runtime.</p>
     *
     * @return A description of the current action rollback.
     *
     * @since 9.1.0
     */
    String describeUndo();
    
}
