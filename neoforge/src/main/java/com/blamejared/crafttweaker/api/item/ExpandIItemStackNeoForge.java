package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.item.ActionSetBurnTime;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.common.ItemAbility;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.item.IItemStack")
@Document("neoforge/api/item/NeoForgeIItemStackExpansions")
public class ExpandIItemStackNeoForge {
    
    /**
     * Sets the burn time of this ingredient, for use in the furnace and other machines
     *
     * @param time the new burn time
     *
     * @docParam time 500
     */
    @ZenCodeType.Method
    public static void setBurnTime(IItemStack internal, int time, IRecipeManager<?> manager) {
        
        CraftTweakerAPI.apply(new ActionSetBurnTime(internal, time, manager.getRecipeType()));
    }
    
    @ZenCodeType.Method
    public static int getBurnTime(IItemStack internal, IRecipeManager<?> manager) {
        
        return internal.getInternal().getBurnTime(manager.getRecipeType());
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static <T, C> T getCapabilityWithContext(IItemStack internal, Class<T> tClass, Class<C> cClass, ItemCapability<T, C> cap, @ZenCodeType.Nullable C context) {
        
        return internal.getInternal().getCapability(cap, context);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static <T> T getCapability(IItemStack internal, Class<T> tClass, ItemCapability<T, Void> cap) {
        
        return internal.getInternal().getCapability(cap);
    }
    
    /**
     * Checks if this item can perform the given {@link ItemAbility}.
     *
     * @param action The action to perform.
     *
     * @return True if it can perform the action, false otherwise.
     */
    @ZenCodeType.Method
    public static boolean canPerformAction(IItemStack internal, ItemAbility action) {
        
        return internal.getInternal().canPerformAction(action);
    }
    
}
