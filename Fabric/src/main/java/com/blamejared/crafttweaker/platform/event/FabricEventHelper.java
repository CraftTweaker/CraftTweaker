package com.blamejared.crafttweaker.platform.event;

import com.blamejared.crafttweaker.api.event.CraftTweakerEvents;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.event.IGatherReplacementExclusionEvent;
import com.blamejared.crafttweaker.platform.services.IEventHelper;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;

public class FabricEventHelper implements IEventHelper {
    
    @Override
    public IGatherReplacementExclusionEvent fireGatherReplacementExclusionEvent(final IRecipeManager<?> manager) {
        
        GatherReplacementExclusionEvent event = new GatherReplacementExclusionEvent(manager);
        CraftTweakerEvents.GATHER_REPLACEMENT_EXCLUSION_EVENT.invoker().accept(event);
        return event;
    }
    
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
