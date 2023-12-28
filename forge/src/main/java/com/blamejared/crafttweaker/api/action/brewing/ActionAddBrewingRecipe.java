package com.blamejared.crafttweaker.api.action.brewing;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.MCItemStackMutable;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import java.util.List;

public class ActionAddBrewingRecipe extends ActionBrewingBase {
    
    private final BrewingRecipe recipe;
    
    public ActionAddBrewingRecipe(List<IBrewingRecipe> recipes, BrewingRecipe recipe) {
        
        super(recipes);
        this.recipe = recipe;
    }
    
    @Override
    public void apply() {
        
        BrewingRecipeRegistry.addRecipe(recipe);
    }
    
    @Override
    public void undo() {
        
        recipes.remove(recipe);
    }
    
    @Override
    public String describe() {
        
        return "Adding a brewing recipe. output: " + new MCItemStackMutable(this.recipe.getOutput()) + ", input: " + IIngredient
                .fromIngredient(this.recipe.getInput()) + " and ingredient: " + IIngredient.fromIngredient(this.recipe.getIngredient());
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing addition of brewing recipe. output: " + new MCItemStackMutable(this.recipe.getOutput()) + ", input: " + IIngredient
                .fromIngredient(this.recipe.getInput()) + " and ingredient: " + IIngredient.fromIngredient(this.recipe.getIngredient());
    }
    
}
