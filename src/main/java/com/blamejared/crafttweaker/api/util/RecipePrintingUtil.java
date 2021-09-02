package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStackMutable;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helpers functions for recipe printing.
 */
public class RecipePrintingUtil {
    
    /**
     * Creates a CraftTweaker String representation of the given {@link Ingredient} List.
     *
     * @param ingredients The List of {@link Ingredient} to stringify.
     * @param delimeter   The string delimeter to use between the values. (` | ` for "OR'd" Ingredients, `, ` for an array).
     *
     * @return A String representing the given ingredients.
     */
    public static String stringifyIngredients(List<Ingredient> ingredients, String delimeter) {
        
        return ingredients.stream()
                .map(IIngredient::fromIngredient)
                .map(CommandStringDisplayable::getCommandString)
                .collect(Collectors.joining(delimeter));
    }
    
    /**
     * Creates a CraftTweaker String representation of the given {@link ItemStack} List.
     *
     * @param ingredients The List of {@link ItemStack} to stringify.
     * @param delimeter   The string delimeter to use between the values. (` | ` for "OR'd" Ingredients, `, ` for an array).
     *
     * @return A String representing the given ingredients.
     */
    public static String stringifyStacks(List<ItemStack> ingredients, String delimeter) {
        
        return ingredients.stream()
                .map(MCItemStackMutable::new)
                .map(CommandStringDisplayable::getCommandString)
                .collect(Collectors.joining(delimeter));
    }
    
    /**
     * Creates a CraftTweaker String representation of the given {@link ItemStack} List.
     *
     * @param ingredients The List of {@link ItemStack} to stringify.
     * @param delimeter   The string delimeter to use between the values. (` | ` for "OR'd" Ingredients, `, ` for an array).
     *
     * @return A String representing the given ingredients.
     */
    public static String stringifyWeightedStacks(List<ItemStack> ingredients, List<Float> weights, String delimeter) {
        
        Iterator<Float> iterator = weights.iterator();
        return ingredients.stream()
                .filter(val -> iterator.hasNext())
                .map(itemStack -> new MCWeightedItemStack(new MCItemStackMutable(itemStack), iterator.next()))
                .map(CommandStringDisplayable::getCommandString)
                .collect(Collectors.joining(delimeter));
    }
    
    /**
     * Creates a CraftTweaker String representation of the given {@link ItemStack} List.
     *
     * @param ingredients The List of {@link ItemStack} to stringify.
     * @param delimeter   The string delimeter to use between the values. (` | ` for "OR'd" Ingredients, `, ` for an array).
     *
     * @return A String representing the given ingredients.
     */
    public static String stringifyFluidStacks(List<FluidStack> ingredients, String delimeter) {
        
        return ingredients.stream()
                .map(MCFluidStackMutable::new)
                .map(CommandStringDisplayable::getCommandString)
                .collect(Collectors.joining(delimeter));
    }
    
}
