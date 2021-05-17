package com.blamejared.crafttweaker.impl.actions.items;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraftforge.fml.LogicalSide;

public class ActionSetImmuneToFire implements IUndoableAction {
    
    private final IItemStack stack;
    private final boolean newValue;
    private final boolean oldValue;
    
    public ActionSetImmuneToFire(IItemStack stack, boolean newValue, boolean oldValue) {
        
        this.stack = stack;
        this.newValue = newValue;
        this.oldValue = oldValue;
    }
    
    @Override
    public void apply() {
        
        this.stack.getInternal().getItem().burnable = newValue;
    }
    
    @Override
    public String describe() {
        
        if(newValue) {
            return stack.getCommandString() + " is now burnable.";
        } else {
            return stack.getCommandString() + " is now not burnable.";
        }
    }
    
    @Override
    public void undo() {
        
        this.stack.getInternal().getItem().burnable = oldValue;
    }
    
    @Override
    public String describeUndo() {
        
        if(oldValue) {
            return stack.getCommandString() + " is now burnable.";
        } else {
            return stack.getCommandString() + " is now not burnable.";
        }
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return shouldApplySingletons();
    }
    
}