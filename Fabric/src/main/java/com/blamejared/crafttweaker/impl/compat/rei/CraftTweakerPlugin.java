package com.blamejared.crafttweaker.impl.compat.rei;

import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipeBase;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.plugin.common.displays.crafting.CraftingRecipeSizeProvider;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay;

public class CraftTweakerPlugin implements REIClientPlugin {
    
    static {
        //noinspection UnstableApiUsage
        DefaultCraftingDisplay.registerSizeProvider(recipe -> {
            if(recipe instanceof CTShapedRecipeBase base) {
                return new CraftingRecipeSizeProvider.Size(base.getRecipeWidth(), base.getRecipeHeight());
            }
            
            return null;
        });
    }
    
}
