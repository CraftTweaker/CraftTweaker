package com.blamejared.crafttweaker.api.recipe.replacement_old.rule;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.ITargetingRule;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public final class OutputTargetingRule implements ITargetingRule {
    
    private final IIngredient output;
    private final Collection<IRecipeManager<?>> whitelist;
    
    private OutputTargetingRule(final IIngredient output, final Collection<IRecipeManager<?>> whitelist) {
        
        this.output = output;
        this.whitelist = whitelist;
    }
    
    public static OutputTargetingRule of(final IIngredient output, final Collection<IRecipeManager<?>> whitelist) {
        
        if(output instanceof IItemStack && ((IItemStack) output).isEmpty()) {
            // Mods that return air, get fucked!
            throw new IllegalArgumentException("Unable to create an output target rule for an empty output");
        }
        return new OutputTargetingRule(output, whitelist);
    }
    
    public static OutputTargetingRule of(final IIngredient output, final IRecipeManager<?>... whitelist) {
        
        return of(output, new HashSet<>(Arrays.asList(whitelist)));
    }
    
    @Override
    public boolean shouldBeReplaced(final Recipe<?> recipe, final IRecipeManager<?> manager) {
        
        return this.output.matches(IItemStack.of(recipe.getResultItem())) && (this.whitelist.isEmpty() || this.whitelist.contains(manager));
    }
    
    @Override
    public String describe() {
        
        return String.format(
                "recipes that output %s%s",
                this.output.getCommandString(),
                this.stringifyWhitelist()
        );
    }
    
    private String stringifyWhitelist() {
        
        if(this.whitelist.isEmpty()) {
            return "";
        }
        
        return this.whitelist.stream()
                .map(IRecipeManager::getCommandString)
                .collect(Collectors.joining(", ", " in managers {", "}"));
    }
    
}
