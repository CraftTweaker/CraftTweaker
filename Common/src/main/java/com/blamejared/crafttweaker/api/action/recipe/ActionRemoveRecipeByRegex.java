package com.blamejared.crafttweaker.api.action.recipe;

import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.world.item.crafting.Recipe;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ActionRemoveRecipeByRegex<T extends Recipe<?>> extends ActionRecipeBase<T> {
    
    private final Pattern compiledPat;
    private final Predicate<String> exclude;
    
    
    public ActionRemoveRecipeByRegex(IRecipeManager<T> manager, String regex, Predicate<String> exclude) {
        
        super(manager);
        this.compiledPat = Pattern.compile(regex);
        this.exclude = exclude;
    }
    
    @Override
    public void apply() {
        
        getRecipes()
                .keySet()
                .removeIf(resourceLocation -> compiledPat.matcher(resourceLocation.toString())
                        .matches() && !exclude.test(resourceLocation.getPath()));
    }
    
    @Override
    public String describe() {
        
        return "Removing \"" + getRecipeTypeName() + "\" recipe with names that match the regex: \"" + compiledPat.pattern() + "\"";
    }
    
}
