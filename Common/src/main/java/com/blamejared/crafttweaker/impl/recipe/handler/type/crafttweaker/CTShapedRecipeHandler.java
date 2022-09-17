package com.blamejared.crafttweaker.impl.recipe.handler.type.crafttweaker;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.function.RecipeFunctionMatrix;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipeBase;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker.platform.Services;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@IRecipeHandler.For(CTShapedRecipeBase.class)
public final class CTShapedRecipeHandler implements IRecipeHandler<CTShapedRecipeBase> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager<? super CTShapedRecipeBase> manager, final CTShapedRecipeBase recipe) {
        
        return String.format(
                "craftingTable.addShaped(%s, %s, %s%s);",
                StringUtil.quoteAndEscape(recipe.getId()),
                recipe.getCtOutput().getCommandString(),
                Arrays.stream(recipe.getCtIngredients())
                        .map(row -> Arrays.stream(row)
                                .map(IIngredient::getCommandString)
                                .collect(Collectors.joining(", ", "[", "]")))
                        .collect(Collectors.joining(", ", "[", "]")),
                recipe.getFunction() == null ? "" : ", (usualOut, inputs) => { ... }"
        );
    }
    
    @Override
    public <U extends Recipe<?>> boolean doesConflict(final IRecipeManager<? super CTShapedRecipeBase> manager, final CTShapedRecipeBase firstRecipe, final U secondRecipe) {
        
        return Services.PLATFORM.doCraftingTableRecipesConflict(manager, firstRecipe, secondRecipe);
    }
    
    @Override
    public Optional<IDecomposedRecipe> decompose(final IRecipeManager<? super CTShapedRecipeBase> manager, final CTShapedRecipeBase recipe) {
        
        final int width = recipe.getRecipeWidth();
        final int height = recipe.getRecipeHeight();
        final RecipeFunctionMatrix function = recipe.getFunction();
        final List<IIngredient> ingredients = this.flatten(recipe.getCtIngredients(), width, height);
        
        @SuppressWarnings("SuspiciousNameCombination") final IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
                .with(BuiltinRecipeComponents.Metadata.SHAPE_SIZE_2D, IntIntPair.of(width, height))
                .with(BuiltinRecipeComponents.Metadata.MIRROR_AXIS, recipe.getMirrorAxis())
                .with(BuiltinRecipeComponents.Input.INGREDIENTS, ingredients)
                .with(BuiltinRecipeComponents.Output.ITEMS, recipe.getCtOutput())
                .build();
        
        if(function != null) {
            decomposedRecipe.set(BuiltinRecipeComponents.Processing.FUNCTION_2D, function::process);
        }
        
        return Optional.of(decomposedRecipe);
    }
    
    @Override
    public Optional<CTShapedRecipeBase> recompose(final IRecipeManager<? super CTShapedRecipeBase> manager, final ResourceLocation name, final IDecomposedRecipe recipe) {
        
        final IntIntPair size = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.SHAPE_SIZE_2D);
        final MirrorAxis axis = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.MIRROR_AXIS);
        final List<IIngredient> ingredients = recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS);
        final var function = recipe.get(BuiltinRecipeComponents.Processing.FUNCTION_2D);
        final IItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS);
        
        final int width = size.firstInt();
        final int height = size.secondInt();
        
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
        final RecipeFunctionMatrix recipeFunction = function == null ? null : function.get(0)::apply;
        return Optional.of(Services.REGISTRY.createCTShapedRecipe(name.getPath(), output, matrix, axis, recipeFunction));
    }
    
    private List<IIngredient> flatten(final IIngredient[][] ingredients, final int width, final int height) {
        
        final int size;
        final List<IIngredient> flattened = new ArrayList<>(size = width * height);
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
