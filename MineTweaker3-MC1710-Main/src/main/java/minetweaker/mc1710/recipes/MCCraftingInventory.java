/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.recipes;

import java.util.List;

import minetweaker.mc1710.player.MCPlayer;
import minetweaker.mc1710.util.MineTweakerHacks;
import minetweaker.api.item.IItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getIItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getIPlayer;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;
import minetweaker.api.player.IPlayer;
import minetweaker.api.recipes.ICraftingInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Stan
 */
public class MCCraftingInventory implements ICraftingInventory {
	private static final ThreadLocal<MCCraftingInventory> cache = new ThreadLocal<MCCraftingInventory>();
	private static final ThreadLocal<MCCraftingInventory> cache2 = new ThreadLocal<MCCraftingInventory>();

	public static MCCraftingInventory get(InventoryCrafting inventory) {
		if (cache.get() == null || cache.get().inventory != inventory) {
			MCCraftingInventory result = new MCCraftingInventory(inventory);
			cache.set(result);
			return result;
		} else {
			MCCraftingInventory result = cache.get();
			result.update();
			return result;
		}
	}

	public static MCCraftingInventory get(IInventory inventory, EntityPlayer player) {
		if (cache2.get() == null || cache2.get().inventory != inventory || cache2.get().player != player) {
			MCCraftingInventory result = new MCCraftingInventory(inventory, player);
			cache2.set(result);
			return result;
		} else {
			MCCraftingInventory result = cache2.get();
			result.update();
			return result;
		}
	}

	private int width;
	private int height;
	private final IInventory inventory;
	private IItemStack[] stacks;
	private ItemStack[] original;
	private int stackCount;
	private final IPlayer player;
	private final EntityPlayer playerOrig;

	private MCCraftingInventory(InventoryCrafting inventory) {
		this.inventory = inventory;
		width = height = (int) Math.sqrt(inventory.getSizeInventory());
		stacks = new IItemStack[width * height];
		original = new ItemStack[stacks.length];
		stackCount = 0;
		update();

		Container container = MineTweakerHacks.getCraftingContainer(inventory);
		if (container != null) {
			List<Slot> slots = container.inventorySlots;
			if (!slots.isEmpty() && slots.get(0) instanceof SlotCrafting) {
				SlotCrafting slotCrafting = (SlotCrafting) slots.get(0);
				playerOrig = MineTweakerHacks.getCraftingSlotPlayer(slotCrafting);
				player = getIPlayer(playerOrig);
			} else {
				playerOrig = null;
				player = null;
			}
		} else {
			playerOrig = null;
			player = null;
		}
	}

	public MCCraftingInventory(IInventory inventory, EntityPlayer player) {
		this.inventory = inventory;
		width = height = (int) Math.sqrt(inventory.getSizeInventory());
		stacks = new IItemStack[width * height];
		original = new ItemStack[stacks.length];
		stackCount = 0;
		update();

		playerOrig = player;
		this.player = player == null ? null : new MCPlayer(player);
	}

	private void update() {
		if (inventory.getSizeInventory() != original.length) {
			width = height = (int) Math.sqrt(inventory.getSizeInventory());
			stacks = new IItemStack[inventory.getSizeInventory()];
			original = new ItemStack[stacks.length];
			stackCount = 0;
		}

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			if (changed(i)) {
				// System.out.println("Slot " + i + " changed");
				original[i] = inventory.getStackInSlot(i);
				if (inventory.getStackInSlot(i) != null) {
					if (stacks[i] == null)
						stackCount++;
					stacks[i] = getIItemStack(original[i]);
				} else {
					if (stacks[i] != null)
						stackCount--;
					stacks[i] = null;
				}
			}
		}
		// System.out.println("Num stack count: " + stackCount);
	}

	@Override
	public IPlayer getPlayer() {
		return player;
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
	public int getStackCount() {
		return stackCount;
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
		// System.out.println("SetStack(" + x + ", " + y + ") " + stack);

		int ix = y * width + x;
		if (stack != stacks[ix]) {
			if (stack == null) {
				stackCount--;
				inventory.setInventorySlotContents(ix, null);
			} else {
				inventory.setInventorySlotContents(ix, getItemStack(stack));

				if (stacks[ix] == null) {
					stackCount++;
				}
			}
			stacks[ix] = stack;
		}
	}

	@Override
	public void setStack(int i, IItemStack stack) {
		// System.out.println("SetStack(" + i + ") " + stack);

		if (stack != stacks[i]) {
			if (stack == null) {
				stackCount--;
				inventory.setInventorySlotContents(i, null);
			} else {
				inventory.setInventorySlotContents(i, getItemStack(stack));

				if (stacks[i] == null) {
					stackCount++;
				}
			}
			stacks[i] = stack;
		}
	}

	private boolean changed(int i) {
		if (original[i] != inventory.getStackInSlot(i))
			return true;
		if (original[i] != null && stacks[i].getAmount() != original[i].stackSize)
			return true;

		return false;
	}
}
