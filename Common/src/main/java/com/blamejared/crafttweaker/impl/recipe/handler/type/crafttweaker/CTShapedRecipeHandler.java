package com.blamejared.crafttweaker.impl.recipe.handler.type.crafttweaker;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction2D;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipe;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@IRecipeHandler.For(CTShapedRecipe.class)
public final class CTShapedRecipeHandler implements IRecipeHandler<CTShapedRecipe> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager<? super CTShapedRecipe> manager, final RegistryAccess registryAccess, final RecipeHolder<CTShapedRecipe> holder) {
        
        return String.format(
                "craftingTable.addShaped(%s, %s, %s%s);",
                StringUtil.quoteAndEscape(holder.id()),
                holder.value().getCtOutput().getCommandString(),
                Arrays.stream(holder.value().getCtIngredients())
                        .map(row -> Arrays.stream(row)
                                .map(IIngredient::getCommandString)
                                .collect(Collectors.joining(", ", "[", "]")))
                        .collect(Collectors.joining(", ", "[", "]")),
                holder.value().getFunction() == null ? "" : ", (usualOut, inputs) => { ... }"
        );
    }
    
    @Override
    public <U extends Recipe<?>> boolean doesConflict(final IRecipeManager<? super CTShapedRecipe> manager, final RecipeHolder<CTShapedRecipe> firstHolder, final RecipeHolder<U> secondHolder) {
        
        return Services.PLATFORM.doCraftingTableRecipesConflict(manager, firstHolder, secondHolder);
    }
    
    @Override
    public Optional<IDecomposedRecipe> decompose(final IRecipeManager<? super CTShapedRecipe> manager, final RegistryAccess registryAccess, final RecipeHolder<CTShapedRecipe> holder) {
        
        CTShapedRecipe recipe = holder.value();
        final int width = recipe.getWidth();
        final int height = recipe.getHeight();
        final RecipeFunction2D function = recipe.getFunction();
        final List<IIngredient> ingredients = this.flatten(recipe.getCtIngredients(), width, height);
        
        final IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
                .with(BuiltinRecipeComponents.Metadata.SHAPE_SIZE_2D, Pair.of(width, height))
                .with(BuiltinRecipeComponents.Metadata.MIRROR_AXIS, recipe.getMirrorAxis())
                .with(BuiltinRecipeComponents.Input.INGREDIENTS, ingredients)
                .with(BuiltinRecipeComponents.Output.ITEMS, recipe.getCtOutput())
                .build();
        
        if(function != null) {
            decomposedRecipe.set(BuiltinRecipeComponents.Processing.FUNCTION_2D, function);
        }
        
        return Optional.of(decomposedRecipe);
    }
    
    @Override
    public Optional<RecipeHolder<CTShapedRecipe>> recompose(final IRecipeManager<? super CTShapedRecipe> manager, final RegistryAccess registryAccess, final ResourceLocation name, final IDecomposedRecipe recipe) {
        
        final Pair<Integer, Integer> size = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.SHAPE_SIZE_2D);
        final MirrorAxis axis = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.MIRROR_AXIS);
        final List<IIngredient> ingredients = recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS);
        final List<RecipeFunction2D> function = recipe.get(BuiltinRecipeComponents.Processing.FUNCTION_2D);
        final IItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS);
        
        final int width = size.getFirst();
        final int height = size.getSecond();
        
        if(width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid shape size: bounds must be positive but got " + size);
        }
        if(width * height != ingredients.size()) {
            throw new IllegalArgumentException("Invalid shape size: incompatible with ingredients, got " + size + " with " + ingredients.size());
        }
        if(output.isEmpty()) {
            throw new IllegalArgumentException("Invalid output: empty item");
        }
        
        final IIngredient[][] matrix = this.inflate(ingredients, width, height);
        final RecipeFunction2D recipeFunction = function == null ? null : function.get(0);
        return Optional.of(new RecipeHolder<>(name, new CTShapedRecipe(output, matrix, axis, recipeFunction)));
    }
    
    private List<IIngredient> flatten(final IIngredient[][] ingredients, final int width, final int height) {
        
        final int size;
        final List<IIngredient> flattened = Arrays.asList(new IIngredient[size = width * height]);
        for(int i = 0; i < size; ++i) {
            flattened.set(i, ingredients[i / width][i % width]);
        }
        return flattened;
    }
    
    private IIngredient[][] inflate(final List<IIngredient> flattened, final int width, final int height) {
        
        final IIngredient[][] inflated = new IIngredient[width][height];
        for(int i = 0, s = flattened.size(); i < s; ++i) {
            inflated[i / width][i % width] = flattened.get(i);
        }
        return inflated;
    }
    
}
