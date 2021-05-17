package com.blamejared.crafttweaker.impl.actions.items;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraftforge.fml.LogicalSide;

public class ActionSetMaxStackSize implements IUndoableAction {
    
    private final IItemStack stack;
    private final int newValue;
    private final int oldValue;
    
    public ActionSetMaxStackSize(IItemStack stack, int newValue, int oldValue) {
        
        this.stack = stack;
        this.newValue = newValue;
        this.oldValue = oldValue;
    }
    
    @Override
    public void apply() {
        
        this.stack.getInternal().getItem().maxStackSize = newValue;
    }
    
    @Override
    public String describe() {
        
        return String.format("Set the max stacksize of %s to %s.", stack.getCommandString(), newValue);
    }
    
    @Override
    public void undo() {
        
        this.stack.getInternal().getItem().maxStackSize = oldValue;
    }
    
    @Override
    public String describeUndo() {
        
        return String.format("Reset the max stacksize of %s to %s.", stack.getCommandString(), oldValue);
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return shouldApplySingletons();
    }
    
}