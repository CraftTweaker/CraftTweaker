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
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.op.IDataOps;
import com.blamejared.crafttweaker.api.data.visitor.DataToJsonStringVisitor;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.recipe.RecipeList;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.NameUtil;
import com.blamejared.crafttweaker.api.zencode.util.PositionUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.JsonObject;
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.CodePosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This recipe manager allows you to perform removal actions over all recipe managers.
 * You can access this manager by using the `recipes` global keyword.
 *
 * @docParam this recipes
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.GenericRecipesManager")
@Document("vanilla/api/recipe/manager/GenericRecipesManager")
public class GenericRecipesManager {
    
    @ZenCodeGlobals.Global("recipes")
    public static final GenericRecipesManager INSTANCE = new GenericRecipesManager();
    
    private static final Set<String> FORBIDDEN_MANAGERS = Set.of(
            CraftTweakerConstants.rl("scripts").toString()
    );
    
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
    public void addJsonRecipe(final String name, final MapData data) {
        
        final String fixedName = Util.make(() -> {
            final CodePosition position = PositionUtil.getZCScriptPositionFromStackTrace();
            return NameUtil.fixing(
                    name,
                    (fixed, mistakes) -> CommonLoggers.api().warn(
                            "{}Invalid recipe name '{}', mistakes:\n{}\nNew recipe name: {}",
                            position == CodePosition.UNKNOWN ? "" : position + ": ",
                            name,
                            String.join("\n", mistakes),
                            fixed
                    )
            );
        });
        
        final IData requestedSerializer = data.getAt("type");
        if(requestedSerializer == null) {
            throw new IllegalArgumentException("Serializer type missing!");
        }
        if(FORBIDDEN_MANAGERS.contains(requestedSerializer.getAsString().toLowerCase(Locale.ENGLISH))) {
            throw new IllegalArgumentException("Cannot add a recipe to the recipe type " + requestedSerializer.asString() + '!');
        }
        
        
        final ResourceLocation serializerKey = Util.make(() -> {
            try {
                return new ResourceLocation(requestedSerializer.getAsString());
            } catch (final ClassCastException | IllegalStateException | ResourceLocationException ex) {
                throw new IllegalArgumentException("Expected 'type' field to be a valid resource location", ex);
            }
        });
        
        final RecipeSerializer<?> serializer = BuiltInRegistries.RECIPE_SERIALIZER.getOptional(serializerKey)
                .orElseThrow(() -> new IllegalArgumentException("Recipe Serializer '%s' does not exist.".formatted(requestedSerializer)));
        
        final ResourceLocation recipeName = CraftTweakerConstants.rl(fixedName);
        final Recipe<?> recipe = Util.getOrThrow(serializer.codec().parse(IDataOps.INSTANCE, data), IllegalArgumentException::new);
        
        final IRecipeManager<?> manager = RecipeTypeBracketHandler.getOrDefault(recipe.getType());
        final RecipeHolder<?> holder = new RecipeHolder<>(recipeName, recipe);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(manager, GenericUtil.uncheck(holder)));
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
    
    @ZenCodeType.Method
    public List<RecipeHolder<Recipe<Container>>> getRecipesMatching(Predicate<RecipeHolder<Recipe<Container>>> predicate) {
        
        return getAllRecipes().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
    
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
     * Removes all recipes that match the given predicate
     *
     * @param predicate a predicate of {@link RecipeHolder<Recipe<Container>>} to test recipes against.
     *
     * @docParam predicate (holder) => "wool" in holder.id.path
     */
    @ZenCodeType.Method
    public void removeMatching(Predicate<RecipeHolder<Recipe<Container>>> predicate) {
        
        CraftTweakerAPI.apply(new ActionRemoveGenericRecipe(predicate));
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

