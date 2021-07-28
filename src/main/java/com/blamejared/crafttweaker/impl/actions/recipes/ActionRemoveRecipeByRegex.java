package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;

import java.util.regex.Pattern;

public class ActionRemoveRecipeByRegex extends ActionRecipeBase {
    
    private final Pattern compiledPat;
    private final IRecipeManager.RecipeFilter exclude;
    
    public ActionRemoveRecipeByRegex(IRecipeManager manager, String regex) {
        
        super(manager);
        this.compiledPat = Pattern.compile(regex);
        this.exclude = name -> false;
    }
    
    public ActionRemoveRecipeByRegex(IRecipeManager manager, String regex, IRecipeManager.RecipeFilter exclude) {
        
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
