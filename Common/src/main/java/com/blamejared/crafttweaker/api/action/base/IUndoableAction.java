package com.blamejared.crafttweaker.api.action.base;

/**
 * Used to signify that an action should be undone, before being redone when the /reload command is ran.
 */
public interface IUndoableAction extends IRuntimeAction {
    
    void apply();
    
    String describe();
    
    void undo();
    
    String describeUndo();
    
}
