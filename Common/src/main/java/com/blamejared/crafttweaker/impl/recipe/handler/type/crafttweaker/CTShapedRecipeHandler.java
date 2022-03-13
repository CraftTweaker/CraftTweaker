package com.blamejared.crafttweaker.impl.recipe.handler.type.crafttweaker;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.handler.helper.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipeBase;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@IRecipeHandler.For(CTShapedRecipeBase.class)
public final class CTShapedRecipeHandler implements IRecipeHandler<CTShapedRecipeBase> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager manager, final CTShapedRecipeBase recipe) {
        
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
    public Optional<Function<ResourceLocation, CTShapedRecipeBase>> replaceIngredients(final IRecipeManager manager, final CTShapedRecipeBase recipe, final List<IReplacementRule> rules) {
        
        return ReplacementHandlerHelper.replaceIngredientArray(
                this.flatten(recipe.getCtIngredients(), recipe.getRecipeWidth(), recipe.getRecipeHeight()),
                IIngredient.class,
                recipe,
                rules,
                newIngredients -> id -> Services.REGISTRY.createCTShapedRecipe(
                        id.getPath(),
                        recipe.getCtOutput(),
                        this.inflate(newIngredients, recipe.getRecipeWidth(), recipe.getRecipeHeight()),
                        recipe.getMirrorAxis(),
                        recipe.getFunction()
                )
        );
    }
    
    @Override
    public <U extends Recipe<?>> boolean doesConflict(final IRecipeManager manager, final CTShapedRecipeBase firstRecipe, final U secondRecipe) {
        
        return Services.PLATFORM.doCraftingTableRecipesConflict(manager, firstRecipe, secondRecipe);
    }
    
    private IIngredient[] flatten(final IIngredient[][] ingredients, final int width, final int height) {
        
        final IIngredient[] flattened = new IIngredient[width * height];
        for(int i = 0; i < flattened.length; ++i) {
            flattened[i] = ingredients[i / width][i % width];
        }
        return flattened;
    }
    
    private IIngredient[][] inflate(final IIngredient[] flattened, final int width, final int height) {
        
        final IIngredient[][] inflated = new IIngredient[width][height];
        for(int i = 0; i < flattened.length; ++i) {
            inflated[i / width][i % width] = flattened[i];
        }
        return inflated;
    }
    
}
