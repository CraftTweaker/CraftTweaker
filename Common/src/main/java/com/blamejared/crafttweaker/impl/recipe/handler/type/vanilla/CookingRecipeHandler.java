package com.blamejared.crafttweaker.impl.recipe.handler.type.vanilla;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;

import java.util.Map;
import java.util.Optional;

@IRecipeHandler.For(BlastingRecipe.class)
@IRecipeHandler.For(CampfireCookingRecipe.class)
@IRecipeHandler.For(SmeltingRecipe.class)
@IRecipeHandler.For(SmokingRecipe.class)
public final class CookingRecipeHandler implements IRecipeHandler<AbstractCookingRecipe> {
    
    @FunctionalInterface
    private interface CookingRecipeFactory<T extends AbstractCookingRecipe> {
        
        T create(final String group, final CookingBookCategory category, final Ingredient ingredient, final ItemStack result, final float experience, final int cookTime);
        
    }
    
    private static final Map<RecipeType<?>, Pair<String, CookingRecipeFactory<?>>> LOOKUP = ImmutableMap
            .<RecipeType<?>, Pair<String, CookingRecipeFactory<?>>> builder()
            .put(RecipeType.BLASTING, Pair.of("blastFurnace", BlastingRecipe::new))
            .put(RecipeType.CAMPFIRE_COOKING, Pair.of("campfire", CampfireCookingRecipe::new))
            .put(RecipeType.SMELTING, Pair.of("furnace", SmeltingRecipe::new))
            .put(RecipeType.SMOKING, Pair.of("smoker", SmokingRecipe::new))
            .build();
    
    @Override
    public String dumpToCommandString(final IRecipeManager<? super AbstractCookingRecipe> manager, final RecipeHolder<AbstractCookingRecipe> holder) {
        
        AbstractCookingRecipe recipe = holder.value();
        return String.format(
                "%s.addRecipe(%s, %s, %s, %s, %s);",
                LOOKUP.get(recipe.getType()).getFirst(),
                StringUtil.quoteAndEscape(holder.id()),
                ItemStackUtil.getCommandString(AccessibleElementsProvider.get().registryAccess(recipe::getResultItem)),
                IIngredient.fromIngredient(recipe.getIngredients().get(0)).getCommandString(),
                recipe.getExperience(),
                recipe.getCookingTime()
        );
    }
    
    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super AbstractCookingRecipe> manager, RecipeHolder<AbstractCookingRecipe> firstHolder, RecipeHolder<U> secondHolder) {
        
        return IngredientUtil.canConflict(firstHolder.value().getIngredients().get(0), secondHolder.value()
                .getIngredients()
                .get(0));
    }
    
    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super AbstractCookingRecipe> manager, RecipeHolder<AbstractCookingRecipe> holder) {
        
        AbstractCookingRecipe recipe = holder.value();
        final IIngredient ingredient = IIngredient.fromIngredient(recipe.getIngredients().get(0));
        final IDecomposedRecipe decomposition = IDecomposedRecipe.builder()
                .with(BuiltinRecipeComponents.Metadata.GROUP, recipe.getGroup())
                .with(BuiltinRecipeComponents.Metadata.COOKING_BOOK_CATEGORY, recipe.category())
                .with(BuiltinRecipeComponents.Input.INGREDIENTS, ingredient)
                .with(BuiltinRecipeComponents.Processing.TIME, recipe.getCookingTime())
                .with(BuiltinRecipeComponents.Output.EXPERIENCE, recipe.getExperience())
                .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.of(AccessibleElementsProvider.get()
                        .registryAccess(recipe::getResultItem)))
                .build();
        return Optional.of(decomposition);
    }
    
    @Override
    public Optional<RecipeHolder<AbstractCookingRecipe>> recompose(final IRecipeManager<? super AbstractCookingRecipe> manager, final ResourceLocation name, final IDecomposedRecipe recipe) {
        
        final String group = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.GROUP);
        final CookingBookCategory category = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.COOKING_BOOK_CATEGORY);
        final IIngredient input = recipe.getOrThrowSingle(BuiltinRecipeComponents.Input.INGREDIENTS);
        final int cookTime = recipe.getOrThrowSingle(BuiltinRecipeComponents.Processing.TIME);
        final float experience = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.EXPERIENCE);
        final IItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS);
        
        if(input.isEmpty()) {
            throw new IllegalArgumentException("Invalid input: empty ingredient");
        }
        if(cookTime <= 0) {
            throw new IllegalArgumentException("Invalid cooking time: less than min allowed 1: " + cookTime);
        }
        if(experience < 0) {
            throw new IllegalArgumentException("Invalid experience: less than min allowed 0:" + experience);
        }
        if(output.isEmpty()) {
            throw new IllegalArgumentException("Invalid output: empty stack");
        }
        
        final CookingRecipeFactory<?> factory = LOOKUP.get(manager.getRecipeType()).getSecond();
        return Optional.of(new RecipeHolder<>(name, factory.create(group, category, input.asVanillaIngredient(), output.getInternal(), experience, cookTime)));
    }
    
}
