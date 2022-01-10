package com.blamejared.crafttweaker.platform.event;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.zencode.bracket.IgnorePrefixCasingBracketParser;
import com.blamejared.crafttweaker.impl.event.CraftTweakerEvents;
import com.blamejared.crafttweaker.platform.services.IEventHelper;
import net.fabricmc.fabric.api.registry.FuelRegistry;

public class FabricEventHelper implements IEventHelper {
    
    @Override
    public void fireRegisterBEPEvent(IgnorePrefixCasingBracketParser bep) {
        
        CraftTweakerEvents.REGISTER_BEP_EVENT.invoker().accept(bep);
    }
    
    @Override
    public void fireCTCommandRegisterEvent() {
        
        CraftTweakerEvents.COMMAND_REGISTER_EVENT.invoker().accept(new CTCommandRegisterEvent());
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
