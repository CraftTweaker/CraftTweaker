package com.blamejared.crafttweaker.impl.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class IngredientHelper {
    
    public static boolean canConflict(final Ingredient a, final Ingredient b) {
        
        return a == b || (a.getMatchingStacks().length == 0 && b.getMatchingStacks().length == 0) || !findIntersection(a, b).isEmpty();
    }
    
    public static List<ItemStack> findIntersection(final Ingredient a, final Ingredient b) {
        
        if (a == Ingredient.EMPTY || b == Ingredient.EMPTY) return Collections.emptyList();
    
        final ItemStack[] aStacks = a.getMatchingStacks();
        final ItemStack[] bStacks = b.getMatchingStacks();
    
        if (a == b) return Arrays.asList(aStacks);
        
        List<ItemStack> intersection = null;
        
        // Empiric testing shows that the naive double-for-loop is very efficient for small arrays rather than more
        // specialized data structures, which have more costs associated with them
        for (final ItemStack aStack : aStacks) {
            
            for (final ItemStack bStack : bStacks) {
                
                if (ItemStackHelper.areStacksTheSame(aStack, bStack)) {
                    
                    if (intersection == null) {
                        
                        intersection = new ArrayList<>();
                    }
                    
                    intersection.add(aStack);
                }
            }
        }
        
        return intersection == null? Collections.emptyList() : intersection;
    }
}
