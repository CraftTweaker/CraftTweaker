package com.blamejared.crafttweaker.impl.recipe.handler.type.crafttweaker;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.handler.helper.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.type.CTShapelessRecipeBase;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@IRecipeHandler.For(CTShapelessRecipeBase.class)
public final class CTShapelessRecipeHandler implements IRecipeHandler<CTShapelessRecipeBase> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager manager, final CTShapelessRecipeBase recipe) {
        
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
    public Optional<Function<ResourceLocation, CTShapelessRecipeBase>> replaceIngredients(final IRecipeManager manager, final CTShapelessRecipeBase recipe, final List<IReplacementRule> rules) {
        
        return ReplacementHandlerHelper.replaceIngredientArray(
                recipe.getCtIngredients(),
                IIngredient.class,
                recipe,
                rules,
                newIngredients -> id -> Services.REGISTRY.createCTShapelessRecipe(id.getPath(), recipe.getCtOutput(), newIngredients, recipe.getFunction())
        );
    }
    
    @Override
    public <U extends Recipe<?>> boolean doesConflict(final IRecipeManager manager, final CTShapelessRecipeBase firstRecipe, final U secondRecipe) {
        
        return Services.PLATFORM.doCraftingTableRecipesConflict(manager, firstRecipe, secondRecipe);
    }
    
}
