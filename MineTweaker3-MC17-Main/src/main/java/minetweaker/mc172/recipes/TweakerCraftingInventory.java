/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.recipes;

import minetweaker.mc172.item.TweakerItemStack;
import minetweaker.minecraft.item.IItemStack;
import minetweaker.minecraft.recipes.ICraftingInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Stan
 */
public class TweakerCraftingInventory implements ICraftingInventory {
	private static final ThreadLocal<TweakerCraftingInventory> cache = new ThreadLocal<TweakerCraftingInventory>();
	
	public static TweakerCraftingInventory get(InventoryCrafting inventory) {
		if (cache.get() == null || cache.get().inventory != inventory) {
			TweakerCraftingInventory result = new TweakerCraftingInventory(inventory);
			cache.set(result);
			return result;
		} else {
			return cache.get();
		}
	}
	
	private final int width;
	private final int height;
	private final InventoryCrafting inventory;
	private final IItemStack[] stacks;
	
	private TweakerCraftingInventory(InventoryCrafting inventory) {
		this.inventory = inventory;
		width = height = (int) Math.sqrt(inventory.getSizeInventory());
		stacks = new IItemStack[width * height];
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			if (inventory.getStackInSlot(i) != null) {
				stacks[i] = new TweakerItemStack(inventory.getStackInSlot(i));
			}
		}
	}

	@Override
	public int getSize() {
		return width * height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public IItemStack getStack(int i) {
		return stacks[i];
	}

	@Override
	public IItemStack getStack(int x, int y) {
		return stacks[y * width + x];
	}

	@Override
	public void setStack(int x, int y, IItemStack stack) {
		stacks[y * width + x] = stack;
		inventory.setInventorySlotContents(y * width + x, (ItemStack) stack.getInternal());
	}

	@Override
	public void setStack(int i, IItemStack stack) {
		stacks[i] = stack;
		inventory.setInventorySlotContents(i, (ItemStack) stack.getInternal());
	}
}
