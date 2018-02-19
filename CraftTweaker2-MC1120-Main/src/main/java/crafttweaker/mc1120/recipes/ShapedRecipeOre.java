package crafttweaker.mc1120.recipes;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.recipes.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;

/**
 * @author Stan
 */
@Deprecated
public class ShapedRecipeOre extends ShapedOreRecipe implements IMTRecipe {
    
    private final ShapedRecipe recipe;
    
    public ShapedRecipeOre(ResourceLocation name, Object[] contents, ShapedRecipe recipe) {
        super(name, getItemStack(recipe.getOutput()), contents);
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
    public ItemStack getRecipeOutput() {
        return CraftTweakerMC.getItemStack(recipe.getOutput());
    }
    
    @Override
    public ICraftingRecipe getRecipe() {
        return recipe;
    }
}