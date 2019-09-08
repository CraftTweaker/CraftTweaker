package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import net.minecraft.util.registry.Registry;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ActionRemoveRecipeByRegex extends ActionRecipeBase {
    
    private final Pattern compiledPat;
    
    public ActionRemoveRecipeByRegex(IRecipeManager manager, String regex) {
        super(manager);
        this.compiledPat = Pattern.compile(regex);
    }
    
    @Override
    public void apply() {
        getManager().getRecipes().keySet().stream().filter(resourceLocation -> compiledPat.matcher(resourceLocation.toString()).matches()).collect(Collectors.toList()).forEach(getManager().getRecipes()::remove);
    }
    
    @Override
    public String describe() {
        return "Removing \"" + Registry.RECIPE_TYPE.getKey(getManager().getRecipeType()) + "\" recipe with names that match the regex: \"" + compiledPat.pattern() + "\"";
    }
    
}
