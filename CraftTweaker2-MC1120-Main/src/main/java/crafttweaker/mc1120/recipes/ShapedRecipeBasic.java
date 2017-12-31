package crafttweaker.mc1120.recipes;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.recipes.ICraftingRecipe;
import crafttweaker.api.recipes.IMTRecipe;
import crafttweaker.api.recipes.ShapedRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;

/**
 * @author Stan
 */
public class ShapedRecipeBasic extends ShapedRecipes implements IMTRecipe {

    private final ShapedRecipe recipe;

    public ShapedRecipeBasic(String name, NonNullList<Ingredient> basicInputs, ShapedRecipe recipe) {
        super(name, recipe.getWidth(), recipe.getHeight(), basicInputs, getItemStack(recipe.getOutput()));

        this.recipe = recipe;
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        return recipe.matches(MCCraftingInventory.get(inventory));
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        IItemStack result = recipe.getCraftingResult(MCCraftingInventory.get(inventory));
        if (result == null) {
            return ItemStack.EMPTY;
        } else {
            return getItemStack(result).copy();
        }
    }

    @Override
    public ICraftingRecipe getRecipe() {
        return recipe;
    }
}
