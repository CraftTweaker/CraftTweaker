package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.item.ActionSetBurnTime;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolAction;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.ForgeItemStack")
@Document("forge/api/item/ForgeItemStack")
public interface ForgeItemStack extends IItemStack {
    
    
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
        
        return ForgeHooks.getBurnTime(getInternal(), RecipeType.SMELTING);
    }
    
    @ZenCodeType.Method
    default int getBurnTime(IRecipeManager manager) {
        
        return ForgeHooks.getBurnTime(getInternal(), manager.getRecipeType());
    }
    
    
    @Override
    default IItemStack getRemainingItem(IItemStack stack) {
        
        return IItemStack.of(stack.getInternal().getCraftingRemainingItem());
    }
    
}
