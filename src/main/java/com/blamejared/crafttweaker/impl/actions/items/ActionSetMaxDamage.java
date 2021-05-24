package com.blamejared.crafttweaker.impl.actions.items;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraftforge.fml.LogicalSide;

public class ActionSetMaxDamage implements IUndoableAction {
    
    private final IItemStack stack;
    private final int newValue;
    private final int oldValue;
    
    public ActionSetMaxDamage(IItemStack stack, int newValue, int oldValue) {
        
        this.stack = stack;
        this.newValue = newValue;
        this.oldValue = oldValue;
    }
    
    @Override
    public void apply() {
        
        this.stack.getInternal().getItem().maxDamage = newValue;
    }
    
    @Override
    public String describe() {
        
        return String.format("Set the max damage of %s to %s.", stack.getCommandString(), newValue);
    }
    
    @Override
    public void undo() {
        
        this.stack.getInternal().getItem().maxDamage = oldValue;
    }
    
    @Override
    public String describeUndo() {
        
        return String.format("Reset the max damage of %s to %s.", stack.getCommandString(), oldValue);
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return shouldApplySingletons();
    }
    
}