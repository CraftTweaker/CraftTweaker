package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.IRecipe;

public class ActionAddRecipe extends ActionRecipeBase {
    
    protected final IRecipe<?> recipe;
    private final String subType;
    
    public ActionAddRecipe(IRecipeManager recipeManager, IRecipe<?> recipe, String subType) {
        
        super(recipeManager);
        this.recipe = recipe;
        this.subType = subType;
    }
    
    public ActionAddRecipe(IRecipeManager recipeManager, IRecipe<?> recipe) {
        
        super(recipeManager);
        this.recipe = recipe;
        this.subType = "";
    }
    
    @Override
    public void apply() {
        
        getManager().getRecipes().put(recipe.getId(), recipe);
    }
    
    @Override
    public String describe() {
        
        return "Adding \"" + getManager().getBracketResourceLocation() + "\" recipe" + getSubTypeDescription() + ", with name: \"" + recipe
                .getId() + "\" that outputs: " + describeOutputs();
    }
    
    protected String describeOutputs() {
        
        return new MCItemStackMutable(recipe.getRecipeOutput()).toString();
    }
    
    private String getSubTypeDescription() {
        
        if(subType != null && !subType.trim().isEmpty()) {
            return ", of type: \"" + subType + "\"";
        } else {
            return "";
        }
    }
    
}
