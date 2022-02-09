package com.blamejared.crafttweaker.api.recipe.handler.helper;

import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandlerRegistry;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipeBase;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;

import java.util.BitSet;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class CraftingTableRecipeConflictChecker {
    
    public static boolean checkConflicts(final IRecipeManager manager, final Recipe<?> first, final Recipe<?> second) {
        
        // Special recipes cannot conflict by definition
        if(first.isSpecial() || second.isSpecial()) {
            return false;
        }
        
        // (Shaped, Shapeless) must always be preferred to (Shapeless, Shaped) in this checker.
        if(!(first instanceof ShapedRecipe || first instanceof CTShapedRecipeBase) && (second instanceof ShapedRecipe || second instanceof CTShapedRecipeBase)) {
            
            return redirect(manager, second, first);
        }
        
        return checkConflictsMaybeDifferent(first, second);
    }
    
    private static <T extends Recipe<?>> boolean redirect(final IRecipeManager manager, final T second, final Recipe<?> first) {
        
        // We need another lookup because of the wildcard capture
        return IRecipeHandlerRegistry.getHandlerFor(second).doesConflict(manager, second, first);
    }
    
    private static boolean checkConflictsMaybeDifferent(final Recipe<?> first, final Recipe<?> second) {
        
        if(first instanceof ShapedRecipe || first instanceof CTShapedRecipeBase) {
            
            int firstWidth = first instanceof ShapedRecipe ? ((ShapedRecipe) first).getWidth() : ((CTShapedRecipeBase) first).getRecipeWidth();
            int firstHeight = first instanceof ShapedRecipe ? ((ShapedRecipe) first).getHeight() : ((CTShapedRecipeBase) first).getRecipeHeight();
            ShapedRecipeDelegate firstDelegate = new ShapedRecipeDelegate(first, () -> Pair.of(firstWidth, firstHeight));
            
            if(second instanceof ShapedRecipe || second instanceof CTShapedRecipeBase) {
                
                int secondWidth = second instanceof ShapedRecipe ? ((ShapedRecipe) second).getWidth() : ((CTShapedRecipeBase) second).getRecipeWidth();
                int secondHeight = second instanceof ShapedRecipe ? ((ShapedRecipe) second).getHeight() : ((CTShapedRecipeBase) second).getRecipeHeight();
                ShapedRecipeDelegate secondDelegate = new ShapedRecipeDelegate(second, () -> Pair.of(secondWidth, secondHeight));
                
                return doShapedShapedConflict(firstDelegate, secondDelegate);
            }
            
            return doShapedShapelessConflict(firstDelegate, second);
        }
        
        return doShapelessShapelessConflict(first, second);
    }
    
    private static boolean doShapedShapedConflict(final ShapedRecipeDelegate first, final ShapedRecipeDelegate second) {
        
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
    
    private static boolean doShapedShapelessConflict(final ShapedRecipeDelegate first, final Recipe<?> second) {
        
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
    
    private record ShapedRecipeDelegate(Recipe<?> recipe,
                                        Supplier<Pair<Integer, Integer>> sizeGetter) {
        
        public NonNullList<Ingredient> getIngredients() {
            
            return recipe.getIngredients();
        }
        
        public int getWidth() {
            
            return sizeGetter.get().getFirst();
        }
        
        
        public int getHeight() {
            
            return sizeGetter.get().getSecond();
        }
        
    }
    
}
