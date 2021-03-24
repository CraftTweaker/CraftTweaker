package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;

import java.util.regex.Pattern;

public class ActionRemoveRecipeByRegex extends ActionRecipeBase {
    
    private final Pattern compiledPat;
    
    public ActionRemoveRecipeByRegex(IRecipeManager manager, String regex) {
        
        super(manager);
        this.compiledPat = Pattern.compile(regex);
    }
    
    @Override
    public void apply() {
        
        getRecipes()
                .keySet()
                .removeIf(resourceLocation -> compiledPat.matcher(resourceLocation.toString()).matches());
    }
    
    @Override
    public String describe() {
        
        return "Removing \"" + getRecipeTypeName() + "\" recipe with names that match the regex: \"" + compiledPat.pattern() + "\"";
    }
    
}
