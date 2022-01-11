package com.blamejared.crafttweaker.api.recipe.manager.base;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveAll;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByModid;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByName;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByOutput;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByRegex;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.visitor.DataToJsonStringVisitor;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.NameUtil;
import com.blamejared.crafttweaker.api.zencode.impl.util.PositionUtil;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.CodePosition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Default interface for Registry based handlers as they can all remove recipes by ResourceLocation.
 *
 * @docParam this craftingTable
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.IRecipeManager")
@Document("vanilla/api/recipe/manager/IRecipeManager")
public interface IRecipeManager<T extends Recipe<?>> extends CommandStringDisplayable {
    
    Gson JSON_RECIPE_GSON = new GsonBuilder().create();
    
    /**
     * Adds a recipe based on a provided IData. The provided IData should represent a DataPack json, this effectively allows you to register recipes for any DataPack supporting RecipeType systems.
     *
     * @param name name of the recipe
     * @param mapData data representing the json file
     *
     * @docParam name "recipe_name"
     * @docParam data {ingredient:{item:<item:minecraft:gold_ore>.registryName},result:<item:minecraft:cooked_porkchop>.registryName,experience:0.35 as float, cookingtime:100}
     */
    @ZenCodeType.Method
    default void addJsonRecipe(String name, MapData mapData) {
        
        name = fixRecipeName(name);
        JsonObject recipeObject = JSON_RECIPE_GSON.fromJson(mapData.accept(DataToJsonStringVisitor.INSTANCE), JsonObject.class);
        ResourceLocation recipeTypeKey = getBracketResourceLocation();
        
        if(recipeObject.has("type")) {
            ResourceLocation recipeSerializerKey;
            try {
                recipeSerializerKey = new ResourceLocation(recipeObject.get("type").getAsString());
            } catch(ClassCastException | IllegalStateException | ResourceLocationException ex) {
                throw new IllegalArgumentException("Expected 'type' field to be a valid resource location.", ex);
            }
            if(!Services.REGISTRY.recipeSerializers().containsKey(recipeSerializerKey)) {
                throw new IllegalArgumentException("Recipe Serializer '%s' does not exist.".formatted(recipeSerializerKey));
            }
        } else {
            if(Services.REGISTRY.recipeSerializers().containsKey(recipeTypeKey)) {
                recipeObject.addProperty("type", recipeTypeKey.toString());
            } else {
                throw new IllegalArgumentException("""
                        Recipe Type '%s' does not have a Recipe Serializer of the same ID. Please specify a serializer manually using the 'type' field in the JSON object.
                        """.formatted(recipeTypeKey));
            }
        }
        T iRecipe = (T) RecipeManager.fromJson(new ResourceLocation(CraftTweakerConstants.MOD_ID, name), recipeObject);
        RecipeType<?> recipeType = iRecipe.getType();
        if(recipeType != getRecipeType()) {
            throw new IllegalArgumentException("""
                    Recipe Serializer "%s" resulted in Recipe Type "%s" but expected Recipe Type "%s"
                    """.formatted(Services.REGISTRY.getRegistryKey(iRecipe.getSerializer()), Services.REGISTRY.recipeTypes()
                    .getKey(recipeType), recipeTypeKey));
        }
        CraftTweakerAPI.apply(new ActionAddRecipe<T>(this, iRecipe, ""));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    default T getRecipeByName(String name) {
        
        return getRecipes().get(new ResourceLocation(name));
    }
    
    @ZenCodeType.Method
    default List<T> getRecipesByOutput(IIngredient output) {
        
        return getRecipes().values()
                .stream()
                .filter(iRecipe -> output.matches(Services.PLATFORM.createMCItemStackMutable(iRecipe.getResultItem())))
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("allRecipes")
    default List<T> getAllRecipes() {
        
        return getRecipes().values().stream().toList();
    }
    
    /**
     * Returns a map of all known recipes.
     *
     * @return A Map of recipe name to recipe of all known recipes.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("recipeMap")
    default Map<ResourceLocation, T> getRecipeMap() {
        
        return getRecipes().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    
    /**
     * Remove a recipe based on it's output.
     *
     * @param output output of the recipe
     *
     * @docParam output <tag:items:minecraft:wool>
     */
    @ZenCodeType.Method
    default void remove(IIngredient output) {
        
        CraftTweakerAPI.apply(new ActionRemoveRecipeByOutput<>(this, output));
    }
    
    /**
     * Removes all recipes where the input contains the given IItemStack.
     *
     * @param input The input IItemStack.
     *
     * @docParam input <item:minecraft:iron_ingot>
     */
    @ZenCodeType.Method
    default void removeByInput(IItemStack input) {
        
        CraftTweakerAPI.apply(new ActionRemoveRecipe<>(this, iRecipe -> iRecipe.getIngredients()
                .stream()
                .anyMatch(ingredient -> ingredient.test(input.getInternal()))));
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
        
        CraftTweakerAPI.apply(new ActionRemoveRecipeByName<>(this, new ResourceLocation(name)));
    }
    
    
    /**
     * Remove recipe based on Registry name modid
     *
     * @param modid modid of the recipes to remove
     *
     * @docParam modid "minecraft"
     */
    @ZenCodeType.Method
    default void removeByModid(String modid, @ZenCodeType.Optional("(name) => false") Predicate<String> exclude) {
        
        CraftTweakerAPI.apply(new ActionRemoveRecipeByModid<>(this, modid, exclude));
    }
    
    /**
     * Remove recipe based on regex with an added exclusion check, so you can remove the whole mod besides a few specified.
     *
     * @param regex regex to match against
     *
     * @docParam regex "\\d_\\d"
     * @docParam exclude (name as string) => {return name == "orange_wool";}
     */
    @ZenCodeType.Method
    default void removeByRegex(String regex, @ZenCodeType.Optional("(name) => false") Predicate<String> exclude) {
        
        CraftTweakerAPI.apply(new ActionRemoveRecipeByRegex<>(this, regex, exclude));
    }
    
    /**
     * Remove all recipes in this registry
     */
    @ZenCodeType.Method
    default void removeAll() {
        
        CraftTweakerAPI.apply(new ActionRemoveAll<>(this));
    }
    
    /**
     * Gets the recipe type for the registry to remove from.
     *
     * @return IRecipeType of this registry.
     */
    RecipeType<T> getRecipeType();
    
    default Map<ResourceLocation, T> getRecipes() {
        
        return (Map<ResourceLocation, T>) CraftTweakerAPI.getAccessibleRecipeManager()
                .getRecipes()
                .computeIfAbsent(getRecipeType(), key -> new HashMap<>());
    }
    
    /**
     * Gets the resource location to get this Recipe handler
     * Default just looks up the Recipe Type key from the registry
     */
    default ResourceLocation getBracketResourceLocation() {
        
        return Services.REGISTRY.getRegistryKey(getRecipeType());
    }
    
    @Override
    default String getCommandString() {
        
        return "<recipetype:" + getBracketResourceLocation() + ">";
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
        return NameUtil.fixing(
                name,
                (fixed, mistakes) -> CraftTweakerAPI.LOGGER.warn(
                        "{}Invalid recipe name '{}', mistakes:\n{}\nNew recipe name: {}",
                        position == CodePosition.UNKNOWN ? "" : position + ": ",
                        name,
                        String.join("\n", mistakes),
                        fixed
                )
        );
    }
    
}
