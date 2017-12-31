package crafttweaker.mc1120.recipes;

import crafttweaker.api.recipes.ICraftingRecipe;
import crafttweaker.api.recipes.IMTRecipe;
import crafttweaker.api.recipes.ShapelessRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;

/**
 * @author Stan
 */
public class ShapelessRecipeOre extends ShapelessOreRecipe implements IMTRecipe {

    private final ShapelessRecipe recipe;

    public ShapelessRecipeOre(ResourceLocation group, Object[] ingredients, ShapelessRecipe recipe) {
        super(group, getItemStack(recipe.getOutput()), ingredients);

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
    public ICraftingRecipe getRecipe() {
        return recipe;
    }
}
