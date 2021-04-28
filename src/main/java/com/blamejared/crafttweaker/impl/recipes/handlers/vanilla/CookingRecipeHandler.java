package com.blamejared.crafttweaker.impl.recipes.handlers.vanilla;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.ReplacementRule;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmokingRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@IRecipeHandler.For(BlastingRecipe.class)
@IRecipeHandler.For(CampfireCookingRecipe.class)
@IRecipeHandler.For(FurnaceRecipe.class)
@IRecipeHandler.For(SmokingRecipe.class)
public final class CookingRecipeHandler implements IRecipeHandler<AbstractCookingRecipe> {
    @FunctionalInterface
    private interface CookingRecipeFactory<T extends AbstractCookingRecipe> {
        T create(final ResourceLocation id, final String group, final Ingredient ingredient, final ItemStack result, final float experience, final int cookTime);
    }
    
    private static final Map<IRecipeType<?>, Pair<String, CookingRecipeFactory<?>>> LOOKUP = ImmutableMap
            .<IRecipeType<?>, Pair<String, CookingRecipeFactory<?>>>builder()
            .put(IRecipeType.BLASTING, Pair.of("blastFurnace", BlastingRecipe::new))
            .put(IRecipeType.CAMPFIRE_COOKING, Pair.of("campfire", CampfireCookingRecipe::new))
            .put(IRecipeType.SMELTING, Pair.of("furnace", FurnaceRecipe::new))
            .put(IRecipeType.SMOKING, Pair.of("smoker", SmokingRecipe::new))
            .build();
    
    @Override
    public String dumpToCommandString(final IRecipeManager manager, final AbstractCookingRecipe recipe) {
        
        return String.format(
                "%s.addRecipe(%s, %s, %s, %s, %s);",
                LOOKUP.get(recipe.getType()).getFirst(),
                StringUtils.quoteAndEscape(recipe.getId()),
                new MCItemStackMutable(recipe.getRecipeOutput()).getCommandString(),
                IIngredient.fromIngredient(recipe.getIngredients().get(0)),
                recipe.getExperience(),
                recipe.getCookTime()
        );
    }
    
    @Override
    public Optional<AbstractCookingRecipe> replaceIngredients(final IRecipeManager manager, final AbstractCookingRecipe recipe, final List<ReplacementRule> rules) {
        
        final Ingredient originalInput = recipe.getIngredients().get(0);
        /*mutable*/ Ingredient input = originalInput;
        for (final ReplacementRule rule : rules) {
            if (rule.shouldReplace(input)) {
                input = rule.getVanillaTo();
            }
        }
        
        if (originalInput == input) return Optional.empty();
        
        final AbstractCookingRecipe newRecipe = LOOKUP.get(recipe.getType())
                .getSecond()
                .create(recipe.getId(), recipe.getGroup(), input, recipe.getRecipeOutput(), recipe.getExperience(), recipe.getCookTime());
        
        return Optional.of(newRecipe);
    }
    
}
