package com.blamejared.crafttweaker.api.action.recipe;


import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.apache.logging.log4j.Logger;

public class ActionRemoveRecipeByName<T extends Recipe<?>> extends ActionRecipeBase<T> {
    
    private final ResourceLocation name;
    
    public ActionRemoveRecipeByName(IRecipeManager<T> manager, ResourceLocation name) {
        
        super(manager);
        this.name = name;
    }
    
    @Override
    public void apply() {
        
        getRecipeMutator().remove(name);
        
    }
    
    @Override
    public String describe() {
        
        return "Removing \"" + getRecipeTypeName() + "\" recipe with name: \"" + name + "\"";
    }
    
    @Override
    public boolean validate(Logger logger) {
        
        
        boolean containsKey = getRecipeMutator().has(name);
        if(!containsKey) {
            logger.warn("No recipe with type: '{}' and name: '{}'", getRecipeTypeName(), name);
        }
        return containsKey;
    }
    
}
