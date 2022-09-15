package com.blamejared.crafttweaker.api.action.recipe.replace;

import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByName;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.function.Function;
import java.util.function.Supplier;

public final class ActionReplaceRecipe<T extends Recipe<?>> implements IRuntimeAction {
    
    private final ResourceLocation oldName;
    private final ResourceLocation newName;
    private final Supplier<ActionAddRecipe<T>> addRecipe;
    private final ActionRemoveRecipeByName<T> removeRecipe;
    
    ActionReplaceRecipe(final ResourceLocation name, final IRecipeManager<T> manager, final Function<ResourceLocation, T> recipeCreator) {
        
        this.oldName = name;
        this.newName = this.createNewName();
        this.addRecipe = () -> new ActionAddRecipe<>(manager, recipeCreator.apply(this.newName));
        this.removeRecipe = new ActionRemoveRecipeByName<>(manager, this.oldName);
    }
    
    @Override
    public void apply() {
    
        this.removeRecipe.apply();
        this.addRecipe.get().apply();
    }
    
    @Override
    public String describe() {
        
        return null;
    }
    
    private ResourceLocation createNewName() {
        
        return this.oldName; // TODO("")
    }
    
}
