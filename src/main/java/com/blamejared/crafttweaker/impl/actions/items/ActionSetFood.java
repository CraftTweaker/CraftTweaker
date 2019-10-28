package com.blamejared.crafttweaker.impl.actions.items;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.food.MCFood;
import net.minecraft.item.Food;
import net.minecraftforge.fml.LogicalSide;

public class ActionSetFood implements IUndoableAction {
    
    private IItemStack stack;
    private Food newFood;
    private Food oldFood;
    
    public ActionSetFood(IItemStack stack, MCFood newFood) {
        this.stack = stack;
        this.newFood = newFood.getInternal();
        this.oldFood = stack.getFood().getInternal();
    }
    
    @Override
    public void apply() {
        this.stack.getInternal().getItem().food = newFood;
    }
    
    @Override
    public String describe() {
        return String.format("Setting food of: %s to food with stats: hunger: %s, saturation: %s, isMeat: %s, isFastToEat: %s, canEatWhenFull: %s, effects: %s", stack.getCommandString(), newFood.getHealing(), newFood.getSaturation(), newFood.isMeat(), newFood.isFastEating(), newFood.canEatWhenFull(), newFood.getEffects());
    }
    
    @Override
    public void undo() {
        this.stack.getInternal().getItem().food = oldFood;
    }
    
    @Override
    public String describeUndo() {
        return "Undoing setting of food for stack: " + stack.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return true;
    }
}
