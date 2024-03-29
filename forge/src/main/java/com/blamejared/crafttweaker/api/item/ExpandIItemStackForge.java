package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.common.ToolAction;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.item.IItemStack")
@Document("forge/api/item/ForgeIItemStackExpansions")
public class ExpandIItemStackForge {
    
    /**
     * Checks if this item can perform the given ToolAction.
     *
     * @param action The action to perform.
     *
     * @return True if it can perform the action, false otherwise.
     */
    @ZenCodeType.Method
    public static boolean canPerformAction(IItemStack internal, ToolAction action) {
        
        return internal.getInternal().canPerformAction(action);
    }
    
}
