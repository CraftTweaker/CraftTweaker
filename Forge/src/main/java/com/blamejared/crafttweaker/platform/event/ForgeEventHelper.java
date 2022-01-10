package com.blamejared.crafttweaker.platform.event;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.zencode.bracket.IgnorePrefixCasingBracketParser;
import com.blamejared.crafttweaker.impl.event.CTCommandRegisterEvent;
import com.blamejared.crafttweaker.impl.event.CTRegisterBEPEvent;
import com.blamejared.crafttweaker.platform.services.IEventHelper;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;

public class ForgeEventHelper implements IEventHelper {
    
    @Override
    public void fireRegisterBEPEvent(IgnorePrefixCasingBracketParser bep) {
        
        MinecraftForge.EVENT_BUS.post(new CTRegisterBEPEvent(bep));
    }
    
    @Override
    public void fireCTCommandRegisterEvent() {
        
        MinecraftForge.EVENT_BUS.post(new CTCommandRegisterEvent());
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
