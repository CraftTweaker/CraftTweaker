package com.blamejared.crafttweaker.api.action.recipe.generic;

import net.minecraft.world.item.crafting.Recipe;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class ActionRemoveGenericRecipe extends ActionRemoveGenericRecipeBase {
    
    protected final Predicate<Recipe<?>> removePredicate;
    protected Supplier<String> describeFunction;
    
    public ActionRemoveGenericRecipe(Predicate<Recipe<?>> removePredicate) {
        
        this.removePredicate = removePredicate;
        this.describeFunction = () -> "Removing all recipes that match a custom condition";
    }
    
    public ActionRemoveGenericRecipe(Predicate<Recipe<?>> removePredicate, Supplier<String> describeFunction) {
        
        this.removePredicate = removePredicate;
        this.describeFunction = describeFunction;
    }
    
    @Override
    public String describe() {
        
        return describeFunction.get();
    }
    
    @Override
    protected boolean shouldRemove(Recipe<?> recipe) {
        
        return removePredicate.test(recipe);
    }
    
}
