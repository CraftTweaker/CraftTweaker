package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.impl.script.RecipeManagerScriptLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ScriptLoadingOptions {
    
    public static final ScriptLoadSource RELOAD_LISTENER_SCRIPT_SOURCE = new ScriptLoadSource(CraftTweakerConstants.rl("reload_listener"));
    public static final ScriptLoadSource CLIENT_RECIPES_UPDATED_SCRIPT_SOURCE = new ScriptLoadSource(CraftTweakerConstants.rl("client_recipes_updated"));
    
    
    private boolean format;
    private boolean execute;
    private String loaderName = CraftTweakerConstants.DEFAULT_LOADER_NAME;
    private ScriptLoadSource source = RELOAD_LISTENER_SCRIPT_SOURCE;
    
    public ScriptLoadingOptions() {
    
    }
    
    /**
     * Causes the scripts to be formatted and outputted in the "formatted" folder
     * Used e.g by {@code /ct format}
     */
    public ScriptLoadingOptions format() {
        
        this.format = true;
        return this;
    }
    
    /**
     * Causes the scripts to be executed
     * If not set, then this will stop after checking the syntax
     */
    public ScriptLoadingOptions execute() {
        
        return setExecute(true);
    }
    
    /**
     * @see #format()
     */
    public boolean isFormat() {
        
        return format;
    }
    
    /**
     * @see #format()
     */
    public ScriptLoadingOptions setFormat(boolean format) {
        
        this.format = format;
        return this;
    }
    
    /**
     * @see #execute()
     */
    public boolean isExecute() {
        
        return execute;
    }
    
    /**
     * @see #execute()
     */
    public ScriptLoadingOptions setExecute(boolean execute) {
        
        this.execute = execute;
        return this;
    }
    
    /**
     * The current loader name
     */
    public String getLoaderName() {
        
        return loaderName;
    }
    
    /**
     * Sets the loader name.
     * Loader names are case insensitive, but are <strong>not</strong> Regular Expressions
     */
    public ScriptLoadingOptions setLoaderName(@Nonnull String loaderName) {
        
        this.loaderName = loaderName;
        return this;
    }
    
    /**
     * Gets the source type of this script load.
     *
     * @return The source type of this script load.
     */
    public ScriptLoadSource getSource() {
        
        return source;
    }
    
    /**
     * Sets the source of this script load.
     *
     * @param source The source of this script load.
     */
    public ScriptLoadingOptions setSource(ScriptLoadSource source) {
        
        this.source = source;
        return this;
    }
    
    public static record ScriptLoadSource(ResourceLocation id) {
    
    }
    
    public static class ClientScriptLoader {
        
        private static UpdatedState currentState = UpdatedState.NONE;
        private static RecipeManager manager = null;
        
        public static void updateRecipes(Supplier<RecipeManager> managerSupplier) {
    
            // If we get here with at-least recipes, then something happened to cause an invalid state, just reset it.
            if(currentState.hasAll()) {
                currentState = UpdatedState.NONE;
                manager = null;
            }
            manager = managerSupplier.get();
            currentState = currentState.merge(UpdatedState.RECIPES);
            
            if(currentState.hasAll()) {
                RecipeManagerScriptLoader.loadScriptsFromManager(manager);
                // At this point, both recipes and tags have been updated,
                // so we can safely reset the state for the next update cycle
                currentState = UpdatedState.NONE;
                manager = null;
            }
        }
        
        public static void updateTags() {
    
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
                RecipeManagerScriptLoader.loadScriptsFromManager(manager);
                // At this point, both recipes and tags have been updated,
                // so we can safely reset the state for the next update cycle
                currentState = UpdatedState.NONE;
                manager = null;
            }
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
