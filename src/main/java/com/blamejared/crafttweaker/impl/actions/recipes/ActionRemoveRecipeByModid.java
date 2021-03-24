package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;

public class ActionRemoveRecipeByModid extends ActionRecipeBase {
    
    private final String modid;
    private final IRecipeManager.RecipeFilter exclude;
    
    public ActionRemoveRecipeByModid(IRecipeManager manager, String modid) {
        
        super(manager);
        this.modid = modid;
        this.exclude = name -> false;
    }
    
    public ActionRemoveRecipeByModid(IRecipeManager manager, String modid, IRecipeManager.RecipeFilter exclude) {
        
        super(manager);
        this.modid = modid;
        this.exclude = exclude;
    }
    
    @Override
    public void apply() {
        
        getRecipes()
                .keySet()
                .removeIf(resourceLocation -> resourceLocation.getNamespace()
                        .equals(modid) && !exclude.test(resourceLocation.getPath()));
    }
    
    @Override
    public String describe() {
        
        return "Removing \"" + getRecipeTypeName() + "\" recipes with modid: \"" + modid + "\"";
    }
    
}
