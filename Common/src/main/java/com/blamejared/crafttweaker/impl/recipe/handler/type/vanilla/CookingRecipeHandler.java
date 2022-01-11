package com.blamejared.crafttweaker.impl.recipe.handler.type.vanilla;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@IRecipeHandler.For(BlastingRecipe.class)
@IRecipeHandler.For(CampfireCookingRecipe.class)
@IRecipeHandler.For(SmeltingRecipe.class)
@IRecipeHandler.For(SmokingRecipe.class)
public final class CookingRecipeHandler implements IRecipeHandler<AbstractCookingRecipe> {
    
    @FunctionalInterface
    private interface CookingRecipeFactory<T extends AbstractCookingRecipe> {
        
        T create(final ResourceLocation id, final String group, final Ingredient ingredient, final ItemStack result, final float experience, final int cookTime);
        
    }
    
    private static final Map<RecipeType<?>, Pair<String, CookingRecipeFactory<?>>> LOOKUP = ImmutableMap
            .<RecipeType<?>, Pair<String, CookingRecipeFactory<?>>> builder()
            .put(RecipeType.BLASTING, Pair.of("blastFurnace", BlastingRecipe::new))
            .put(RecipeType.CAMPFIRE_COOKING, Pair.of("campfire", CampfireCookingRecipe::new))
            .put(RecipeType.SMELTING, Pair.of("furnace", SmeltingRecipe::new))
            .put(RecipeType.SMOKING, Pair.of("smoker", SmokingRecipe::new))
            .build();
    
    @Override
    public String dumpToCommandString(final IRecipeManager manager, final AbstractCookingRecipe recipe) {
        
        return String.format(
                "%s.addRecipe(%s, %s, %s, %s, %s);",
                LOOKUP.get(recipe.getType()).getFirst(),
                StringUtils.quoteAndEscape(recipe.getId()),
                ItemStackUtil.getCommandString(recipe.getResultItem()),
                IIngredient.fromIngredient(recipe.getIngredients().get(0)).getCommandString(),
                recipe.getExperience(),
                recipe.getCookingTime()
        );
    }
    
    @Override
    public Optional<Function<ResourceLocation, AbstractCookingRecipe>> replaceIngredients(final IRecipeManager manager, final AbstractCookingRecipe recipe, final List<IReplacementRule> rules) {
        
        return IRecipeHandler.attemptReplacing(recipe.getIngredients().get(0), Ingredient.class, recipe, rules)
                .map(input -> id -> LOOKUP.get(recipe.getType())
                        .getSecond()
                        .create(id, recipe.getGroup(), input, recipe.getResultItem(), recipe.getExperience(), recipe.getCookingTime()));
    }
    
    @Override
    public <U extends Recipe<?>> boolean doesConflict(final IRecipeManager manager, final AbstractCookingRecipe firstRecipe, final U secondRecipe) {
        
        return IngredientUtil.canConflict(firstRecipe.getIngredients().get(0), secondRecipe.getIngredients().get(0));
    }
    
}
