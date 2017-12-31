package crafttweaker.mc1120.recipes;

import crafttweaker.api.recipes.ICraftingRecipe;
import crafttweaker.api.recipes.IMTRecipe;
import crafttweaker.api.recipes.ShapedRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;

/**
 * @author Stan
 */
public class ShapedRecipeAdvanced extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe, IMTRecipe {

    private final ShapedRecipe recipe;

    public ShapedRecipeAdvanced(ShapedRecipe recipe) {
        this.recipe = recipe;

    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        return recipe.matches(MCCraftingInventory.get(inventory));
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        return getItemStack(recipe.getCraftingResult(MCCraftingInventory.get(inventory))).copy();
    }

    @Override
    public boolean canFit(int x, int y) {
        return x >= recipe.getWidth() && y >= recipe.getHeight();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return getItemStack(recipe.getOutput());
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return NonNullList.create();
    }

    @Override
    public ICraftingRecipe getRecipe() {
        return recipe;
    }

}
