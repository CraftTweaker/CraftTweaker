package com.blamejared.crafttweaker.api.recipe.handler.helper;

import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandlerRegistry;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.*;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.*;

import java.util.List;
import java.util.stream.Collectors;

public final class CraftingTableRecipeConflictChecker {
    
    public static boolean checkConflicts(final IRecipeManager<?> manager, final Recipe<?> first, final Recipe<?> second) {
        
        // Special recipes cannot conflict by definition
        if(first.isSpecial() || second.isSpecial()) {
            return false;
        }
        
        // (Shaped, Shapeless) must always be preferred to (Shapeless, Shaped) in this checker.
        if(!(first instanceof ShapedRecipe) && (second instanceof ShapedRecipe)) {
            
            return redirect(manager, second, first);
        }
        
        return checkConflictsMaybeDifferent(first, second);
    }
    
    private static <T extends Recipe<?>> boolean redirect(final IRecipeManager<?> manager, final T second, final Recipe<?> first) {
        
        // We need another lookup because of the wildcard capture
        return IRecipeHandlerRegistry.getHandlerFor(second).doesConflict(GenericUtil.uncheck(manager), second, first);
    }
    
    private static boolean checkConflictsMaybeDifferent(final Recipe<?> first, final Recipe<?> second) {
        
        if(first instanceof ShapedRecipe shapedFirst) {
            
            if(second instanceof ShapedRecipe shapedSecond) {
                
                return doShapedShapedConflict(shapedFirst, shapedSecond);
            }
            
            return doShapedShapelessConflict(shapedFirst, second);
        }
        
        return doShapelessShapelessConflict(first, second);
    }
    
    private static boolean doShapedShapedConflict(final ShapedRecipe first, final ShapedRecipe second) {
        
        if(first.getHeight() != second.getHeight()) {
            return false;
        }
        if(first.getWidth() != second.getWidth()) {
            return false;
        }
        
        final NonNullList<Ingredient> firstIngredients = first.getIngredients();
        final NonNullList<Ingredient> secondIngredients = second.getIngredients();
        
        for(int i = 0; i < firstIngredients.size(); ++i) {
            
            final Ingredient firstIngredient = firstIngredients.get(i);
            final Ingredient secondIngredient = secondIngredients.get(i);
            
            if(!IngredientUtil.canConflict(firstIngredient, secondIngredient)) {
                return false;
            }
        }
        
        return true;
    }
    
    private static boolean doShapedShapelessConflict(final ShapedRecipe first, final Recipe<?> second) {
        
        return doShapelessShapelessConflict(first.getIngredients()
                .stream()
                .filter(it -> it != Ingredient.EMPTY)
                .collect(Collectors.toList()), second.getIngredients());
    }
    
    private static boolean doShapelessShapelessConflict(final Recipe<?> first, final Recipe<?> second) {
        
        return doShapelessShapelessConflict(first.getIngredients(), second.getIngredients());
    }
    
    private static boolean doShapelessShapelessConflict(final List<Ingredient> first, final List<Ingredient> second) {
        
        if(first.size() != second.size()) {
            return false;
        }
        
        return IngredientUtil.doIngredientsConflict(first, second);
    }
    
}
