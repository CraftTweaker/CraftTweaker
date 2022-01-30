package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolAction;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.ForgeItemStack")
//@Document("forge/api/item/ForgeItemStack")
public interface ForgeItemStack extends IItemStack {
    
    @Override
    default int getBurnTime() {
        
        return ForgeHooks.getBurnTime(getInternal(), null);
    }
    
    /**
     * Checks if this item can perform the given ToolAction.
     *
     * @param action The action to perform.
     *
     * @return True if it can perform the action, false otherwise.
     *
     * @deprecated Method has been moved to an expansion, so it exists in IItemStack for scripters, and ExpandIItemStackForge for modders.
     */
    @Deprecated(forRemoval = true)
    default boolean canPerformAction(ToolAction action) {
        
        return getInternal().canPerformAction(action);
    }
    
}
