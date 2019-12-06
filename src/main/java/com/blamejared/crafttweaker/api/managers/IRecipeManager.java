package com.blamejared.crafttweaker.api.managers;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveAll;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByModid;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByName;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByOutput;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByRegex;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.managers.CTCraftingTableManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Map;

/**
 * Default interface for Registry based handlers as they can all remove recipes by ResourceLocation.
 *
 * @docParam this craftingTable
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.registries.IRecipeManager")
@Document("vanilla/managers/IRecipeManager")
public interface IRecipeManager {
    
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
        validateRecipeName(name);
        if(!(data instanceof MapData)) {
            throw new IllegalArgumentException("Json recipe's IData should be a MapData!");
        }
        MapData mapData = (MapData) data;
        JsonObject recipeObject = JSON_RECIPE_GSON.fromJson(mapData.toJsonString(), JsonObject.class);
        if(recipeObject.has("type")) {
            if(!recipeObject.get("type").getAsString().equals(getRecipeType().toString()))
                throw new IllegalArgumentException("Cannot override recipe type! Given: \"" + recipeObject.get("type").getAsString() + "\", Expected: \"" + getRecipeType().toString() + "\"");
        } else {
            recipeObject.addProperty("type", getRecipeType().toString());
        }
        IRecipe<?> iRecipe = RecipeManager.deserializeRecipe(new ResourceLocation(CraftTweaker.MODID, name), recipeObject);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, iRecipe, ""));
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
        return CTCraftingTableManager.recipeManager.recipes.get(getRecipeType());
    }
    
    /**
     * Checks if the given name is a valid ResourceLocation path, used to ensure recipe names are correct
     *
     * @param name name to check
     */
    default void validateRecipeName(String name) {
        if(!name.chars().allMatch((ch) -> ch == 95 || ch == 45 || ch >= 97 && ch <= 122 || ch >= 48 && ch <= 57 || ch == 47 || ch == 46)) {
            throw new IllegalArgumentException("Given name does not fit the \"[a-z0-9/._-]\" regex! Name: \"" + name + "\"");
        }
    }
    
    @FunctionalInterface
    @ZenRegister
    interface RecipeFunctionSingle {
        
        IItemStack process(IItemStack usualOut, IItemStack inputs);
    }
    
    @FunctionalInterface
    @ZenRegister
    interface RecipeFunctionArray {
        
        IItemStack process(IItemStack usualOut, IItemStack[] inputs);
    }
    
    @FunctionalInterface
    @ZenRegister
    interface RecipeFunctionMatrix {
        
        IItemStack process(IItemStack usualOut, IItemStack[][] inputs);
    }
    
}
