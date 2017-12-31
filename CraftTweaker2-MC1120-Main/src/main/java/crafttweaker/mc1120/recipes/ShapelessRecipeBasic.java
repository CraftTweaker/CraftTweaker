package crafttweaker.mc1120.recipes;

import crafttweaker.api.recipes.ICraftingRecipe;
import crafttweaker.api.recipes.IMTRecipe;
import crafttweaker.api.recipes.ShapelessRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;

/**
 * @author Stan
 */
public class ShapelessRecipeBasic extends ShapelessRecipes implements IMTRecipe {

    private final ShapelessRecipe recipe;

    public ShapelessRecipeBasic(String name, NonNullList<Ingredient> ingredients, ShapelessRecipe recipe) {
        super(name, getItemStack(recipe.getOutput()), ingredients);

        this.recipe = recipe;
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        return recipe.matches(MCCraftingInventory.get(inventory));
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        if (recipe.getCraftingResult(MCCraftingInventory.get(inventory)) != null) {
            return getItemStack(recipe.getCraftingResult(MCCraftingInventory.get(inventory))).copy();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ICraftingRecipe getRecipe() {
        return recipe;
    }
}
