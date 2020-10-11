package com.blamejared.crafttweaker.api.managers;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveAll;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByModid;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByName;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByOutput;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByRegex;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.managers.CTCraftingTableManager;
import com.blamejared.crafttweaker.impl.recipes.wrappers.WrapperRecipe;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Default interface for Registry based handlers as they can all remove recipes by ResourceLocation.
 *
 * @docParam this craftingTable
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.registries.IRecipeManager")
@Document("vanilla/api/managers/IRecipeManager")
public interface IRecipeManager extends CommandStringDisplayable {
    
    Gson JSON_RECIPE_GSON = new GsonBuilder().create();
    
    /**
     * Adds a recipe based on a provided IData. The provided IData should represent a DataPack JSON, this effectively allows you to register recipes for any DataPack supporting IRecipeType systems.
     *
     * @param name name of the recipe
     * @param data data representing the json file
     *
     * @docParam name "recipe_name"
     * @docParam data {ingredient:{item:<item:minecraft:gold_ore>.registryName},result:<item:minecraft:cooked_porkchop>.registryName,experience:0.35 as float, cookingtime:100}
     */
    @ZenCodeType.Method
    default void addJSONRecipe(String name, IData data) {
        name = validateRecipeName(name);
        if(!(data instanceof MapData)) {
            throw new IllegalArgumentException("Json recipe's IData should be a MapData!");
        }
        MapData mapData = (MapData) data;
        JsonObject recipeObject = JSON_RECIPE_GSON.fromJson(mapData.toJsonString(), JsonObject.class);
        String recipeTypeKey = getBracketResourceLocation().toString();
        
        if(recipeObject.has("type")) {
            if(!recipeObject.get("type").getAsString().equals(recipeTypeKey))
                throw new IllegalArgumentException("Cannot override recipe type! Given: \"" + recipeObject.get("type").getAsString() + "\", Expected: \"" + recipeTypeKey + "\"");
        } else {
            recipeObject.addProperty("type", recipeTypeKey);
        }
        IRecipe<?> iRecipe = RecipeManager.deserializeRecipe(new ResourceLocation(CraftTweaker.MODID, name), recipeObject);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, iRecipe, ""));
    }
    
    @ZenCodeType.Method
    default WrapperRecipe getRecipeByName(String name) {
        IRecipe<?> recipe = getRecipes().get(new ResourceLocation(name));
        if(recipe == null) {
            throw new IllegalArgumentException("No recipe found with name: \"" + name + "\" in type: \"" + getRecipeType().toString() + "\"");
        }
        return new WrapperRecipe(recipe);
    }
    
    @ZenCodeType.Method
    default List<WrapperRecipe> getRecipesByOutput(IIngredient output) {
        return getRecipes().values().stream().filter(iRecipe -> output.matches(new MCItemStackMutable(iRecipe.getRecipeOutput()))).map(WrapperRecipe::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    default List<WrapperRecipe> getAllRecipes() {
        return getRecipes().values().stream().map(WrapperRecipe::new).collect(Collectors.toList());
    }
    
    /**
     * Remove a recipe based on it's output.
     *
     * @param output output of the recipe
     *
     * @docParam output <item:minecraft:glass>
     */
    @ZenCodeType.Method
    default void removeRecipe(IItemStack output) {
        CraftTweakerAPI.apply(new ActionRemoveRecipeByOutput(this, output));
    }
    
    /**
     * Remove recipe based on Registry name
     *
     * @param name registry name of recipe to remove
     *
     * @docParam name "minecraft:furnace"
     */
    @ZenCodeType.Method
    default void removeByName(String name) {
        CraftTweakerAPI.apply(new ActionRemoveRecipeByName(this, new ResourceLocation(name)));
    }
    
    /**
     * Remove recipe based on Registry name modid
     *
     * @param modid modid of the recipes to remove
     *
     * @docParam modid "minecraft"
     */
    @ZenCodeType.Method
    default void removeByModid(String modid) {
        CraftTweakerAPI.apply(new ActionRemoveRecipeByModid(this, modid));
    }
    
    /**
     * Remove recipe based on Registry name modid with an added exclusion check, so you can remove the whole mod besides a few specified.
     *
     * @param modid   modid of the recipes to remove
     * @param exclude recipes to exlude from being removed.
     *
     * @docParam modid "minecraft"
     * @docParam exclude (name as string) => {return name == "orange_wool";}
     */
    @ZenCodeType.Method
    default void removeByModid(String modid, RecipeFilter exclude) {
        CraftTweakerAPI.apply(new ActionRemoveRecipeByModid(this, modid, exclude));
    }
    
    /**
     * Remove recipe based on regex
     *
     * @param regex regex to match against
     *
     * @docParam regex "\\d_\\d"
     */
    @ZenCodeType.Method
    default void removeByRegex(String regex) {
        CraftTweakerAPI.apply(new ActionRemoveRecipeByRegex(this, regex));
    }
    
    /**
     * Remove all recipes in this registry
     */
    @ZenCodeType.Method
    default void removeAll() {
        CraftTweakerAPI.apply(new ActionRemoveAll(this));
    }
    
    /**
     * Gets the recipe type for the registry to remove from.
     *
     * @return IRecipeType of this registry.
     */
    IRecipeType getRecipeType();
    
    /**
     * Gets all the vanilla IRecipes for this recipe type.
     *
     * @return Map of ResourceLocation to IRecipe for this recipe type.
     */
    default Map<ResourceLocation, IRecipe<?>> getRecipes() {
        return CTCraftingTableManager.recipeManager.recipes.getOrDefault(getRecipeType(), Collections.emptyMap());
    }
    
    /**
     * Checks if the given name is a valid ResourceLocation path, used to ensure recipe names are correct
     *
     * @param name name to check
     */
    default String validateRecipeName(String name) {
        name = fixRecipeName(name);
        if(!name.chars().allMatch((ch) -> ch == 95 || ch == 45 || ch >= 97 && ch <= 122 || ch >= 48 && ch <= 57 || ch == 47 || ch == 46)) {
            throw new IllegalArgumentException("Given name does not fit the \"[a-z0-9/._-]\" regex! Name: \"" + name + "\"");
        }
        return name;
    }
    
    /**
     * Fixes and logs some common errors that people run into with recipe names
     *
     * @param name name to check
     *
     * @return fixed name
     */
    default String fixRecipeName(String name) {
        String fixed = name;
        if(fixed.indexOf(':') >= 0) {
            String temp = fixed.replaceAll(":", ".");
            CraftTweakerAPI.logWarning("Invalid recipe name \"%s\", recipe names cannot have a \":\"! New recipe name: \"%s\"", fixed, temp);
            fixed = temp;
        }
        if(fixed.indexOf(' ') >= 0) {
            String temp = fixed.replaceAll(" ", ".");
            CraftTweakerAPI.logWarning("Invalid recipe name \"%s\", recipe names cannot have a \" \"! New recipe name: \"%s\"", fixed, temp);
            fixed = temp;
        }
        if(!fixed.toLowerCase().equals(fixed)) {
            String temp = fixed.toLowerCase();
            CraftTweakerAPI.logWarning("Invalid recipe name \"%s\", recipe names have to be lowercase! New recipe name: \"%s\"", fixed, temp);
            fixed = temp;
        }
        return fixed;
    }
    
    /**
     * Gets the resource location to get this Recipe handler
     * Default just looks up the Recipe Type key from the registry
     */
    default ResourceLocation getBracketResourceLocation() {
        return Registry.RECIPE_TYPE.getKey(getRecipeType());
    }
    
    @FunctionalInterface
    @ZenRegister
    @ZenCodeType.Name("crafttweaker.api.recipe.RecipeFilter")
    @Document("vanilla/api/recipe/RecipeFilter")
    interface RecipeFilter {
        @ZenCodeType.Method
        boolean test(String name);
    }
    
    @FunctionalInterface
    @ZenRegister
    @ZenCodeType.Name("crafttweaker.api.recipe.RecipeFunctionSingle")
    @Document("vanilla/api/recipe/RecipeFunctionSingle")
    interface RecipeFunctionSingle {
        @ZenCodeType.Method
        IItemStack process(IItemStack usualOut, IItemStack inputs);
    }
    
    @FunctionalInterface
    @ZenRegister
    @ZenCodeType.Name("crafttweaker.api.recipe.RecipeFunctionArray")
    @Document("vanilla/api/recipe/RecipeFunctionArray")
    interface RecipeFunctionArray {
        @ZenCodeType.Method
        IItemStack process(IItemStack usualOut, IItemStack[] inputs);
    }
    
    @FunctionalInterface
    @ZenRegister
    @ZenCodeType.Name("crafttweaker.api.recipe.RecipeFunctionMatrix")
    @Document("vanilla/api/recipe/RecipeFunctionMatrix")
    interface RecipeFunctionMatrix {
        @ZenCodeType.Method
        IItemStack process(IItemStack usualOut, IItemStack[][] inputs);
    }
    
    @Override
    default String getCommandString() {
        return "<recipetype:" + getRecipeType().toString() + ">";
    }
    
}
