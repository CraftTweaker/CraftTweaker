package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ActionRemoveRecipeByName extends ActionRecipeBase {
    
    private final ResourceLocation name;
    
    public ActionRemoveRecipeByName(IRecipeManager manager, ResourceLocation name) {
        super(manager);
        this.name = name;
    }
    
    @Override
    public void apply() {
        getManager().getRecipes().remove(name);
    }
    
    @Override
    public String describe() {
        return "Removing \"" + Registry.RECIPE_TYPE.getKey(getManager().getRecipeType()) + "\" recipe with name: \"" + name + "\"";
    }
    
    @Override
    public boolean validate(ILogger logger) {
        boolean containsKey = getManager().getRecipes().containsKey(name);
        if(!containsKey) {
            logger.warning("No recipe with type: \"" + Registry.RECIPE_TYPE.getKey(getManager().getRecipeType()) + "\" and name: \"" + name + "\"");
        }
        return containsKey;
    }
}
