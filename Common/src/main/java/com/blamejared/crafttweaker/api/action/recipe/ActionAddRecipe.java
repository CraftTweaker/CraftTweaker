package com.blamejared.crafttweaker.api.action.recipe;

import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import net.minecraft.world.item.crafting.Recipe;

public class ActionAddRecipe<T extends Recipe<?>> extends ActionRecipeBase<T> {
    
    protected final T recipe;
    private final String subType;
    
    public ActionAddRecipe(IRecipeManager<T> recipeManager, T recipe, String subType) {
        
        super(recipeManager);
        this.recipe = recipe;
        this.subType = subType;
    }
    
    public ActionAddRecipe(IRecipeManager<T> recipeManager, T recipe) {
        
        super(recipeManager);
        this.recipe = recipe;
        this.subType = "";
    }
    
    @Override
    public void apply() {
        
        getRecipeMutator().add(recipe.getId(), recipe);
    }
    
    @Override
    public String describe() {
        
        return "Adding '%s' recipe%s, with name: '%s' that outputs: '%s'".formatted(getManager().getBracketResourceLocation(), getSubTypeDescription(), recipe.getId(), describeOutputs());
    }
    
    protected String describeOutputs() {
        
        return ItemStackUtil.getCommandString(recipe.getResultItem());
    }
    
    private String getSubTypeDescription() {
        
        if(subType != null && !subType.isBlank()) {
            return ", of type: \"" + subType + "\"";
        } else {
            return "";
        }
    }
    
}
