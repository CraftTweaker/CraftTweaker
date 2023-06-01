package com.blamejared.crafttweaker.impl.recipe.handler.type.crafttweaker;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.*;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction1D;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.type.CTShapelessRecipe;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.*;
import java.util.stream.Collectors;

@IRecipeHandler.For(CTShapelessRecipe.class)
public final class CTShapelessRecipeHandler implements IRecipeHandler<CTShapelessRecipe> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager<? super CTShapelessRecipe> manager, final CTShapelessRecipe recipe) {
        
        return String.format(
                "craftingTable.addShapeless(%s, %s, %s%s);",
                StringUtil.quoteAndEscape(recipe.getId()),
                recipe.getCtOutput().getCommandString(),
                Arrays.stream(recipe.getCtIngredients())
                        .map(IIngredient::getCommandString)
                        .collect(Collectors.joining(", ", "[", "]")),
                recipe.getFunction() == null ? "" : ", (usualOut, inputs) => { ... }"
        );
    }
    
    @Override
    public <U extends Recipe<?>> boolean doesConflict(final IRecipeManager<? super CTShapelessRecipe> manager, final CTShapelessRecipe firstRecipe, final U secondRecipe) {
        
        return Services.PLATFORM.doCraftingTableRecipesConflict(manager, firstRecipe, secondRecipe);
    }
    
    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super CTShapelessRecipe> manager, CTShapelessRecipe recipe) {
        
        final RecipeFunction1D function = recipe.getFunction();
        final List<IIngredient> ingredients = Arrays.asList(recipe.getCtIngredients());
        
        final IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
                .with(BuiltinRecipeComponents.Input.INGREDIENTS, ingredients)
                .with(BuiltinRecipeComponents.Output.ITEMS, recipe.getCtOutput())
                .build();
        
        if(function != null) {
            decomposedRecipe.set(BuiltinRecipeComponents.Processing.FUNCTION_1D, function);
        }
        
        return Optional.of(decomposedRecipe);
    }
    
    @Override
    public Optional<CTShapelessRecipe> recompose(IRecipeManager<? super CTShapelessRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        
        final List<IIngredient> ingredients = recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS);
        final List<RecipeFunction1D> function = recipe.get(BuiltinRecipeComponents.Processing.FUNCTION_1D);
        final IItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS);
        
        if(ingredients.stream().anyMatch(IIngredient::isEmpty)) {
            throw new IllegalArgumentException("Invalid inputs: found empty ingredient in list " + ingredients);
        }
        if(output.isEmpty()) {
            throw new IllegalArgumentException("Invalid output: empty item");
        }
        
        final IIngredient[] list = ingredients.toArray(IIngredient[]::new);
        final RecipeFunction1D recipeFunction = function == null ? null : function.get(0);
        return Optional.of(new CTShapelessRecipe(name.getPath(), output, list, recipeFunction));
    }
    
}
