package com.blamejared.crafttweaker.api.action.item;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.platform.Services;

public class ActionSetBurnTime implements IUndoableAction {
    
    private final IIngredient ingredient;
    private final int newBurnTime;
    
    public ActionSetBurnTime(IIngredient ingredient, int newBurnTime) {
        
        this.ingredient = ingredient;
        this.newBurnTime = newBurnTime;
    }
    
    @Override
    public void apply() {
        
        Services.EVENT.setBurnTime(ingredient, newBurnTime);
    }
    
    @Override
    public String describe() {
        
        return String.format("Setting burn time of: %s to %s", ingredient.getCommandString(), newBurnTime);
    }
    
    @Override
    public void undo() {
        
        Services.EVENT.getBurnTimes().entrySet().removeIf(entry -> ingredient.contains(entry.getKey()));
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing setting of burn time for stack: " + ingredient.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(ScriptLoadingOptions.ScriptLoadSource source) {
        
        return true;
    }
    
}
