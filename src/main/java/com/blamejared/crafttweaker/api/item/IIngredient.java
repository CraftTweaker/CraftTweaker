package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeHooks;
import org.openzen.zencode.java.ZenCodeType;


@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.IIngredient")
public interface IIngredient {
    
    /**
     * Does the given stack match the ingredient?
     */
    @ZenCodeType.Method
    boolean matches(IItemStack stack);
    
    /**
     * Create a Vanilla ingredient matching this one.
     */
    Ingredient asVanillaIngredient();
    
    /**
     * When this ingredient stack is crafted, what will remain in the grid?
     * Used e.g. in CrT's {@link net.minecraft.item.crafting.ICraftingRecipe#getRemainingItems}
     */
    default IItemStack getRemainingItem(IItemStack stack) {
        return new MCItemStack(ForgeHooks.getContainerItem(stack.getInternal()));
    }
    
    /**
     * Returns the BEP to get this stack
     */
    @ZenCodeType.Getter("commandString")
    String getCommandString();
}
