package com.blamejared.crafttweaker.impl.recipe.handler.type.vanilla;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandlerRegistry;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessLegacyUpgradeRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.LegacyUpgradeRecipe;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("removal")
@IRecipeHandler.For(LegacyUpgradeRecipe.class)
public final class LegacySmithingRecipeHandler implements IRecipeHandler<LegacyUpgradeRecipe> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager<? super LegacyUpgradeRecipe> manager, final LegacyUpgradeRecipe recipe) {
        
        return String.format(
                "smithing.addLegacyRecipe(%s, %s, %s, %s);",
                StringUtil.quoteAndEscape(recipe.getId()),
                ItemStackUtil.getCommandString(AccessibleElementsProvider.get().registryAccess(recipe::getResultItem)),
                IIngredient.fromIngredient(((AccessLegacyUpgradeRecipe) recipe).crafttweaker$getBase())
                        .getCommandString(),
                IIngredient.fromIngredient(((AccessLegacyUpgradeRecipe) recipe).crafttweaker$getAddition())
                        .getCommandString()
        );
    }
    
    @Override
    public <U extends Recipe<?>> boolean doesConflict(final IRecipeManager<? super LegacyUpgradeRecipe> manager, final LegacyUpgradeRecipe firstRecipe, final U secondRecipe) {
        
        if(!(secondRecipe instanceof LegacyUpgradeRecipe)) {
            
            // If it is not an instanceof the normal recipe class, it means it has been added by a mod. To ensure symmetry,
            // we redirect to the other recipe handler. We are sure this cannot cause an infinite loop because recipe
            // handlers are checked based on the recipe class or superclasses. If secondRecipe were to redirect here,
            // it means that the if above would be false. If secondRecipe were not to have a recipe handler, it would
            // get the default of "never conflicts" anyway. Last possibility is for secondRecipe to have a mod-added
            // handler, and infinite loops are now up to them
            return this.redirectNonVanilla(manager, secondRecipe, firstRecipe);
        }
        
        final AccessLegacyUpgradeRecipe first = (AccessLegacyUpgradeRecipe) firstRecipe;
        final AccessLegacyUpgradeRecipe second = (AccessLegacyUpgradeRecipe) secondRecipe; // It is an UpgradeRecipe, thus it also is our accessor
        
        return IngredientUtil.canConflict(first.crafttweaker$getBase(), second.crafttweaker$getBase())
                && IngredientUtil.canConflict(first.crafttweaker$getAddition(), second.crafttweaker$getAddition());
    }
    
    @Override
    public Optional<IDecomposedRecipe> decompose(final IRecipeManager<? super LegacyUpgradeRecipe> manager, final LegacyUpgradeRecipe recipe) {
        
        final AccessLegacyUpgradeRecipe access = (AccessLegacyUpgradeRecipe) recipe;
        final IIngredient base = IIngredient.fromIngredient(access.crafttweaker$getBase());
        final IIngredient addition = IIngredient.fromIngredient(access.crafttweaker$getAddition());
        final IDecomposedRecipe decomposed = IDecomposedRecipe.builder()
                .with(BuiltinRecipeComponents.Input.INGREDIENTS, List.of(base, addition))
                .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.of(AccessibleElementsProvider.get()
                        .registryAccess(recipe::getResultItem)))
                .build();
        
        return Optional.of(decomposed);
    }
    
    @Override
    public Optional<LegacyUpgradeRecipe> recompose(final IRecipeManager<? super LegacyUpgradeRecipe> manager, final ResourceLocation name, final IDecomposedRecipe recipe) {
        
        final List<IIngredient> ingredients = recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS);
        final IItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS);
        
        if(ingredients.size() != 2) {
            throw new IllegalArgumentException("Invalid inputs: expected two ingredients for recipe, but got " + ingredients.size() + ": " + ingredients);
        }
        if(ingredients.get(0).isEmpty() || ingredients.get(1).isEmpty()) {
            throw new IllegalArgumentException("Invalid inputs: empty ingredients");
        }
        if(output.isEmpty()) {
            throw new IllegalArgumentException("Invalid outputs: empty item");
        }
        
        final Ingredient base = ingredients.get(0).asVanillaIngredient();
        final Ingredient addition = ingredients.get(1).asVanillaIngredient();
        //noinspection removal
        return Optional.of(new LegacyUpgradeRecipe(name, base, addition, output.getInternal()));
    }
    
    private <T extends Recipe<?>> boolean redirectNonVanilla(final IRecipeManager<?> manager, final T second, final LegacyUpgradeRecipe first) {
        
        return IRecipeHandlerRegistry.getHandlerFor(second).doesConflict(GenericUtil.uncheck(manager), second, first);
    }
    
}
