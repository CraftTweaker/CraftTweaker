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
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.op.IDataOps;
import com.blamejared.crafttweaker.api.data.visitor.DataToJsonStringVisitor;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.recipe.RecipeList;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.NameUtil;
import com.blamejared.crafttweaker.api.zencode.util.PositionUtil;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.CodePosition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Default interface for Registry based handlers as they can all remove recipes by ResourceLocation.
 *
 * @docParam this craftingTable
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.IRecipeManager")
@Document("vanilla/api/recipe/manager/IRecipeManager")
public interface IRecipeManager<T extends Recipe<?>> extends CommandStringDisplayable, Iterable<RecipeHolder<T>> {
    
    /**
     * Adds a recipe based on a provided IData. The provided IData should represent a DataPack json, this effectively allows you to register recipes for any DataPack supporting RecipeType systems.
     *
     * @param name    name of the recipe
     * @param mapData data representing the json file
     *
     * @docParam name "recipe_name"
     * @docParam mapData {
     * ingredient: <item:minecraft:gold_ore>,
     * result: <item:minecraft:cooked_porkchop>.registryName,
     * experience: 0.35 as float,
     * cookingtime:100
     * }
     */
    @ZenCodeType.Method
    default void addJsonRecipe(final String name, final MapData mapData) {
        
        final String fixedName = this.fixRecipeName(name);
        final ResourceLocation recipeTypeKey = this.getBracketResourceLocation();
        
        final IData requestedSerializer = mapData.getAt("type");
        final ResourceLocation serializerKey = requestedSerializer == null? recipeTypeKey : Util.make(() -> {
            try {
                return new ResourceLocation(requestedSerializer.getAsString());
            } catch (final ClassCastException | IllegalStateException | ResourceLocationException ex) {
                throw new IllegalArgumentException("Expected 'type' field to be a valid resource location", ex);
            }
        });
        
        final RecipeSerializer<T> serializer = BuiltInRegistries.RECIPE_SERIALIZER.getOptional(serializerKey)
                .map(GenericUtil::<RecipeSerializer<T>>uncheck)
                .orElseThrow(() -> {
                    if (requestedSerializer == null) {
                        return new IllegalArgumentException(
                                "Recipe Type '%s' does not have a Recipe Serializer of the same ID. Please specify a serializer manually using the 'type' field in the JSON object"
                                        .formatted(recipeTypeKey)
                        );
                    }

                    return new IllegalArgumentException("Recipe Serializer '%s' does not exist.".formatted(requestedSerializer));
                });
        
        final ResourceLocation recipeName = CraftTweakerConstants.rl(fixedName);
        final T recipe = Util.getOrThrow(serializer.codec().parse(IDataOps.INSTANCE, mapData), IllegalArgumentException::new);
        
        final RecipeType<?> recipeType = recipe.getType();
        if (recipeType != this.getRecipeType()) {
            throw new IllegalArgumentException(
                    "Recipe Serializer \"%s\" resulted in Recipe Type \"%s\" but expected Recipe Type \"%s\""
                            .formatted(
                                    BuiltInRegistries.RECIPE_SERIALIZER.getKey(recipe.getSerializer()),
                                    BuiltInRegistries.RECIPE_TYPE.getKey(recipeType),
                                    BuiltInRegistries.RECIPE_TYPE.getKey(this.getRecipeType())
                            )
            );
        }
        
        final RecipeHolder<T> holder = new RecipeHolder<>(recipeName, recipe);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, holder));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    default RecipeHolder<T> getRecipeByName(String name) {
        
        return getRecipeList().get(name);
    }
    
    @ZenCodeType.Method
    default List<RecipeHolder<T>> getRecipesByOutput(IIngredient output) {
        
        return getRecipeList().getRecipesByOutput(output);
    }
    
    @ZenCodeType.Method
    default List<RecipeHolder<T>> getRecipesMatching(Predicate<RecipeHolder<T>> predicate) {
        
        return getRecipeList().getRecipesMatching(predicate);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("allRecipes")
    default List<RecipeHolder<T>> getAllRecipes() {
        
        return getRecipeList().getAllRecipes();
    }
    
    /**
     * Returns a map of all known recipes.
     *
     * @return A Map of recipe name to recipe of all known recipes.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("recipeMap")
    default Map<ResourceLocation, RecipeHolder<T>> getRecipeMap() {
        
        return getRecipeList().getRecipes();
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
        
        CraftTweakerAPI.apply(new ActionRemoveRecipe<>(this, holder -> holder.value().getIngredients()
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
    default void removeByName(String... names) {
        
        CraftTweakerAPI.apply(new ActionRemoveRecipeByName<>(this, Arrays.stream(names)
                .map(ResourceLocation::new)
                .toArray(ResourceLocation[]::new)));
    }
    
    
    /**
     * Remove recipe based on Registry name modid
     *
     * @param modid modid of the recipes to remove
     *
     * @docParam modid "minecraft"
     */
    @ZenCodeType.Method
    default void removeByModid(String modid, @ZenCodeType.Optional("(name as string) as bool => false") Predicate<String> exclude) {
        
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
    default void removeByRegex(String regex, @ZenCodeType.Optional("(name as string) as bool => false") Predicate<String> exclude) {
        
        CraftTweakerAPI.apply(new ActionRemoveRecipeByRegex<>(this, regex, exclude));
    }
    
    /**
     * Removes all recipes that match the given predicate
     *
     * @param predicate a predicate of {@link RecipeHolder<T>} to test recipes against.
     *
     * @docParam predicate (holder) => "wool" in holder.id.path
     */
    @ZenCodeType.Method
    default void removeMatching(Predicate<RecipeHolder<T>> predicate) {
        
        CraftTweakerAPI.apply(new ActionRemoveRecipe<>(this, predicate));
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
    
    
    /**
     * Gets a {@link RecipeList} which can be used to change recipes for this manager.
     *
     * Changes made through a {@link RecipeList} are applied to all the places that vanilla keeps track of recipes.
     *
     * @return A {@link RecipeList} for this manager.
     */
    default RecipeList<T> getRecipeList() {
        
        return new RecipeList<>(getRecipeType(), getRecipes(), CraftTweakerAPI.getAccessibleElementsProvider()
                .accessibleRecipeManager()
                .crafttweaker$getByName());
    }
    
    /**
     * Gets the recipes for this RecipeManager.
     *
     * This should only be used to view recipes, if you need to change the map, use {@link #getRecipeList()}
     *
     * In the future this method will either be removed or made to return an immutable map.
     *
     * @return A map of name to recipe for the manager type.
     */
    default Map<ResourceLocation, RecipeHolder<T>> getRecipes() {
        
        return GenericUtil.uncheck(
                CraftTweakerAPI.getAccessibleElementsProvider()
                        .accessibleRecipeManager()
                        .crafttweaker$getRecipes()
                        .computeIfAbsent(getRecipeType(), key -> new HashMap<>())
        );
    }
    
    /**
     * Gets the resource location to get this Recipe handler
     * Default just looks up the Recipe Type key from the registry
     */
    default ResourceLocation getBracketResourceLocation() {
        
        return BuiltInRegistries.RECIPE_TYPE.getKey(getRecipeType());
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
                (fixed, mistakes) -> CommonLoggers.api().warn(
                        "{}Invalid recipe name '{}', mistakes:\n{}\nNew recipe name: {}",
                        position == CodePosition.UNKNOWN ? "" : position + ": ",
                        name,
                        String.join("\n", mistakes),
                        fixed
                )
        );
    }
    
    /**
     * Create a new {@link ResourceLocation} from the given name, the name is checked for common errors that are often ran into with recipe names, and logs them.
     * <p>
     * NOTE: The returned location is under the {@value CraftTweakerConstants#MOD_ID} namespace
     *
     * @param name name to check
     *
     * @return fixed name
     *
     * @since 15.0.0
     */
    default ResourceLocation fixRecipeId(String name) {
        
        return fixRecipeId(name, CraftTweakerConstants::rl);
    }
    
    /**
     * Create a new {@link ResourceLocation} from the given name, the name is checked for common errors that are often ran into with recipe names, and logs them.
     *
     * @param name    name to check
     * @param idMaker a function to create a {@link ResourceLocation} from the name
     *
     * @return fixed name
     *
     * @since 15.0.0
     */
    default ResourceLocation fixRecipeId(String name, Function<String, ResourceLocation> idMaker) {
        
        return idMaker.apply(fixRecipeName(name));
    }
    
    /**
     * Creates a {@link RecipeHolder} to hold the given recipe.
     *
     * @param id     The id of the recipe
     * @param recipe The recipe to hold
     *
     * @return a new {@link RecipeHolder}
     */
    default RecipeHolder<T> createHolder(ResourceLocation id, T recipe) {
        
        return new RecipeHolder<>(id, recipe);
    }
    
    @Override
    default @NotNull Iterator<RecipeHolder<T>> iterator() {
        
        return getAllRecipes().iterator();
    }
    
}
