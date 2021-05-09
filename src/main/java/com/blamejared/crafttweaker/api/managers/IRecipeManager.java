package com.blamejared.crafttweaker.api.managers;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.zencode.impl.util.PositionUtil;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveAll;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipe;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByModid;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByName;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByOutput;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByRegex;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.managers.CTCraftingTableManager;
import com.blamejared.crafttweaker.impl.recipes.wrappers.WrapperRecipe;
import com.blamejared.crafttweaker.impl.util.NameUtils;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.CodePosition;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
        ResourceLocation recipeTypeKey = getBracketResourceLocation();
        
        if(recipeObject.has("type")) {
            ResourceLocation recipeSerializerKey;
            try {
                recipeSerializerKey = new ResourceLocation(recipeObject.get("type").getAsString());
            } catch(ClassCastException | IllegalStateException | ResourceLocationException ex) {
                throw new IllegalArgumentException("Expected \"type\" field to be a valid resource location.", ex);
            }
            if(!ForgeRegistries.RECIPE_SERIALIZERS.containsKey(recipeSerializerKey)) {
                throw new IllegalArgumentException("Recipe Serializer \"" + recipeSerializerKey + "\" does not exist.");
            }
        } else {
            if(ForgeRegistries.RECIPE_SERIALIZERS.containsKey(recipeTypeKey)) {
                recipeObject.addProperty("type", recipeTypeKey.toString());
            } else {
                throw new IllegalArgumentException("Recipe Type \"" + recipeTypeKey + "\" does not have a Recipe Serializer of the same ID."
                        + " Please specify a serializer manually using the \"type\" field in the JSON object.");
            }
        }
        IRecipe<?> iRecipe = RecipeManager.deserializeRecipe(new ResourceLocation(CraftTweaker.MODID, name), recipeObject);
        IRecipeType<?> recipeType = iRecipe.getType();
        if(recipeType != getRecipeType()) {
            throw new IllegalArgumentException("Recipe Serializer \"" + iRecipe.getSerializer().getRegistryName()
                    + "\" resulted in Recipe Type \"" + Registry.RECIPE_TYPE.getKey(recipeType)
                    + "\" but expected Recipe Type \"" + recipeTypeKey + "\".");
        }
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
    @ZenCodeType.Getter("allRecipes")
    default List<WrapperRecipe> getAllRecipes() {
        return getRecipes().values().stream().map(WrapperRecipe::new).collect(Collectors.toList());
    }
    
    /**
     * Returns a map of all known recipes.
     *
     * @return A Map of recipe name to recipe of all known recipes.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("recipeMap")
    default Map<ResourceLocation, WrapperRecipe> getRecipeMap() {
    
        return getRecipes().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new WrapperRecipe(entry
                        .getValue())));
    }
    
    /**
     * Remove a recipe based on it's output.
     *
     * @param output output of the recipe
     *
     * @docParam output <tag:items:minecraft:wool>
     */
    @ZenCodeType.Method
    default void removeRecipe(IIngredient output) {
        CraftTweakerAPI.apply(new ActionRemoveRecipeByOutput(this, output));
    }
    
    // This is only here for backwards compat, should be removed next breaking change
    /**
     * Removes a recipe based on it's output.
     *
     * @param output output of the recipe
     *
     * @docParam output <item:minecraft:glass>
     */
    @ZenCodeType.Method
    default void removeRecipe(IItemStack output){
        removeRecipe((IIngredient) output);
    }
    
    /**
     * Removes all recipes who's input contains the given IItemStack.
     *
     * @param input The input IItemStack.
     *
     * @docParam input <item:minecraft:ironingot>
     */
    @ZenCodeType.Method
    default void removeRecipeByInput(IItemStack input) {
        CraftTweakerAPI.apply(new ActionRemoveRecipe(this, iRecipe -> iRecipe.getIngredients().stream().anyMatch(ingredient -> ingredient.test(input.getInternal()))));
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
        return CTCraftingTableManager.recipeManager.recipes.computeIfAbsent(getRecipeType(), iRecipeType -> new HashMap<>());
    }
    
    /**
     * Checks if the given name is a valid ResourceLocation path, used to ensure recipe names are correct
     *
     * @param name name to check
     */
    default String validateRecipeName(String name) {
        return fixRecipeName(name);
    }
    
    /**
     * Fixes and logs some common errors that people run into with recipe names
     *
     * @param name name to check
     *
     * @return fixed name
     */
    default String fixRecipeName(String name) {
        CodePosition position = PositionUtil.getZCScriptPositionFromStackTrace();
        return NameUtils.fixing(
                name,
                (fixed, mistakes) -> CraftTweakerAPI.logWarning(
                        "%sInvalid recipe name '%s', mistakes:\n%s\nNew recipe name: %s",
                        position == null ? "" : position + ": ",
                        name,
                        String.join("\n", mistakes),
                        fixed
                )
        );
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
        return "<recipetype:" + getBracketResourceLocation() + ">";
    }
    
}
