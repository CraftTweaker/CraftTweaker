package minetweaker.mc1112.recipes;

import minetweaker.api.recipes.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

import java.util.Arrays;

import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;

/**
 * @author Stan
 */
public class ShapelessRecipeBasic extends ShapelessRecipes implements IMTRecipe {
    
    private final ShapelessRecipe recipe;
    
    public ShapelessRecipeBasic(ItemStack[] ingredients, ShapelessRecipe recipe) {
        super(getItemStack(recipe.getOutput()), Arrays.asList(ingredients));
        
        this.recipe = recipe;
    }
    
    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        return recipe.matches(MCCraftingInventory.get(inventory));
    }
    
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        if(recipe.getCraftingResult(MCCraftingInventory.get(inventory)) != null) {
            return getItemStack(recipe.getCraftingResult(MCCraftingInventory.get(inventory))).copy();
        }
        return ItemStack.EMPTY;
    }
    
    @Override
    public ICraftingRecipe getRecipe() {
        return recipe;
    }
}
