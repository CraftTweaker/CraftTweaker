package com.blamejared.crafttweaker.api.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.generic.ActionRemoveAllGenericRecipes;
import com.blamejared.crafttweaker.api.action.recipe.generic.ActionRemoveGenericRecipe;
import com.blamejared.crafttweaker.api.action.recipe.generic.ActionRemoveGenericRecipeByModId;
import com.blamejared.crafttweaker.api.action.recipe.generic.ActionRemoveGenericRecipeByName;
import com.blamejared.crafttweaker.api.action.recipe.generic.ActionRemoveGenericRecipeByOutput;
import com.blamejared.crafttweaker.api.action.recipe.generic.ActionRemoveGenericRecipeByRegex;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.visitor.DataToJsonStringVisitor;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This recipe manager allows you to perform removal actions over all recipe managers.
 * You can access this manager by using the `recipes` global keyword.
 *
 * @docParam this recipes
 */
//TODO 1.20.2 confirm using container here is fine
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.GenericRecipesManager")
@Document("vanilla/api/recipe/manager/GenericRecipesManager")
public class GenericRecipesManager {
    
    @ZenCodeGlobals.Global("recipes")
    public static final GenericRecipesManager INSTANCE = new GenericRecipesManager();
    
    private GenericRecipesManager() {}
    
    /**
     * Add a new recipe based on the given recipe in a valid DataPack JSON format.
     * <p>
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
    public void addJsonRecipe(String name, MapData data) {
        
        JsonObject recipeObject = IRecipeManager.JSON_RECIPE_GSON.fromJson(data.accept(DataToJsonStringVisitor.INSTANCE), JsonObject.class);
        if(!recipeObject.has("type")) {
            throw new IllegalArgumentException("Serializer type missing!");
        }
        if(recipeObject.get("type")
                .getAsString()
                .equals("crafttweaker:scripts")) {
            throw new IllegalArgumentException("Cannot add a recipe to the CraftTweaker Scripts recipe type!");
        }
        
        final ResourceLocation recipeName = CraftTweakerConstants.rl(name);
        final RecipeHolder<?> result = AccessRecipeManager.crafttweaker$callFromJson(recipeName, recipeObject);
        final RecipeManagerWrapper recipeManagerWrapper = new RecipeManagerWrapper(GenericUtil.uncheck(result.value().getType()));
        CraftTweakerAPI.apply(new ActionAddRecipe<>(recipeManagerWrapper, GenericUtil.uncheck(result), null));
    }
    
    @ZenCodeType.Method
    public RecipeHolder<Recipe<Container>> getRecipeByName(String name) {
        
        RecipeHolder<Recipe<Container>> recipe = getRecipeMap().get(new ResourceLocation(name));
        if(recipe == null) {
            throw new IllegalArgumentException("No recipe found with name: \"" + name + "\"");
        }
        return recipe;
    }
    
    @ZenCodeType.Method
    public List<RecipeHolder<Recipe<Container>>> getRecipesByOutput(IIngredient output) {
        
        return getAllRecipes().stream()
                .filter(recipe -> output.matches(IItemStack.of(AccessibleElementsProvider.get()
                        .registryAccess(recipe.value()::getResultItem))))
                .collect(Collectors.toList());
    }
    
    //TODO 1.20.2 confirm using container here is fine
    @ZenCodeType.Method
    @ZenCodeType.Getter("allRecipes")
    public List<RecipeHolder<Recipe<Container>>> getAllRecipes() {
        
        return GenericUtil.uncheck(getAllManagers().stream()
                .map(IRecipeManager::getAllRecipes)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
    }
    
    public List<RecipeHolder<?>> getAllRecipesRaw() {
        
        return getAllManagers().stream()
                .map(IRecipeManager::getAllRecipes)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
    
    //TODO 1.20.2 confirm using container here is fine
    /**
     * Returns a map of all known recipes.
     *
     * @return A Map of recipe name to recipe of all known recipes.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("recipeMap")
    public Map<ResourceLocation, RecipeHolder<Recipe<Container>>> getRecipeMap() {
        
        return GenericUtil.uncheck(getAllManagers().stream()
                .map(IRecipeManager::getRecipeMap)
                .flatMap(recipeMap -> recipeMap
                        .entrySet()
                        .stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }
    
    /**
     * Removes recipes by output
     *
     * @param output The recipe result
     *
     * @docParam output <item:minecraft:iron_ingot>
     * @deprecated use remove(IIngredient output)
     */
    @Deprecated(forRemoval = true)
    @ZenCodeType.Method
    public void removeRecipe(IIngredient output) {
        
        remove(output);
    }
    
    /**
     * Removes recipes by output
     *
     * @param output The recipe result
     *
     * @docParam output <item:minecraft:iron_ingot>
     */
    @ZenCodeType.Method
    public void remove(IIngredient output) {
        
        CraftTweakerAPI.apply(new ActionRemoveGenericRecipeByOutput(output));
    }
    
    /**
     * Removes all recipes where the input contains the given IItemStack.
     *
     * @param input The input IItemStack.
     *
     * @docParam input <item:minecraft:iron_ingot>
     */
    @ZenCodeType.Method
    public void removeByInput(IItemStack input) {
        
        CraftTweakerAPI.apply(new ActionRemoveGenericRecipe(holder -> holder.value().getIngredients()
                .stream()
                .anyMatch(ingredient -> ingredient.test(input.getInternal()))));
    }
    
    /**
     * Removes all recipes with this name.
     *
     * @param name The recipe name to remove
     *
     * @deprecated Use {@link #removeByName(String...)} instead
     */
    @Deprecated(forRemoval = true)
    public void removeByName(String name) {
        
        CraftTweakerAPI.apply(new ActionRemoveGenericRecipeByName(name));
    }
    
    /**
     * Remove recipes based on Registry names
     *
     * @param names registry names of recipes to remove
     *
     * @docParam name "minecraft:furnace", "minecraft:bow"
     */
    @ZenCodeType.Method
    public void removeByName(String... names) {
        
        CraftTweakerAPI.apply(new ActionRemoveGenericRecipeByName(Arrays.stream(names)
                .map(ResourceLocation::new)
                .toArray(ResourceLocation[]::new)));
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
    public void removeByModid(String modId, Predicate<String> exclude) {
        
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
    public List<IRecipeManager<?>> getAllManagers() {
        
        return new ArrayList<>(RecipeTypeBracketHandler.getManagerInstances());
    }
    
}

