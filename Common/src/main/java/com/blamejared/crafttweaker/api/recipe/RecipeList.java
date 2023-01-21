package com.blamejared.crafttweaker.api.recipe;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * This class acts as a wrapper for the (currently) two recipe maps that vanilla stores recipes in:
 * The {@code recipes} map, which is used for actual recipe lookups.
 * The {@code byName} map, which is used when you have a recipe id, and want to get the recipe for that id, such as in the recipe book.
 *
 * @param <T> The base type of {@link Recipe} in this list.
 */
public class RecipeList<T extends Recipe<?>> {
    
    private final RecipeType<T> recipeType;
    private final Map<ResourceLocation, T> recipes;
    private final Map<ResourceLocation, Recipe<?>> byName;
    
    private final Map<ResourceLocation, T> unmodifiableRecipes;
    private final Map<ResourceLocation, Recipe<?>> unmodifiableByName;
    
    public RecipeList(RecipeType<T> recipeType, Map<ResourceLocation, T> recipes, Map<ResourceLocation, Recipe<?>> byName) {
        
        this.recipeType = recipeType;
        this.recipes = recipes;
        this.byName = byName;
        this.unmodifiableRecipes = Collections.unmodifiableMap(recipes);
        this.unmodifiableByName = Collections.unmodifiableMap(byName);
    }
    
    /**
     * Gets recipes based on the output item, checked against the given IIngredient.
     *
     * @param output The output of the recipes to get.
     *
     * @return A List of recipes who's output matches the given IIngredient.
     */
    public List<T> getRecipesByOutput(IIngredient output) {
        
        return getRecipes().values()
                .stream()
                .filter(iRecipe -> output.matches(IItemStack.ofMutable(iRecipe.getResultItem())))
                .toList();
    }
    
    /**
     * Gets a view of the recipes in this RecipeList.
     *
     * @return A view of the recipes in this RecipeList.
     */
    public List<T> getAllRecipes() {
        
        return new ArrayList<>(getRecipes().values());
    }
    
    /**
     * Gets a recipe based on the given Id.
     *
     * @param id The resource location Id of the recipe.
     *
     * @return Teh found recipe or null if not found.
     */
    public T get(ResourceLocation id) {
        
        return getRecipes().get(id);
    }
    
    /**
     * Gets a recipe based on the given Id.
     *
     * @param id The string Id of the recipe.
     *
     * @return Teh found recipe or null if not found.
     */
    public T get(String id) {
        
        return get(ResourceLocation.tryParse(id));
    }
    
    /**
     * Checks if this list contains a recipe with the given key.
     *
     * @param id The resource location Id of the recipe.
     *
     * @return True if the list has the recipe, false otherwise.
     */
    public boolean has(ResourceLocation id) {
        
        return getRecipes().containsKey(id);
    }
    
    /**
     * Checks if this list contains a recipe with the given key.
     *
     * @param id The string Id of the recipe.
     *
     * @return True if the list has the recipe, false otherwise.
     */
    public boolean has(String id) {
        
        return has(ResourceLocation.tryParse(id));
    }
    
    /**
     * Adds the given recipe to this list.
     *
     * @param id     The Id of the recipe.
     * @param recipe The recipe to add.
     */
    public void add(ResourceLocation id, T recipe) {
        
        if(getByName().containsKey(recipe.getId())) {
            // TODO("Or CommonLoggers.zenCode()?")
            CommonLoggers.own().warn(
                    "A recipe with the name '{}' already exists and will be overwritten: this is most likely an error in your scripts",
                    recipe.getId().getPath()
            );
        }
        
        recipes.put(id, recipe);
        byName.put(id, recipe);
    }
    
    
    /**
     * Remove the recipe with the given Id.
     *
     * @param id The Id of the recipe to remove.
     */
    public void remove(ResourceLocation id) {
        
        recipes.remove(id);
        byName.remove(id);
    }
    
    /**
     * Removes recipes that pass the given recipe Predicate
     *
     * @param recipePredicate The predicate to check the recipes against.
     */
    public void removeByRecipeTest(Predicate<T> recipePredicate) {
        
        Iterator<ResourceLocation> iterator = recipes.keySet().iterator();
        
        while(iterator.hasNext()) {
            ResourceLocation next = iterator.next();
            T recipe = recipes.get(next);
            if(recipePredicate.test(recipe)) {
                byName.remove(next);
                iterator.remove();
            }
        }
    }
    
    /**
     * Removes recipes that pass the given id Predicate.
     *
     * @param idPredicate The predicate to check the recipe id against.
     */
    public void removeByIdTest(Predicate<ResourceLocation> idPredicate) {
        
        removeByIdTest(idPredicate, s -> false);
    }
    
    /**
     * Removes recipes that pass the given id Predicate.
     *
     * @param idPredicate The predicate to check the recipe id against.
     * @param exclusions  A predicate to exclude certain recipes from removal.
     */
    public void removeByIdTest(Predicate<ResourceLocation> idPredicate, Predicate<String> exclusions) {
        
        Iterator<ResourceLocation> iterator = recipes.keySet().iterator();
        
        while(iterator.hasNext()) {
            ResourceLocation next = iterator.next();
            if(idPredicate.test(next) && !exclusions.test(next.getPath())) {
                byName.remove(next);
                iterator.remove();
            }
        }
    }
    
    /**
     * Removes all recipes in this list.
     */
    public void removeAll() {
        
        byName.keySet().removeAll(recipes.keySet());
        recipes.clear();
    }
    
    /**
     * Gets the recipe type that this list deals with.
     *
     * @return The recipe type that this list deals with.
     */
    public RecipeType<T> getRecipeType() {
        
        return recipeType;
    }
    
    /**
     * Gets an unmodifiable view of the recipe map in this list.
     *
     * @return An unmodifiable view of the recipe map in this list.
     */
    public Map<ResourceLocation, T> getRecipes() {
        
        return unmodifiableRecipes;
    }
    
    /**
     * Gets an unmodifiable view of the byName map in this list.
     *
     * @return An unmodifiable view of the byName map in this list.
     */
    public Map<ResourceLocation, Recipe<?>> getByName() {
        
        return unmodifiableByName;
    }
    
    /**
     * Gets how many recipes are in this list.
     *
     * @return How many recipes are in this list.
     */
    public int getSize() {
        
        return getRecipes().size();
    }
    
}
