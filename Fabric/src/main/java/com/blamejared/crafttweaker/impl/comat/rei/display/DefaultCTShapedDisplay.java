package com.blamejared.crafttweaker.impl.comat.rei.display;

import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipeBase;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay;

import java.util.Collections;
import java.util.Optional;

public class DefaultCTShapedDisplay extends DefaultCraftingDisplay<CTShapedRecipeBase> {
    
    public DefaultCTShapedDisplay(CTShapedRecipeBase recipe) {
        
        super(EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getResultItem())), Optional.of(recipe));
    }
    
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public int getWidth() {
        
        return recipe.get().getRecipeWidth();
    }
    
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public int getHeight() {
        
        return recipe.get().getRecipeHeight();
    }
    
}