package com.blamejared.crafttweaker.api.action.item;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

public class ActionSetBurnTime extends CraftTweakerAction implements IUndoableAction {
    
    private final IIngredient ingredient;
    private final int newBurnTime;
    private final RecipeType<?> recipeType;
    
    public ActionSetBurnTime(IIngredient ingredient, int newBurnTime) {
        
        this.ingredient = ingredient;
        this.newBurnTime = newBurnTime;
        this.recipeType = RecipeType.SMELTING;
    }
    
    public ActionSetBurnTime(IIngredient ingredient, int newBurnTime, RecipeType<?> type) {
        
        this.ingredient = ingredient;
        this.newBurnTime = newBurnTime;
        this.recipeType = type;
    }
    
    @Override
    public void apply() {
        
        Services.EVENT.setBurnTime(ingredient, newBurnTime, recipeType);
    }
    
    @Override
    public String describe() {
        
        return String.format("Setting burn time of: %s to %s for type %s", ingredient.getCommandString(), newBurnTime, recipeType);
    }
    
    @Override
    public void undo() {
        
        Services.EVENT.getBurnTimes()
                .getOrDefault(recipeType, List.of())
                .removeIf(pair -> ingredient.contains(pair.getFirst()));
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing setting of burn time of stack %s for type %s".formatted(ingredient.getCommandString(), recipeType);
    }
    
    @Override
    public boolean shouldApplyOn(final IScriptLoadSource source) {
        
        return true;
    }
    
}
