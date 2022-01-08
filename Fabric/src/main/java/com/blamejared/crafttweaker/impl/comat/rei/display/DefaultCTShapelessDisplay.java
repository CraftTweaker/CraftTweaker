package com.blamejared.crafttweaker.impl.comat.rei.display;

import com.blamejared.crafttweaker.api.recipe.type.CTShapelessRecipeBase;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay;

import java.util.Collections;
import java.util.Optional;

public class DefaultCTShapelessDisplay extends DefaultCraftingDisplay<CTShapelessRecipeBase> {
    
    public DefaultCTShapelessDisplay(CTShapelessRecipeBase recipe) {
        
        super(EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getResultItem())), Optional.of(recipe));
    }
    
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public int getWidth() {
        
        return this.recipe.get().getIngredients().size() > 4 ? 3 : 2;
    }
    
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public int getHeight() {
        
        return this.recipe.get().getIngredients().size() > 4 ? 3 : 2;
    }
    
}