package minetweaker.mc1112.recipes;

import minetweaker.api.item.IItemStack;
import minetweaker.api.recipes.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;

import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;

/**
 * @author Stan
 */
public class ShapedRecipeBasic extends ShapedRecipes implements IMTRecipe {

    private final ShapedRecipe recipe;

    public ShapedRecipeBasic(ItemStack[] basicInputs, ShapedRecipe recipe) {
        super(recipe.getWidth(), recipe.getHeight(), basicInputs, getItemStack(recipe.getOutput()));

        this.recipe = recipe;
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        return recipe.matches(MCCraftingInventory.get(inventory));
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        IItemStack result = recipe.getCraftingResult(MCCraftingInventory.get(inventory));
        if(result == null) {
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
