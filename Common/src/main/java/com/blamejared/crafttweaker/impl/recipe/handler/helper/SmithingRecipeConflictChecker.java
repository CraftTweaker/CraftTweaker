package com.blamejared.crafttweaker.impl.recipe.handler.helper;

import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandlerRegistry;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessSmithingTransformRecipe;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessSmithingTrimRecipe;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.Nullable;

public final class SmithingRecipeConflictChecker {
    
    
    public static boolean doesConflict(final IRecipeManager<?> manager, final RecipeHolder<? extends SmithingRecipe> first, final RecipeHolder<? extends SmithingRecipe> second) {
        
        BasicRecipe firstRecipe = BasicRecipe.from(first.value());
        BasicRecipe secondRecipe = BasicRecipe.from(second.value());
        
        if(firstRecipe == null || secondRecipe == null) {
            return redirect(GenericUtil.uncheck(manager), second, first);
        }
        
        return IngredientUtil.canConflict(firstRecipe.template(), secondRecipe.template())
                && IngredientUtil.canConflict(firstRecipe.base(), secondRecipe.base())
                && IngredientUtil.canConflict(firstRecipe.addition(), secondRecipe.addition());
    }
    
    private static <T extends Recipe<?>> boolean redirect(final IRecipeManager<?> manager, final RecipeHolder<T> second, final RecipeHolder<?> first) {
        
        return IRecipeHandlerRegistry.getHandlerFor(second)
                .doesConflict(GenericUtil.uncheck(manager), second, first);
    }
    
    record BasicRecipe(Ingredient template, Ingredient base, Ingredient addition) {
        
        @Nullable
        static BasicRecipe from(SmithingRecipe recipe) {
            
            if(recipe instanceof SmithingTrimRecipe trim) {
                return from(trim);
            } else if(recipe instanceof SmithingTransformRecipe transform) {
                return from(transform);
            }
            return null;
        }
        
        static BasicRecipe from(SmithingTrimRecipe recipe) {
            
            AccessSmithingTrimRecipe access = (AccessSmithingTrimRecipe) recipe;
            return new BasicRecipe(access.crafttweaker$getTemplate(), access.crafttweaker$getBase(), access.crafttweaker$getAddition());
        }
        
        static BasicRecipe from(SmithingTransformRecipe recipe) {
            
            AccessSmithingTransformRecipe access = (AccessSmithingTransformRecipe) recipe;
            return new BasicRecipe(access.crafttweaker$getTemplate(), access.crafttweaker$getBase(), access.crafttweaker$getAddition());
        }
        
    }
    
}
