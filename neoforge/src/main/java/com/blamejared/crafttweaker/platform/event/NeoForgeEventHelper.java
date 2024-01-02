package com.blamejared.crafttweaker.platform.event;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.services.IEventHelper;
import net.neoforged.neoforge.common.CommonHooks;

public class NeoForgeEventHelper implements IEventHelper {
    
    @Override
    public int getBurnTime(IItemStack stack) {
        
        return CommonHooks.getBurnTime(stack.getInternal(), null);
    }
    
}
