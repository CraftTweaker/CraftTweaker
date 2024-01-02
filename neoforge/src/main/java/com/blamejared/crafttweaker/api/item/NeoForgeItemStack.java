package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.item.ActionSetBurnTime;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.ToolAction;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.NeoForgeItemStack")
@Document("neoforge/api/item/NeoForgeItemStack")
public interface NeoForgeItemStack extends IItemStack {
    
    
    /**
     * Sets the burn time of this ingredient, for use in the furnace and other machines
     *
     * @param time the new burn time
     *
     * @docParam time 500
     */
    @ZenCodeType.Method
    default void setBurnTime(int time, IRecipeManager manager) {
        
        CraftTweakerAPI.apply(new ActionSetBurnTime(this, time, manager.getRecipeType()));
    }
    
    @Override
    default int getBurnTime() {
        
        return CommonHooks.getBurnTime(getInternal(), RecipeType.SMELTING);
    }
    
    @ZenCodeType.Method
    default int getBurnTime(IRecipeManager manager) {
        
        return CommonHooks.getBurnTime(getInternal(), manager.getRecipeType());
    }
    
    
    @Override
    default IItemStack getRemainingItem(IItemStack stack) {
        
        return IItemStack.of(stack.getInternal().getCraftingRemainingItem());
    }
    
}
