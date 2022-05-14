package com.blamejared.crafttweaker.api.action.recipe;


import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ActionRemoveRecipeByName<T extends Recipe<?>> extends ActionRecipeBase<T> {
    
    private final ResourceLocation[] names;
    
    public ActionRemoveRecipeByName(IRecipeManager<T> manager, ResourceLocation name) {
        
        super(manager);
        this.names = new ResourceLocation[]{name};
    }
    
    public ActionRemoveRecipeByName(IRecipeManager<T> manager, ResourceLocation... names) {
        
        super(manager);
        this.names = names;
    }
    
    @Override
    public void apply() {
    
        for(ResourceLocation name : names) {
            getRecipeMutator().remove(name);
        }
    }
    
    @Override
    public String describe() {
        
        return "Removing '" + getRecipeTypeName() + "' recipe(s) with name(s): '" + Arrays.stream(names).map(ResourceLocation::toString).collect(Collectors.joining(", ", "[", "]")) + "'";
    }
    
    @Override
    public boolean validate(Logger logger) {
        boolean containsKey = true;
        for(ResourceLocation name : names) {
            boolean contains = getRecipeMutator().has(name);
            if(!contains) {
                logger.warn("No recipe with type: '{}' and name: '{}'", getRecipeTypeName(), name);
                containsKey = false;
            }
        }
        
       
        return containsKey;
    }
    
}
