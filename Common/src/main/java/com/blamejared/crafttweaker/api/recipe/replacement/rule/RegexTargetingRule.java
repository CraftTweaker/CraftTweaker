package com.blamejared.crafttweaker.api.recipe.replacement.rule;


import com.blamejared.crafttweaker.api.recipe.handler.ITargetingRule;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.world.item.crafting.Recipe;

import java.util.regex.Pattern;

public final class RegexTargetingRule implements ITargetingRule {
    
    private final Pattern regex;
    private final boolean recipes;
    
    private RegexTargetingRule(final Pattern regex, final boolean recipes) {
        
        this.regex = regex;
        this.recipes = recipes;
    }
    
    public static RegexTargetingRule of(final Pattern regex, final boolean recipes) {
        
        return new RegexTargetingRule(regex, recipes);
    }
    
    public static RegexTargetingRule of(final String regex, final boolean recipes) {
        
        return new RegexTargetingRule(Pattern.compile(regex), recipes);
    }
    
    @Override
    public boolean shouldBeReplaced(final Recipe<?> recipe, final IRecipeManager<?> manager) {
        
        return this.regex.matcher((this.recipes ? recipe.getId() : manager.getBracketResourceLocation()).toString())
                .matches();
    }
    
    @Override
    public String describe() {
        
        return String.format(
                "%s matching the regular expression %s",
                this.recipes ? "recipes" : "managers",
                this.regex.toString()
        );
    }
    
}
