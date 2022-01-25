package com.blamejared.crafttweaker.platform.event;

import com.blamejared.crafttweaker.api.event.CraftTweakerEvents;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.event.IGatherReplacementExclusionEvent;
import com.blamejared.crafttweaker.api.zencode.bracket.IgnorePrefixCasingBracketParser;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IBepRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptLoadingOptionsView;
import com.blamejared.crafttweaker.platform.services.IEventHelper;
import net.fabricmc.fabric.api.registry.FuelRegistry;

public class FabricEventHelper implements IEventHelper {
    
    @Deprecated(forRemoval = true)
    @Override
    public void fireRegisterBEPEvent(final IgnorePrefixCasingBracketParser bep) {
        
        CraftTweakerEvents.REGISTER_BEP_EVENT.invoker().accept(bep);
    }
    
    @Override
    public void fireCustomRegisterBepEvent(final IScriptLoadingOptionsView view, final IBepRegistrationHandler handler) {
        
        CraftTweakerEvents.REGISTER_CUSTOM_BEP_EVENT.invoker().accept(new CustomBepRegistrationEvent(view, handler));
    }
    
    @Override
    public void fireCTCommandRegisterEvent() {
        
        CraftTweakerEvents.COMMAND_REGISTER_EVENT.invoker().accept(new CTCommandRegisterEvent());
    }
    
    @Override
    public IGatherReplacementExclusionEvent fireGatherReplacementExclusionEvent(final IRecipeManager manager) {
        
        GatherReplacementExclusionEvent event = new GatherReplacementExclusionEvent(manager);
        CraftTweakerEvents.GATHER_REPLACEMENT_EXCLUSION_EVENT.invoker().accept(event);
        return event;
    }
    
    @Override
    public void setBurnTime(IIngredient ingredient, int burnTime) {
        
        for(IItemStack stack : ingredient.getItems()) {
            FuelRegistry.INSTANCE.get(stack.getInternal().getItem());
        }
        getBurnTimes().put(ingredient, burnTime);
    }
    
    @Override
    public int getBurnTime(IItemStack stack) {
        
        return FuelRegistry.INSTANCE.get(stack.getInternal().getItem());
    }
    
}
