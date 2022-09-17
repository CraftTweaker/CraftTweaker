package com.blamejared.crafttweaker.impl.recipe.handler.type.vanilla;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@IRecipeHandler.For(ShapelessRecipe.class)
public final class ShapelessRecipeHandler implements IRecipeHandler<ShapelessRecipe> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager<? super ShapelessRecipe> manager, final ShapelessRecipe recipe) {
        
        return String.format(
                "craftingTable.addShapeless(%s, %s, %s);",
                StringUtil.quoteAndEscape(recipe.getId()),
                ItemStackUtil.getCommandString(recipe.getResultItem()),
                recipe.getIngredients().stream()
                        .map(IIngredient::fromIngredient)
                        .map(IIngredient::getCommandString)
                        .collect(Collectors.joining(", ", "[", "]"))
        );
    }
    
    @Override
    public <U extends Recipe<?>> boolean doesConflict(final IRecipeManager<? super ShapelessRecipe> manager, final ShapelessRecipe firstRecipe, final U secondRecipe) {
        
        return Services.PLATFORM.doCraftingTableRecipesConflict(manager, firstRecipe, secondRecipe);
    }
    
    @Override
    public Optional<IDecomposedRecipe> decompose(final IRecipeManager<? super ShapelessRecipe> manager, final ShapelessRecipe recipe) {
        
        final List<IIngredient> ingredients = recipe.getIngredients().stream()
                .map(IIngredient::fromIngredient)
                .toList();
        final IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
                .with(BuiltinRecipeComponents.Metadata.GROUP, recipe.getGroup())
                .with(BuiltinRecipeComponents.Input.INGREDIENTS, ingredients)
                .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.of(recipe.getResultItem()))
                .build();
        return Optional.of(decomposedRecipe);
    }
    
    @Override
    public Optional<ShapelessRecipe> recompose(final IRecipeManager<? super ShapelessRecipe> manager, final ResourceLocation name, final IDecomposedRecipe recipe) {
        
        final String group = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.GROUP);
        final List<IIngredient> ingredients = recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS);
        final IItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS);
        
        if(ingredients.stream().anyMatch(IIngredient::isEmpty)) {
            throw new IllegalArgumentException("Invalid inputs: found empty ingredient in list " + ingredients);
        }
        if(output.isEmpty()) {
            throw new IllegalArgumentException("Invalid output: empty item");
        }
        
        final NonNullList<Ingredient> recipeIngredients = ingredients.stream()
                .map(IIngredient::asVanillaIngredient)
                .collect(NonNullList::create, NonNullList::add, NonNullList::addAll);
        return Optional.of(new ShapelessRecipe(name, group, output.getInternal(), recipeIngredients));
    }
    
}
