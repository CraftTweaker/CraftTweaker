package com.blamejared.crafttweaker.platform.event;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.services.IEventHelper;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.world.item.crafting.RecipeType;

public class FabricEventHelper implements IEventHelper {
    
    @Override
    public void setBurnTime(IIngredient ingredient, int burnTime, RecipeType<?> type) {
        
        for(IItemStack stack : ingredient.getItems()) {
            FuelRegistry.INSTANCE.add(stack.getInternal().getItem(), burnTime);
        }
        IEventHelper.super.setBurnTime(ingredient, burnTime, type);
    }
    
    @Override
    public int getBurnTime(IItemStack stack) {
        
        return FuelRegistry.INSTANCE.get(stack.getInternal().getItem());
    }
    
}
