package com.blamejared.crafttweaker.api.action.recipe;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Map;

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
        
        final Map<ResourceLocation, T> recipes = getManager().getRecipes();
        if(recipes.containsKey(recipe.getId())) {
            CraftTweakerAPI.LOGGER.warn(
                    "A recipe with the name '{}' already exists and will be overwritten: this is most likely an error in your scripts",
                    recipe.getId().getPath()
            );
        }
        recipes.put(recipe.getId(), recipe);
    }
    
    @Override
    public String describe() {
        
        return "Adding '%s' recipe%s, with name: '%s' that outputs: '%s'".formatted(getManager().getBracketResourceLocation(), getSubTypeDescription(), recipe.getId(), describeOutputs());
    }
    
    protected String describeOutputs() {
        
        return Services.PLATFORM.createMCItemStackMutable(recipe.getResultItem()).getCommandString();
    }
    
    private String getSubTypeDescription() {
        
        if(subType != null && !subType.trim().isEmpty()) {
            return ", of type: \"" + subType + "\"";
        } else {
            return "";
        }
    }
    
}
