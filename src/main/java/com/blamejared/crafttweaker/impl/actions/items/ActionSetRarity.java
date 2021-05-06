package com.blamejared.crafttweaker.impl.actions.items;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.LogicalSide;

public class ActionSetRarity implements IUndoableAction {
    
    private final IItemStack stack;
    private final Rarity newValue;
    private final Rarity oldValue;
    
    public ActionSetRarity(IItemStack stack, Rarity newValue, Rarity oldValue) {
        
        this.stack = stack;
        this.newValue = newValue;
        this.oldValue = oldValue;
    }
    
    @Override
    public void apply() {
        
        this.stack.getInternal().getItem().rarity = newValue;
    }
    
    @Override
    public String describe() {
        
        return String.format("Set the rarity of %s to %s.", stack.getCommandString(), newValue);
    }
    
    @Override
    public void undo() {
        
        this.stack.getInternal().getItem().rarity = oldValue;
    }
    
    @Override
    public String describeUndo() {
        
        return String.format("Reset the rarity of %s to %s.", stack.getCommandString(), oldValue);
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return shouldApplySingletons();
    }
    
}