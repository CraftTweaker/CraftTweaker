package com.blamejared.crafttweaker.api.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByOutputInput;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Default interface for Registry based handlers as they can all remove recipes by ResourceLocation.
 *
 * @docParam this furnace
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.registries.ICookingRecipeManager")
@Document("vanilla/api/managers/ICookingRecipeManager")
public interface ICookingRecipeManager extends IRecipeManager {
    
    /**
     * Adds a recipe based on given params.
     *
     * Note: A `cookTime` of `0` will cause the recipe to never complete, it will burn and use fuel, but no progress will be made on the recipe, it needs to be at-least `1` or more.
     *
     * Saying that, if you would like to make a recipe that will never complete
     * (for example being able to give the player an infinitely burning furnace for whatever reason), you can
     * still use a `cookTime` of `0`.
     *
     * @param name     Name of the new recipe
     * @param output   IItemStack output of the recipe
     * @param input    IIngredient input of the recipe
     * @param xp       how much xp the player gets
     * @param cookTime how long it takes to cook
     *
     * @docParam name "wool2diamond"
     * @docParam output <item:minecraft:diamond>
     * @docParam input <tag:items:minecraft:wool>
     * @docParam xp 1.0
     * @docParam cookTime 30
     */
    @ZenCodeType.Method
    default void addRecipe(String name, IItemStack output, IIngredient input, float xp, int cookTime) {
        name = validateRecipeName(name);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, makeRecipe(name, output, input, xp, cookTime), ""));
    }
    
    /**
     * Removes a recipe based on it's output and input.
     *
     * @param output IItemStack output of the recipe.
     * @param input  IIngredient of the recipe to remove.
     *
     * @docParam output <item:minecraft:diamond>
     * @docParam input <tag:items:minecraft:wool>
     */
    @ZenCodeType.Method
    default void removeRecipe(IItemStack output, IIngredient input) {
        CraftTweakerAPI.apply(new ActionRemoveRecipeByOutputInput(this, output, input));
    }
    
    
    AbstractCookingRecipe makeRecipe(String name, IItemStack output, IIngredient input, float xp, int cookTime);
}
