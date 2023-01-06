package com.blamejared.crafttweaker.api.action.item;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.mixin.common.access.item.AccessItem;
import net.minecraft.world.food.FoodProperties;

public class ActionSetFood extends CraftTweakerAction implements IUndoableAction {
    
    private final IItemStack stack;
    private final FoodProperties newFood;
    private final FoodProperties oldFood;
    
    public ActionSetFood(IItemStack stack, FoodProperties newFood, FoodProperties oldFood) {
        
        this.stack = stack;
        this.newFood = newFood;
        this.oldFood = oldFood;
    }
    
    @Override
    public void apply() {
        
        ((AccessItem) this.stack.getInternal().getItem()).crafttweaker$setFoodProperties(newFood);
    }
    
    @Override
    public String describe() {
        
        if(newFood == null) {
            return "Removing food properties of " + stack.getCommandString();
        }
        return String.format("Setting food of: %s to food with stats: nutrition: %s, saturation: %s, isMeat: %s, isFastFood: %s, canAlwaysEat: %s, effects: %s", stack
                .getCommandString(), newFood.getNutrition(), newFood.getSaturationModifier(), newFood.isMeat(), newFood.isFastFood(), newFood
                .canAlwaysEat(), newFood.getEffects());
    }
    
    @Override
    public void undo() {
        
        ((AccessItem) this.stack.getInternal().getItem()).crafttweaker$setFoodProperties(oldFood);
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing modification of food for stack: " + stack.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(final IScriptLoadSource source) {
        
        return true;
    }
    
    
}
