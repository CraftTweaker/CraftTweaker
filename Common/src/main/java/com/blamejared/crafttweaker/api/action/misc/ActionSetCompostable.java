package com.blamejared.crafttweaker.api.action.misc;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import net.minecraft.world.level.block.ComposterBlock;

public class ActionSetCompostable extends CraftTweakerAction implements IUndoableAction {
    
    private final IItemStack stack;
    private final float newValue;
    private final float oldValue;
    
    public ActionSetCompostable(IItemStack stack, float newValue) {
        
        this.stack = stack;
        this.newValue = newValue;
        this.oldValue = ComposterBlock.COMPOSTABLES.getOrDefault(stack.getInternal().getItem(), -1);
    }
    
    @Override
    public void apply() {
        
        if(newValue <= 0) {
            ComposterBlock.COMPOSTABLES.remove(stack.getInternal().getItem());
        } else {
            ComposterBlock.COMPOSTABLES.put(stack.getInternal().getItem(), newValue);
        }
    }
    
    @Override
    public String describe() {
        
        return "Setting Composter value of: " + stack.getCommandString() + ", to: " + newValue + ", from: " + oldValue;
    }
    
    @Override
    public void undo() {
        
        if(oldValue <= 0) {
            ComposterBlock.COMPOSTABLES.remove(stack.getInternal().getItem());
        } else {
            ComposterBlock.COMPOSTABLES.put(stack.getInternal().getItem(), oldValue);
        }
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing setting Composter value of: " + stack.getCommandString() + ", to: " + newValue + ", reverting to: " + oldValue;
    }
    
    
    @Override
    public boolean shouldApplyOn(final IScriptLoadSource source) {
        
        return true;
    }
    
}
