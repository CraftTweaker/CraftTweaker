package com.blamejared.crafttweaker.api.action.recipe.generic;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Set;
import java.util.stream.Collectors;

public class ActionRemoveGenericRecipeByName extends ActionRemoveGenericRecipeBase {
    
    private final Set<ResourceLocation> names;
    
    public ActionRemoveGenericRecipeByName(String name) {
        
        this.names = Set.of(new ResourceLocation(name));
    }
    
    public ActionRemoveGenericRecipeByName(ResourceLocation[] names) {
        
        this.names = Set.of(names);
    }
    
    @Override
    public String describe() {
        
        return "Removing all recipe(s) with name(s): '" + names.stream()
                .map(ResourceLocation::toString)
                .collect(Collectors.joining(", ", "[", "]")) + "'";
    }
    
    @Override
    protected boolean shouldRemove(RecipeHolder<?> holder) {
        
        return this.names.contains(holder.id());
    }
    
}
