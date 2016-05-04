/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.recipes;

import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;
import minetweaker.api.recipes.ShapedRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 *
 * @author Stan
 */
public class ShapedRecipeOre extends ShapedOreRecipe {
    private final ShapedRecipe recipe;

    public ShapedRecipeOre(Object[] contents, ShapedRecipe recipe) {
        super(getItemStack(recipe.getOutput()), contents);

        this.recipe = recipe;
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        return recipe.matches(MCCraftingInventory.get(inventory));
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        if(recipe !=null){
            if(recipe.getCraftingResult(MCCraftingInventory.get(inventory))!=null){
                return getItemStack(recipe.getCraftingResult(MCCraftingInventory.get(inventory))).copy();
            }
        }
        return null;
    }
}
