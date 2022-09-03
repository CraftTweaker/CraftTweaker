package com.blamejared.crafttweaker.impl.script;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.util.sequence.SequenceManager;
import com.blamejared.crafttweaker.api.util.sequence.SequenceType;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.script.scriptrun.ThroughRecipeScriptRunManager;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RecipeManagerScriptLoader {
    
    private static UpdatedState currentState = UpdatedState.NONE;
    private static RecipeManager manager = null;
    
    public static void updateState(UpdatedState state, @Nullable Supplier<RecipeManager> managerSupplier) {
        
        // If we get here with at-least recipes, then something happened to cause an invalid state, just reset it.
        if(currentState.hasAll()) {
            currentState = UpdatedState.NONE;
            manager = null;
            throw new IllegalArgumentException("Invalid state found in RecipeManagerScriptLoader!");
        }
        currentState = currentState.merge(state);
        if(managerSupplier != null) {
            manager = managerSupplier.get();
        }
        if(currentState.hasAll()) {
            if(manager == null) {
                throw new RuntimeException("State is ready, but manager is null!");
            }
            loadScriptsFromManager(manager);
            // At this point, both recipes and tags have been updated,
            // so we can safely reset the state for the next update cycle
            currentState = UpdatedState.NONE;
            manager = null;
        }
    }
    
    //Maybe move this / rename the class
    public static void loadScriptsFromManager(final RecipeManager manager) {
    
        SequenceManager.clearSequences(SequenceType.CLIENT_THREAD_LEVEL);
        
        if(Services.CLIENT.isSingleplayer()) {
            
            return;
        }
        
        fixRecipeManager(manager);
        
        final Map<ResourceLocation, Recipe<?>> recipes = ((AccessRecipeManager) manager).crafttweaker$getRecipes()
                .getOrDefault(ScriptRecipeType.INSTANCE, Collections.emptyMap());
        
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
        accessRecipeManager.crafttweaker$setRecipes(new HashMap<>(accessRecipeManager.crafttweaker$getRecipes()));
        accessRecipeManager.crafttweaker$getRecipes()
                .replaceAll((k, v) -> new HashMap<>(accessRecipeManager.crafttweaker$getRecipes().get(k)));
        accessRecipeManager.crafttweaker$setByName(new HashMap<>(accessRecipeManager.crafttweaker$getByName()));
        CraftTweakerAPI.getAccessibleElementsProvider().recipeManager(manager);
    }
    
    public enum UpdatedState {
        NONE(false, false),
        RECIPES(true, false),
        TAGS(false, true),
        ALL(true, true);
        
        private final boolean hasRecipes;
        private final boolean hasTags;
        
        UpdatedState(boolean hasRecipes, boolean hasTags) {
            
            this.hasRecipes = hasRecipes;
            this.hasTags = hasTags;
        }
        
        public boolean hasRecipes() {
            
            return hasRecipes;
        }
        
        public boolean hasTags() {
            
            return hasTags;
        }
        
        public boolean hasAll() {
            
            return hasRecipes && hasTags;
        }
        
        public static UpdatedState of(boolean hasRecipes, boolean hasTags) {
            
            if(hasRecipes) {
                
                if(hasTags) {
                    return ALL;
                } else {
                    return RECIPES;
                }
            } else if(hasTags) {
                return TAGS;
            }
            return NONE;
        }
        
        public UpdatedState merge(UpdatedState other) {
            
            return of(this.hasRecipes || other.hasRecipes, this.hasTags || other.hasTags);
        }
    }
    
}
