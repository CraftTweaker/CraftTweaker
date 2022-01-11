package com.blamejared.crafttweaker.impl.events;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.impl.managers.CTCraftingTableManager;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CraftTweaker.MODID)
public class CTStateEventHandler {
    
    private static UpdatedState currentState = UpdatedState.NONE;
    private static RecipeManager manager = null;
    
    private static void loadScripts() {
        
        if(manager.recipes.getOrDefault(CraftTweaker.RECIPE_TYPE_SCRIPTS, new HashMap<>())
                .size() == 0) {
            // probably joining single player, but possible the server doesn't have any recipes as well, either way, don't reload scripts!
            return;
        }
        //ImmutableMap of ImmutableMaps. Nice.
        manager.recipes = new HashMap<>(manager.recipes);
        manager.recipes.replaceAll((t, v) -> new HashMap<>(manager.recipes.get(t)));
        
        CraftTweaker.serverOverride = false;
        CTCraftingTableManager.recipeManager = manager;
        final ScriptLoadingOptions scriptLoadingOptions = new ScriptLoadingOptions().execute();
        CraftTweakerAPI.loadScriptsFromRecipeManager(manager, scriptLoadingOptions);
    }
    
    @SubscribeEvent
    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
        
        
        // If we get here with at-least recipes, then something happened to cause an invalid state, just reset it.
        if(currentState.hasAll()) {
            currentState = UpdatedState.NONE;
            manager = null;
        }
        manager = event.getRecipeManager();
        currentState = currentState.merge(UpdatedState.RECIPES);
        
        if(currentState.hasAll()) {
            loadScripts();
            // At this point, both recipes and tags have been updated,
            // so we can safely reset the state for the next update cycle
            currentState = UpdatedState.NONE;
            manager = null;
        }
    }
    
    @SubscribeEvent
    public static void onTagsUpdated(TagsUpdatedEvent event) {
        
        // If we get here with at-least recipes, then something happened to cause an invalid state, just reset it.
        if(currentState.hasAll()) {
            currentState = UpdatedState.NONE;
            manager = null;
        }
        
        currentState = currentState.merge(UpdatedState.TAGS);
        if(currentState.hasAll()) {
            if(manager == null) {
                throw new RuntimeException("Recipe manager is null in tags updated event, but state has recipes!");
            }
            loadScripts();
            // At this point, both recipes and tags have been updated,
            // so we can safely reset the state for the next update cycle
            currentState = UpdatedState.NONE;
            manager = null;
        }
    }
    
    private enum UpdatedState {
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
