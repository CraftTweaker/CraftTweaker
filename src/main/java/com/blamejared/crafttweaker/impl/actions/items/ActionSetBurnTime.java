package com.blamejared.crafttweaker.impl.actions.items;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.impl.events.CTEventHandler;
import net.minecraftforge.fml.LogicalSide;

public class ActionSetBurnTime implements IUndoableAction {
    
    private IIngredient ingredient;
    private int newBurnTime;
    
    public ActionSetBurnTime(IIngredient ingredient, int newBurnTime) {
        
        this.ingredient = ingredient;
        this.newBurnTime = newBurnTime;
    }
    
    @Override
    public void apply() {
        
        CTEventHandler.BURN_TIMES.put(ingredient, newBurnTime);
    }
    
    @Override
    public String describe() {
        
        return String.format("Setting burn time of: %s to %s", ingredient.getCommandString(), newBurnTime);
    }
    
    @Override
    public void undo() {
        
        CTEventHandler.BURN_TIMES.entrySet().removeIf(entry -> ingredient.contains(entry.getKey()));
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing setting of burn time for stack: " + ingredient.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return true;
    }
    
}
