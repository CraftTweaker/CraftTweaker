package com.blamejared.crafttweaker.impl.actions.items;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.events.CTEventHandler;
import net.minecraftforge.fml.LogicalSide;

public class ActionSetBurnTime implements IUndoableAction {
    
    private IItemStack stack;
    private int newBurnTime;
    
    public ActionSetBurnTime(IItemStack stack, int newBurnTime) {
        this.stack = stack;
        this.newBurnTime = newBurnTime;
    }
    
    @Override
    public void apply() {
        CTEventHandler.BURN_TIMES.put(stack, newBurnTime);
    }
    
    @Override
    public String describe() {
        return String.format("Setting burn time of: %s to %s", stack.getCommandString(), newBurnTime);
    }
    
    @Override
    public void undo() {
        CTEventHandler.BURN_TIMES.entrySet().removeIf(entry -> stack.matches(entry.getKey()));
    }
    
    @Override
    public String describeUndo() {
        return "Undoing setting of burn time for stack: " + stack.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return true;
    }
}
