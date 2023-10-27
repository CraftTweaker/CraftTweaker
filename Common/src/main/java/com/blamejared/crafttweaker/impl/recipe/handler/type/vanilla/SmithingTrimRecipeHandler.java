package com.blamejared.crafttweaker.impl.recipe.handler.type.vanilla;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker.impl.recipe.handler.helper.SmithingRecipeConflictChecker;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessSmithingTrimRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingTrimRecipe;

import java.util.List;
import java.util.Optional;

@IRecipeHandler.For(SmithingTrimRecipe.class)
public final class SmithingTrimRecipeHandler implements IRecipeHandler<SmithingTrimRecipe> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager<? super SmithingTrimRecipe> manager, final RegistryAccess registryAccess, final RecipeHolder<SmithingTrimRecipe> holder) {
        
        SmithingTrimRecipe recipe = holder.value();
        return String.format(
                "smithing.addTrimRecipe(%s, %s, %s, %s);",
                StringUtil.quoteAndEscape(holder.id()),
                IIngredient.fromIngredient(((AccessSmithingTrimRecipe) recipe).crafttweaker$getTemplate())
                        .getCommandString(),
                IIngredient.fromIngredient(((AccessSmithingTrimRecipe) recipe).crafttweaker$getBase())
                        .getCommandString(),
                IIngredient.fromIngredient(((AccessSmithingTrimRecipe) recipe).crafttweaker$getAddition())
                        .getCommandString()
        );
    }
    
    @Override
    public <U extends Recipe<?>> boolean doesConflict(final IRecipeManager<? super SmithingTrimRecipe> manager, final SmithingTrimRecipe firstRecipe, final U secondRecipe) {
        
        if(!(secondRecipe instanceof SmithingRecipe)) {
            return false;
        }
        return SmithingRecipeConflictChecker.doesConflict(manager, firstRecipe, GenericUtil.uncheck(secondRecipe));
    }
    
    @Override
    public Optional<IDecomposedRecipe> decompose(final IRecipeManager<? super SmithingTrimRecipe> manager, final RegistryAccess registryAccess,final SmithingTrimRecipe recipe) {
        
        final AccessSmithingTrimRecipe access = (AccessSmithingTrimRecipe) recipe;
        final IIngredient template = IIngredient.fromIngredient(access.crafttweaker$getTemplate());
        final IIngredient base = IIngredient.fromIngredient(access.crafttweaker$getBase());
        final IIngredient addition = IIngredient.fromIngredient(access.crafttweaker$getAddition());
        final IDecomposedRecipe decomposed = IDecomposedRecipe.builder()
                .with(BuiltinRecipeComponents.Input.INGREDIENTS, List.of(template, base, addition))
                .build();
        
        return Optional.of(decomposed);
    }
    
    @Override
    public Optional<SmithingTrimRecipe> recompose(final IRecipeManager<? super SmithingTrimRecipe> manager,final RegistryAccess registryAccess, final IDecomposedRecipe recipe) {
        
        final List<IIngredient> ingredients = recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS);
        
        if(ingredients.size() != 3) {
            throw new IllegalArgumentException("Invalid inputs: expected three ingredients for recipe, but got " + ingredients.size() + ": " + ingredients);
        }
        if(ingredients.stream().anyMatch(IIngredient::isEmpty)) {
            throw new IllegalArgumentException("Invalid inputs: empty ingredients");
        }
        final Ingredient template = ingredients.get(0).asVanillaIngredient();
        final Ingredient base = ingredients.get(1).asVanillaIngredient();
        final Ingredient addition = ingredients.get(2).asVanillaIngredient();
        return Optional.of(AccessSmithingTrimRecipe.crafttweaker$createSmithingTrimRecipe(template, base, addition));
    }
    
}
