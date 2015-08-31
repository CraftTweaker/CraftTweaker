/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.recipes;

import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Crafting inventory interface. The crafting inventory is the 2x2 grid in the
 * player inventory or the 3x3 grid in the crafting bench.
 * 
 * @author Stan Hebben
 */
@ZenClass("minetweaker.recipes.ICraftingInventory")
public interface ICraftingInventory {
	/**
	 * Gets the player owning this inventory.
	 * 
	 * @return inventory player
	 */
	@ZenGetter("player")
	public IPlayer getPlayer();

	/**
	 * Gets the size of this inventory.
	 * 
	 * @return inventory size
	 */
	@ZenGetter("size")
	public int getSize();

	/**
	 * Gets the width of this inventory.
	 * 
	 * @return inventory width
	 */
	@ZenGetter("width")
	public int getWidth();

	/**
	 * Gets the height of this inventory.
	 * 
	 * @return inventory height
	 */
	@ZenGetter("height")
	public int getHeight();

	/**
	 * Gets the number of stacks that are actually filled in.
	 * 
	 * @return effective stack count
	 */
	@ZenGetter("stackCount")
	public int getStackCount();

	/**
	 * Gets the stack at the given location.
	 * 
	 * @param i stack index
	 * @return stack contents, or null
	 */
	@ZenMethod
	public IItemStack getStack(int i);

	/**
	 * Gets the stack at the given position. The top left stack is position (0,
	 * 0).
	 * 
	 * @param x stack x position
	 * @param y stack y position
	 * @return item stack, or null if there is no item stack at that position
	 */
	@ZenMethod
	public IItemStack getStack(int x, int y);

	/**
	 * Sets the stack at the given position. The top left stack is position (0,
	 * 0).
	 * 
	 * @param x stack x position
	 * @param y stack y position
	 * @param stack item stack to be set, or null to clear the stack at that
	 *        position
	 */
	@ZenMethod
	public void setStack(int x, int y, IItemStack stack);

	/**
	 * Sets the stack at the given index.
	 * 
	 * @param i stack index
	 * @param stack item stack to be set, or null to clear the stack at that
	 *        index
	 */
	@ZenMethod
	public void setStack(int i, IItemStack stack);
}
