package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeHooks;
import org.openzen.zencode.java.ZenCodeType;


/**
 * This is IIngredient!!!
 *
 * @docParam this <tag:ingotIron>
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.IIngredient")
@Document("vanilla/items/IIngredient")
@ZenWrapper(wrappedClass = "net.minecraft.item.crafting.Ingredient", conversionMethodFormat = "%s.asVanillaIngredient()", displayStringFormat = "%.getCommandString()")
public interface IIngredient {
    
    /**
     * Does the given stack match the ingredient?
     *
     * @param stack The stack to check
     * @docParam stack <item:minecraft:iron_ingot>
     */
    @ZenCodeType.Method
    boolean matches(IItemStack stack);
    
    /**
     * Create a Vanilla ingredient matching this one.
     */
    Ingredient asVanillaIngredient();
    
    /**
     * When this ingredient stack is crafted, what will remain in the grid?
     * Does not check if the stack matches though!
     * Used e.g. in CrT's {@link net.minecraft.item.crafting.ICraftingRecipe#getRemainingItems}
     *
     * @param stack The stack to provide for this ingredient.
     * @docParam stack <item:minecraft:iron_ingot>
     */
    @ZenCodeType.Method
    default IItemStack getRemainingItem(IItemStack stack) {
        return new MCItemStack(ForgeHooks.getContainerItem(stack.getInternal()));
    }
    
    /**
     * Returns the BEP to get this stack
     */
    @ZenCodeType.Getter("commandString")
    String getCommandString();
    
    
    @ZenCodeType.Getter("items")
    IItemStack[] getItems();
}
