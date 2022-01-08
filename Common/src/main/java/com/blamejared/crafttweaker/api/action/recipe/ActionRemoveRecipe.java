package com.blamejared.crafttweaker.api.action.recipe;

import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.world.item.crafting.Recipe;

import java.util.function.Function;
import java.util.function.Predicate;

public class ActionRemoveRecipe<T extends Recipe<?>> extends ActionRecipeBase<T> {
    
    protected final Predicate<T> removePredicate;
    protected Function<ActionRecipeBase<T>, String> describeFunction;
    
    public ActionRemoveRecipe(IRecipeManager<T> manager, Predicate<T> removePredicate) {
        
        super(manager);
        this.removePredicate = removePredicate;
        this.describeFunction = action -> "Removing '%s' recipes that match a custom condition".formatted(getRecipeTypeName());
    }
    
    public ActionRemoveRecipe(IRecipeManager<T> manager, Predicate<T> removePredicate, Function<ActionRecipeBase<T>, String> describeFunction) {
        
        super(manager);
        this.removePredicate = removePredicate;
        this.describeFunction = describeFunction;
    }
    
    @Override
    public void apply() {
        
        getRecipes().keySet().removeIf(location -> removePredicate.test(getRecipes().get(location)));
    }
    
    @Override
    public String describe() {
        
        return describeFunction.apply(this);
    }
    
    
    public ActionRemoveRecipe<T> describeDefaultRemoval(CommandStringDisplayable output) {
        
        this.describeFunction = action -> "Removing '%s' recipes with output: '%s'".formatted(action.getRecipeType(), output);
        return this;
    }
    
}
