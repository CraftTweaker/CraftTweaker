/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc18.recipes;

import minetweaker.api.recipes.ShapelessRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;

/**
 * @author Stan
 */
public class ShapelessRecipeAdvanced implements IRecipe{
    private final ShapelessRecipe recipe;

    public ShapelessRecipeAdvanced(ShapelessRecipe recipe){
        this.recipe = recipe;
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world){
        return recipe.matches(MCCraftingInventory.get(inventory));
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory){
        return getItemStack(recipe.getCraftingResult(MCCraftingInventory.get(inventory))).copy();
    }

    @Override
    public int getRecipeSize(){
        return recipe.getSize();
    }

    @Override
    public ItemStack getRecipeOutput(){
        return getItemStack(recipe.getOutput());
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv){
        return new ItemStack[]{};
    }
}
