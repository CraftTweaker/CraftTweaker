package com.blamejared.crafttweaker.impl.managers;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.actions.recipes.generic.ActionRemoveAllGenericRecipes;
import com.blamejared.crafttweaker.impl.actions.recipes.generic.ActionRemoveGenericRecipeByModId;
import com.blamejared.crafttweaker.impl.actions.recipes.generic.ActionRemoveGenericRecipeByName;
import com.blamejared.crafttweaker.impl.actions.recipes.generic.ActionRemoveGenericRecipeByOutput;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * This recipe manager allows you to perform removal actions over all recipe managers.
 * This is also the only place where you can specify the serializer type of an addJSON call directly.
 * You can access this manager by using the `recipes` global keyword.
 *
 * @docParam this recipes
 */
@ZenRegister
@Document("vanilla/api/managers/GenericRecipesManager")
@ZenCodeType.Name("crafttweaker.api.GenericRecipesManager")
public class GenericRecipesManager {
    
    
    @ZenCodeGlobals.Global("recipes")
    public static final GenericRecipesManager recipes = new GenericRecipesManager();
    
    /**
     * Add a new recipe based on the given recipe in a valid DataPack JSON format.
     *
     * Unlike the addJSONRecipe method in {@link IRecipeManager} you **must** set the type of the recipe within the JSON yourself.
     * This allows you to explicitly specify a serializer if their name does not match the RecipeType name.
     *
     * @param name The recipe's resource path
     * @param data The recipe in JSON format
     * @docParam name "recipe_name"
     * @docParam data {
     * type: "minecraft:smoking",
     * ingredient: <item:minecraft:gold_ore>,
     * result: <item:minecraft:cooked_porkchop>,
     * experience:0.35 as float,
     * cookingtime:100
     * }
     */
    @ZenCodeType.Method
    public void addJSONRecipe(String name, IData data) {
        if(!(data instanceof MapData)) {
            throw new IllegalArgumentException("Json recipe's IData should be a MapData!");
        }
        final JsonObject recipeObject = IRecipeManager.JSON_RECIPE_GSON.fromJson(data.toJsonString(), JsonObject.class);
        if(!recipeObject.has("type")) {
            throw new IllegalArgumentException("Serializer type missing!");
        }
        
        final ResourceLocation recipeName = new ResourceLocation(CraftTweaker.MODID, name);
        final IRecipe<?> result = RecipeManager.deserializeRecipe(recipeName, recipeObject);
        final RecipeManagerWrapper recipeManagerWrapper = new RecipeManagerWrapper(result.getType());
        CraftTweakerAPI.apply(new ActionAddRecipe(recipeManagerWrapper, result, null));
    }
    
    /**
     * Removes recipes by output
     *
     * @param output The recipe result
     * @docParam output <item:minecraft:iron_ingot>
     */
    @ZenCodeType.Method
    public void removeRecipe(IIngredient output) {
        CraftTweakerAPI.apply(new ActionRemoveGenericRecipeByOutput(output));
    }
    
    /**
     * Removes all recipes with this name.
     * It is possible that there exist more than one recipe with the same name, if they exist in different recipe types.
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
     * @docParam modId "crafttweaker"
     */
    @ZenCodeType.Method
    public void removeByModId(String modId) {
        removeByModId(modId, null);
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
     * @docParam modId "minecraft"
     * @docParam exclude (recipeName as string) => recipeName == "white_bed"
     */
    @ZenCodeType.Method
    public void removeByModId(String modId, IRecipeManager.RecipeFilter exclude) {
        CraftTweakerAPI.apply(new ActionRemoveGenericRecipeByModId(modId, exclude));
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
    
    /**
     * Returns a list of all known recipes.
     * The map's value is a list, since it may be possible that two types have a recipe with the same name.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("allRecipes")
    public Map<ResourceLocation, List<WrapperRecipe>> getAllRecipes() {
        return getAllManagers().stream()
                .flatMap(manager -> manager.getAllRecipes().stream())
                .collect(toMergedMap());
    }
    
    private Collector<WrapperRecipe, ?, Map<ResourceLocation, List<WrapperRecipe>>> toMergedMap() {
        final Function<WrapperRecipe, ResourceLocation> keyMapper = WrapperRecipe::getId;
        final Function<WrapperRecipe, List<WrapperRecipe>> valueMapper = Collections::singletonList;
        final BinaryOperator<List<WrapperRecipe>> mergerFunction = this::mergeLists;
        
        return Collectors.toMap(keyMapper, valueMapper, mergerFunction);
    }
    
    private List<WrapperRecipe> mergeLists(List<WrapperRecipe> left, List<WrapperRecipe> right) {
        final List<WrapperRecipe> result = new ArrayList<>(left);
        result.addAll(right);
        return result;
    }
}
