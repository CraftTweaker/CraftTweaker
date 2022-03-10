package com.blamejared.crafttweaker.platform.event;

import com.blamejared.crafttweaker.api.event.type.GatherReplacementExclusionEvent;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.event.IGatherReplacementExclusionEvent;
import com.blamejared.crafttweaker.platform.services.IEventHelper;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;

public class ForgeEventHelper implements IEventHelper {
    
    @Override
    public IGatherReplacementExclusionEvent fireGatherReplacementExclusionEvent(IRecipeManager manager) {
        
        GatherReplacementExclusionEvent event = new GatherReplacementExclusionEvent(manager);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }
    
    @Override
    public void setBurnTime(IIngredient ingredient, int newBurnTime) {
        
        getBurnTimes().put(ingredient, newBurnTime);
    }
    
    @Override
    public int getBurnTime(IItemStack stack) {
        
        return ForgeHooks.getBurnTime(stack.getInternal(), null);
    }
    
}
