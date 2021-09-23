package com.blamejared.crafttweaker.api.actions;

/**
 * Used to signify that an action should be undone, before being redone when the /reload command is ran.
 */
public interface IUndoableAction extends IRuntimeAction {
    
    void undo();
    
    String describeUndo();
    
}
