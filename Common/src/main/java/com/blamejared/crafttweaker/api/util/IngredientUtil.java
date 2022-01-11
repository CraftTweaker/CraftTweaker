package com.blamejared.crafttweaker.api.util;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class IngredientUtil {
    
    public static boolean canConflict(final Ingredient a, final Ingredient b) {
        
        return a == b || (a.getItems().length == 0 && b.getItems().length == 0) || !findIntersection(a, b).isEmpty();
    }
    
    public static List<ItemStack> findIntersection(final Ingredient a, final Ingredient b) {
    
        if(a == Ingredient.EMPTY || b == Ingredient.EMPTY) {
            return Collections.emptyList();
        }
        
        final ItemStack[] aStacks = a.getItems();
        final ItemStack[] bStacks = b.getItems();
    
        if(a == b) {
            return Arrays.asList(aStacks);
        }
        
        List<ItemStack> intersection = null;
        
        // Empiric testing shows that the naive double-for-loop is very efficient for small arrays rather than more
        // specialized data structures, which have more costs associated with them
        for(final ItemStack aStack : aStacks) {
            
            for(final ItemStack bStack : bStacks) {
                
                if(ItemStackUtil.areStacksTheSame(aStack, bStack)) {
                    
                    if(intersection == null) {
                        
                        intersection = new ArrayList<>();
                    }
                    
                    intersection.add(aStack);
                }
            }
        }
        
        return intersection == null ? Collections.emptyList() : intersection;
    }
    
}
