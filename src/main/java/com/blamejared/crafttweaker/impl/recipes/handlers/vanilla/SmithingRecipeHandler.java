package com.blamejared.crafttweaker.impl.recipes.handlers.vanilla;

import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.helper.IngredientHelper;
import com.blamejared.crafttweaker.impl.helper.ItemStackHelper;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@IRecipeHandler.For(SmithingRecipe.class)
public final class SmithingRecipeHandler implements IRecipeHandler<SmithingRecipe> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager manager, final SmithingRecipe recipe) {
        
        return String.format(
                "smithing.addRecipe(%s, %s, %s, %s);",
                StringUtils.quoteAndEscape(recipe.getId()),
                ItemStackHelper.getCommandString(recipe.getRecipeOutput()),
                IIngredient.fromIngredient(recipe.base).getCommandString(),
                IIngredient.fromIngredient(recipe.addition).getCommandString()
        );
    }
    
    @Override
    public Optional<Function<ResourceLocation, SmithingRecipe>> replaceIngredients(final IRecipeManager manager, final SmithingRecipe recipe, final List<IReplacementRule> rules) {
        
        final Optional<Ingredient> base = IRecipeHandler.attemptReplacing(recipe.base, Ingredient.class, recipe, rules);
        final Optional<Ingredient> addition = IRecipeHandler.attemptReplacing(recipe.addition, Ingredient.class, recipe, rules);
    
        if(!base.isPresent() && !addition.isPresent()) {
            return Optional.empty();
        }
        
        return Optional.of(id -> new SmithingRecipe(id, base.orElseGet(() -> recipe.base), addition.orElseGet(() -> recipe.addition), recipe.getRecipeOutput()));
    }
    
    @Override
    public boolean conflictsWith(final IRecipeManager manager, final SmithingRecipe firstRecipe, final IRecipe<?> secondRecipe) {
        
        if (!(secondRecipe instanceof SmithingRecipe)) {
            
            // If it is not an instanceof the normal recipe class, it means it has been added by a mod. To ensure symmetry,
            // we redirect to the other recipe handler. We are sure this cannot cause an infinite loop because recipe
            // handlers are checked based on the recipe class or superclasses. If secondRecipe were to redirect here,
            // it means that the if above would be false. If secondRecipe were not to have a recipe handler, it would
            // get the default of "never conflicts" anyway. Last possibility is for secondRecipe to have a mod-added
            // handler, and infinite loops are now up to them
            return this.redirectNonVanilla(manager, secondRecipe, firstRecipe);
        }
        
        final SmithingRecipe second = (SmithingRecipe) secondRecipe;
        
        return IngredientHelper.canConflict(firstRecipe.base, second.base) && IngredientHelper.canConflict(firstRecipe.addition, second.addition);
    }
    
    private <T extends IRecipe<?>> boolean redirectNonVanilla(final IRecipeManager manager, final T second, final SmithingRecipe first) {
        
        return CraftTweakerRegistry.getHandlerFor(second).conflictsWith(manager, second, first);
    }
    
}
