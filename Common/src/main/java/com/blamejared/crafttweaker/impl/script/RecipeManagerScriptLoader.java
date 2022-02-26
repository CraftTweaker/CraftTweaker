package com.blamejared.crafttweaker.impl.script;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.script.scriptrun.ThroughRecipeScriptRunManager;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RecipeManagerScriptLoader {
    
    //Maybe move this / rename the class
    public static void loadScriptsFromManager(final RecipeManager manager) {
        
        if(Services.CLIENT.isSingleplayer()) {
            
            return;
        }
        
        fixRecipeManager(manager);
        
        final Map<ResourceLocation, Recipe<?>> recipes = ((AccessRecipeManager) manager).getRecipes()
                .getOrDefault(CraftTweakerRegistries.RECIPE_TYPE_SCRIPTS, Collections.emptyMap());
        
        if(recipes.isEmpty()) {
            
            // The server does not have any scripts, so don't reload scripts!
            return;
        }
        
        final Collection<ScriptRecipe> scriptRecipes = recipes.values()
                .stream()
                .map(ScriptRecipe.class::cast)
                .toList();
        final ScriptRunConfiguration configuration = new ScriptRunConfiguration(
                CraftTweakerConstants.DEFAULT_LOADER_NAME,
                CraftTweakerConstants.CLIENT_RECIPES_UPDATED_SOURCE_ID,
                ScriptRunConfiguration.RunKind.EXECUTE
        );
        
        try {
            
            ThroughRecipeScriptRunManager.createScriptRunFromRecipes(scriptRecipes, configuration).execute();
        } catch(final Throwable e) {
            
            CraftTweakerAPI.LOGGER.error("Unable to execute script run", e);
        }
    }
    
    private static void fixRecipeManager(final RecipeManager manager) {
        
        //ImmutableMap of ImmutableMaps. Nice.
        final AccessRecipeManager accessRecipeManager = (AccessRecipeManager) manager;
        accessRecipeManager.setRecipes(new HashMap<>(accessRecipeManager.getRecipes()));
        accessRecipeManager.getRecipes()
                .replaceAll((k, v) -> new HashMap<>(accessRecipeManager.getRecipes().get(k)));
        accessRecipeManager.setByName(new HashMap<>(accessRecipeManager.getByName()));
        CraftTweakerAPI.getAccessibleElementsProvider().recipeManager(manager);
    }
    
}
