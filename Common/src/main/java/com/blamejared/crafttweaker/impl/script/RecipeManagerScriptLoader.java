package com.blamejared.crafttweaker.impl.script;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.zencode.impl.CraftTweakerDefaultScriptRunConfiguration;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.HashMap;

public class RecipeManagerScriptLoader {
    
    //Maybe move this / rename the class
    public static void loadScriptsFromManager(RecipeManager manager) {
        
        if(Services.CLIENT.isSingleplayer()) {
            return;
        }
        if(((AccessRecipeManager) manager).getRecipes()
                .getOrDefault(CraftTweakerRegistries.RECIPE_TYPE_SCRIPTS, new HashMap<>())
                .size() == 0) {
            // The server does not have any scripts, so don't reload scripts!
            return;
        }
        //ImmutableMap of ImmutableMaps. Nice.
        AccessRecipeManager accessRecipeManager = (AccessRecipeManager) manager;
        accessRecipeManager.setRecipes(new HashMap<>(accessRecipeManager.getRecipes()));
        accessRecipeManager.getRecipes().replaceAll((k, v) -> new HashMap<>(accessRecipeManager.getRecipes().get(k)));
        CraftTweakerAPI.setRecipeManager(manager);
        
        final ScriptLoadingOptions scriptLoadingOptions = new ScriptLoadingOptions()
                .setSource(ScriptLoadingOptions.CLIENT_RECIPES_UPDATED_SCRIPT_SOURCE)
                .setRunConfiguration(CraftTweakerDefaultScriptRunConfiguration.DEFAULT_CONFIGURATION)
                .execute();
        CraftTweakerAPI.loadScriptsFromRecipeManager(manager, scriptLoadingOptions);
    }
    
}
