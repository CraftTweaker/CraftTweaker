package com.blamejared.crafttweaker.api.recipe.handler.helper;

import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandlerRegistry;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.common.crafting.IShapedRecipe;

import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

public final class CraftingTableRecipeConflictChecker {
    
    public static boolean checkConflicts(final IRecipeManager<?> manager, final Recipe<?> first, final Recipe<?> second) {
        
        // Special recipes cannot conflict by definition
        if(first.isSpecial() || second.isSpecial()) {
            return false;
        }
        
        // (Shaped, Shapeless) must always be preferred to (Shapeless, Shaped) in this checker.
        if(!(first instanceof IShapedRecipe<?>) && second instanceof IShapedRecipe<?>) {
            
            return redirect(manager, second, first);
        }
        
        return checkConflictsMaybeDifferent(first, second);
    }
    
    private static <T extends Recipe<?>> boolean redirect(final IRecipeManager<?> manager, final T second, final Recipe<?> first) {
        
        // We need another lookup because of the wildcard capture
        return IRecipeHandlerRegistry.getHandlerFor(second).doesConflict(GenericUtil.uncheck(manager), second, first);
    }
    
    private static boolean checkConflictsMaybeDifferent(final Recipe<?> first, final Recipe<?> second) {
        
        if(first instanceof IShapedRecipe<?>) {
            
            if(second instanceof IShapedRecipe<?>) {
                
                return doShapedShapedConflict((IShapedRecipe<?>) first, (IShapedRecipe<?>) second);
            }
            
            return doShapedShapelessConflict((IShapedRecipe<?>) first, second);
        }
        
        return doShapelessShapelessConflict(first, second);
    }
    
    private static boolean doShapedShapedConflict(final IShapedRecipe<?> first, final IShapedRecipe<?> second) {
        
        if(first.getRecipeHeight() != second.getRecipeHeight()) {
            return false;
        }
        if(first.getRecipeWidth() != second.getRecipeWidth()) {
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
    
    private static boolean doShapedShapelessConflict(final IShapedRecipe<?> first, final Recipe<?> second) {
        
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
        
        return craftShapelessRecipeVirtually(first, second);
    }
    
    private static boolean craftShapelessRecipeVirtually(final List<Ingredient> first, final List<Ingredient> second) {
        
        final BitSet visitData = new BitSet(second.size());
        
        for(final Ingredient target : first) {
            
            for(int j = 0; j < second.size(); ++j) {
                
                if(visitData.get(j)) {
                    continue;
                }
                
                final Ingredient attempt = second.get(j);
                
                if(IngredientUtil.canConflict(target, attempt)) {
                    
                    visitData.set(j);
                    break;
                }
            }
        }
        
        // Since all ingredients must have been used, visitData must have been set fully to 1
        return visitData.nextClearBit(0) == second.size();
    }
    
}
