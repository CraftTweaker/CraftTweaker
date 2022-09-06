package com.blamejared.crafttweaker.api.recipe.replacement_old.rule;


import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientList;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Arrays;
import java.util.Optional;

public final class StackTargetingReplacementRule implements IReplacementRule {
    
    private final IItemStack from;
    private final IIngredient to;
    
    private StackTargetingReplacementRule(final IItemStack from, final IIngredient to) {
        
        this.from = from.copy();
        this.to = to;
    }
    
    public static IReplacementRule create(final IItemStack from, final IIngredient to) {
        
        return to instanceof IItemStack && from.matches((IItemStack) to) ? IReplacementRule.EMPTY : new StackTargetingReplacementRule(from, to);
    }
    
    @Override
    public <T, U extends Recipe<?>> Optional<T> getReplacement(final T ingredient, final Class<T> type, final U recipe) {
        
        return IReplacementRule.chain(
                IReplacementRule.withType(ingredient, type, recipe, IIngredient.class, this::getIIngredientReplacement),
                IReplacementRule.withType(ingredient, type, recipe, Ingredient.class, this::getIngredientReplacement)
        );
    }
    
    private <U extends Recipe<?>> Optional<IIngredient> getIIngredientReplacement(final IIngredient ingredient, final U recipe) {
        
        final IItemStack[] oldItems = ingredient.getItems();
        final IIngredient[] newItems = Arrays.stream(oldItems)
                .map(this::getStackReplacement)
                .toArray(IIngredient[]::new);
        
        return Arrays.equals(oldItems, newItems) ? Optional.empty() : Optional.of(new IIngredientList(newItems));
    }
    
    private <U extends Recipe<?>> Optional<Ingredient> getIngredientReplacement(final Ingredient ingredient, final U recipe) {
        
        return this.getIIngredientReplacement(IIngredient.fromIngredient(ingredient), recipe)
                .map(IIngredient::asVanillaIngredient);
    }
    
    private IIngredient getStackReplacement(final IItemStack original) {
        
        return this.from.matches(original) ? this.to : original;
    }
    
    @Override
    public String describe() {
        
        return String.format("Replacing stacks %s --> %s", this.from.getCommandString(), this.to.getCommandString());
    }
    
}
