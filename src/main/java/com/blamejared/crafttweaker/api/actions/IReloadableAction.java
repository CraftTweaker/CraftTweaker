package com.blamejared.crafttweaker.api.actions;

import com.blamejared.crafttweaker.api.logger.*;

public interface IReloadableAction extends IAction {
    
    
    void undo();
    
    void describeUndo(ILogger logger);
}
