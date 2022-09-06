package com.blamejared.crafttweaker.platform.event;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.services.IEventHelper;
import net.minecraftforge.common.ForgeHooks;

public class ForgeEventHelper implements IEventHelper {
    
    @Override
    public int getBurnTime(IItemStack stack) {
        
        return ForgeHooks.getBurnTime(stack.getInternal(), null);
    }
    
}
