package com.blamejared.crafttweaker.impl.managers;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.actions.recipes.generic.ActionRemoveAllGenericRecipes;
import com.blamejared.crafttweaker.impl.actions.recipes.generic.ActionRemoveGenericRecipeByModId;
import com.blamejared.crafttweaker.impl.actions.recipes.generic.ActionRemoveGenericRecipeByName;
import com.blamejared.crafttweaker.impl.actions.recipes.generic.ActionRemoveGenericRecipeByOutput;
import com.blamejared.crafttweaker.impl.actions.recipes.generic.ActionRemoveGenericRecipeByRegex;
import com.blamejared.crafttweaker.impl.brackets.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.recipes.wrappers.WrapperRecipe;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This recipe manager allows you to perform removal actions over all recipe managers.
 * You can access this manager by using the `recipes` global keyword.
 *
 * @docParam this recipes
 */
@ZenRegister
@Document("vanilla/api/managers/GenericRecipesManager")
@ZenCodeType.Name("crafttweaker.api.GenericRecipesManager")
public class GenericRecipesManager {
    
    
    @ZenCodeGlobals.Global("recipes")
    public static final GenericRecipesManager RECIPES = new GenericRecipesManager();
    
    /**
     * Add a new recipe based on the given recipe in a valid DataPack JSON format.
     *
     * Unlike the addJSONRecipe method in {@link IRecipeManager} you **must** set the type of the recipe within the JSON yourself.
     *
     * @param name The recipe's resource path
     * @param data The recipe in JSON format
     *
     * @docParam name "recipe_name"
     * @docParam data {
     * type: "minecraft:smoking",
     * ingredient: <item:minecraft:gold_ore>,
     * result: <item:minecraft:cooked_porkchop>,
     * experience: 0.35 as float,
     * cookingtime: 100
     * }
     */
    @ZenCodeType.Method
    public void addJSONRecipe(String name, IData data) {
        
        if(!(data instanceof MapData)) {
            throw new IllegalArgumentException("Json recipe's IData should be a MapData!");
        }
        final JsonObject recipeObject = IRecipeManager.JSON_RECIPE_GSON.fromJson(data
                .toJsonString(), JsonObject.class);
        if(!recipeObject.has("type")) {
            throw new IllegalArgumentException("Serializer type missing!");
        }
        if(recipeObject.get("type")
                .getAsString()
                .equals("crafttweaker:scripts")) {
            throw new IllegalArgumentException("Cannot add a recipe to the CraftTweaker Scripts recipe type!");
        }
        
        final ResourceLocation recipeName = new ResourceLocation(CraftTweaker.MODID, name);
        final IRecipe<?> result = RecipeManager.deserializeRecipe(recipeName, recipeObject);
        final RecipeManagerWrapper recipeManagerWrapper = new RecipeManagerWrapper(result
                .getType());
        CraftTweakerAPI.apply(new ActionAddRecipe(recipeManagerWrapper, result, null));
    }
    
    @ZenCodeType.Method
    public WrapperRecipe getRecipeByName(String name) {
        
        WrapperRecipe recipe = getRecipeMap().get(new ResourceLocation(name));
        if(recipe == null) {
            throw new IllegalArgumentException("No recipe found with name: \"" + name + "\"");
        }
        return recipe;
    }
    
    @ZenCodeType.Method
    public List<WrapperRecipe> getRecipesByOutput(IIngredient output) {
        
        return getAllRecipes().stream()
                .filter(iRecipe -> output.matches(iRecipe.getOutput()))
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("allRecipes")
    public List<WrapperRecipe> getAllRecipes() {
        
        return getAllManagers().stream()
                .map(IRecipeManager::getAllRecipes)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
    
    /**
     * Returns a map of all known recipes.
     *
     * @return A Map of recipe name to recipe of all known recipes.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("recipeMap")
    public Map<ResourceLocation, WrapperRecipe> getRecipeMap() {
        
        return getAllManagers().stream()
                .map(IRecipeManager::getRecipeMap)
                .flatMap(recipeMap -> recipeMap
                        .entrySet()
                        .stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    
    /**
     * Removes recipes by output
     *
     * @param output The recipe result
     *
     * @docParam output <item:minecraft:iron_ingot>
     */
    @ZenCodeType.Method
    public void removeRecipe(IIngredient output) {
        
        CraftTweakerAPI.apply(new ActionRemoveGenericRecipeByOutput(output));
    }
    
    /**
     * Removes all recipes with this name.
     *
     * @param name The recipe name to remove
     */
    @ZenCodeType.Method
    public void removeByName(String name) {
        
        CraftTweakerAPI.apply(new ActionRemoveGenericRecipeByName(name));
    }
    
    /**
     * Removes all recipes from the provided mod.
     * Chooses the recipes based on their full recipe name, not based on output item!
     *
     * @param modId The mod's modId
     *
     * @docParam modId "crafttweaker"
     */
    @ZenCodeType.Method
    public void removeByModid(String modId) {
        
        removeByModid(modId, null);
    }
    
    /**
     * Removes all recipes from the provided mod.
     * Allows a function to exclude certain recipe names from being removed.
     * In the example below, only the recipe for the white bed would remain.
     * Since the recipe's namespace is already fixed based on the modId argument,
     * the recipe filter will only check the resource path!
     *
     * @param modId   The mod's modid
     * @param exclude Function that returns `true` if the recipe should remain in the registry.
     *
     * @docParam modId "minecraft"
     * @docParam exclude (recipeName as string) => recipeName == "white_bed"
     */
    @ZenCodeType.Method
    public void removeByModid(String modId, IRecipeManager.RecipeFilter exclude) {
        
        CraftTweakerAPI.apply(new ActionRemoveGenericRecipeByModId(modId, exclude));
    }
    
    /**
     * Remove recipe based on regex
     *
     * @param regex regex to match against
     *
     * @docParam regex "\\d_\\d"
     */
    @ZenCodeType.Method
    public void removeByRegex(String regex) {
        
        CraftTweakerAPI.apply(new ActionRemoveGenericRecipeByRegex(regex));
    }
    
    /**
     * Removes all recipes from all managers.
     */
    @ZenCodeType.Method
    public void removeAll() {
        
        CraftTweakerAPI.apply(new ActionRemoveAllGenericRecipes());
    }
    
    /**
     * Returns a list of all known recipe managers.
     * This includes managers added by mod integrations as well as wrapper managers added to provide simple support.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("allManagers")
    public List<IRecipeManager> getAllManagers() {
        
        return new ArrayList<>(RecipeTypeBracketHandler.getManagerInstances());
    }
    
}
