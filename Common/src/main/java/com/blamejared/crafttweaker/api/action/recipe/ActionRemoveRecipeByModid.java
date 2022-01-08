package com.blamejared.crafttweaker.api.action.recipe;

import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.world.item.crafting.Recipe;

import java.util.function.Predicate;

public class ActionRemoveRecipeByModid<T extends Recipe<?>> extends ActionRecipeBase<T> {
    
    private final String modid;
    private final Predicate<String> exclude;
    
    public ActionRemoveRecipeByModid(IRecipeManager<T> manager, String modid, Predicate<String> exclude) {
        
        super(manager);
        this.modid = modid;
        this.exclude = exclude;
    }
    
    @Override
    public void apply() {
        
        getRecipes().keySet().removeIf(resourceLocation -> resourceLocation.getNamespace()
                .equals(modid) && !exclude.test(resourceLocation.getPath()));
    }
    
    @Override
    public String describe() {
        
        return "Removing \"" + getRecipeTypeName() + "\" recipes with modid: \"" + modid + "\"";
    }
    
}
