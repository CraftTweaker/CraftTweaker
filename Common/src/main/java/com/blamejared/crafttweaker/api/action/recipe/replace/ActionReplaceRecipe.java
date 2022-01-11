package com.blamejared.crafttweaker.api.action.recipe.replace;

import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRecipeBase;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByName;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.function.Function;
import java.util.function.Supplier;

public class ActionReplaceRecipe<T extends Recipe<?>> extends ActionRecipeBase<T> {
    
    private final Supplier<ActionAddRecipe<T>> addRecipe;
    private final ActionRemoveRecipeByName<T> removeRecipe;
    private final ResourceLocation oldName;
    private final Supplier<ResourceLocation> newName;
    
    public ActionReplaceRecipe(final IRecipeManager<T> manager, final Function<ResourceLocation, ResourceLocation> nameGenerator,
                               final Recipe<?> oldRecipe, final Function<ResourceLocation, T> recipeCreator) {
        
        super(manager);
        this.oldName = oldRecipe.getId();
        this.newName = Suppliers.memoize(() -> nameGenerator.apply(this.oldName));
        this.removeRecipe = new ActionRemoveRecipeByName<>(manager, this.oldName);
        this.addRecipe = () -> new ActionAddRecipe<>(manager, recipeCreator.apply(this.newName.get()));
    }
    
    @Override
    public void apply() {
        
        this.removeRecipe.apply();
        this.addRecipe.get().apply();
    }
    
    @Override
    public String describe() {
        
        return String.format(
                "- Replacing \"%s\" recipe with name \"%s\"%s",
                this.getManager().getBracketResourceLocation(),
                this.oldName,
                this.oldName.equals(this.newName.get()) ? "" : String.format(", renaming it to \"%s\"", this.newName.get())
        );
    }
    
}
