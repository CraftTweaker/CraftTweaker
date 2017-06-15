package minetweaker.mc1120.recipes;

import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.recipes.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;

/**
 * @author Stan
 */
public class ShapedRecipeOre extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe, IMTRecipe {

    private final ShapedRecipe recipe;

    public ShapedRecipeOre(ResourceLocation name, Object[] contents, ShapedRecipe recipe) {
        //TODO change this class back
//        super(name, getItemStack(recipe.getOutput()), contents);
        this.recipe = recipe;
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        return recipe.matches(MCCraftingInventory.get(inventory));
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        if(recipe != null) {
            if(recipe.getCraftingResult(MCCraftingInventory.get(inventory)) != null) {
                return getItemStack(recipe.getCraftingResult(MCCraftingInventory.get(inventory))).copy();
            }
        }
        return ItemStack.EMPTY;
    }
    
    @Override
    public boolean canFit(int x, int y) {
        return x>= recipe.getWidth() && y >= recipe.getHeight();
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return MineTweakerMC.getItemStack(recipe.getOutput());
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
