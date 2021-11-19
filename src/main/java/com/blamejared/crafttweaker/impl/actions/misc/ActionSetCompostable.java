package com.blamejared.crafttweaker.impl.actions.misc;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.block.ComposterBlock;
import net.minecraftforge.fml.LogicalSide;

public class ActionSetCompostable implements IUndoableAction {
    
    private final IItemStack stack;
    private final float newValue;
    private final float oldValue;
    
    public ActionSetCompostable(IItemStack stack, float newValue) {
        
        this.stack = stack;
        this.newValue = newValue;
        this.oldValue = ComposterBlock.CHANCES.getOrDefault(stack.getInternal().getItem(), -1);
    }
    
    @Override
    public void apply() {
        
        if(newValue <= 0) {
            ComposterBlock.CHANCES.remove(stack.getInternal().getItem());
        } else {
            ComposterBlock.CHANCES.put(stack.getInternal().getItem(), newValue);
        }
    }
    
    @Override
    public String describe() {
        
        return "Setting Composter value of: " + stack.getCommandString() + ", to: " + newValue + ", from: " + oldValue;
    }
    
    @Override
    public void undo() {
        
        if(oldValue <= 0) {
            ComposterBlock.CHANCES.remove(stack.getInternal().getItem());
        } else {
            ComposterBlock.CHANCES.put(stack.getInternal().getItem(), oldValue);
        }
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing setting Composter value of: " + stack.getCommandString() + ", to: " + newValue + ", reverting to: " + oldValue;
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return shouldApplySingletons();
    }
    
}
