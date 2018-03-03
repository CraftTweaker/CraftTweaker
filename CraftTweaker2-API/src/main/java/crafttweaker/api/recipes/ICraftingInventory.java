package crafttweaker.api.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * Crafting inventory interface. The crafting inventory is the 2x2 grid in the
 * player inventory or the 3x3 grid in the crafting bench.
 *
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.recipes.ICraftingInventory")
@ZenRegister
public interface ICraftingInventory {
    
    /**
     * Gets the player owning this inventory.
     *
     * @return inventory player
     */
    @ZenGetter("player")
    IPlayer getPlayer();
    
    /**
     * Gets the size of this inventory.
     *
     * @return inventory size
     */
    @ZenGetter("size")
    int getSize();
    
    /**
     * Gets the width of this inventory.
     *
     * @return inventory width
     */
    @ZenGetter("width")
    int getWidth();
    
    /**
     * Gets the height of this inventory.
     *
     * @return inventory height
     */
    @ZenGetter("height")
    int getHeight();
    
    /**
     * Gets the number of stacks that are actually filled in.
     *
     * @return effective stack count
     */
    @ZenGetter("stackCount")
    int getStackCount();
    
    /**
     * Gets the stack at the given location.
     *
     * @param i stack index
     *
     * @return stack contents, or null
     */
    @ZenMethod
    IItemStack getStack(int i);
    
    /**
     * Gets the stack at the given position. The top left stack is position (0,
     * 0).
     *
     * @param row       stack row (from top downwards)
     * @param column    stack column (from left to right)
     *
     * @return item stack, or null if there is no item stack at that position
     */
    @ZenMethod
    IItemStack getStack(int row, int column);
    
    /**
     * Sets the stack at the given position. The top left stack is position (0,
     * 0).
     * @param row        stack row (from top downwards)
     * @param column     stack column (from left to right)
     * @param stack      item stack to be set, or null to clear the stack at that position
     */
    @ZenMethod
    void setStack(int row, int column, IItemStack stack);
    
    /**
     * Sets the stack at the given index.
     *
     * @param i     stack index
     * @param stack item stack to be set, or null to clear the stack at that index
     *
     */
    @ZenMethod
    void setStack(int i, IItemStack stack);
    
    @ZenGetter("items")
    IItemStack[][] getItems();
    
    @ZenGetter("itemArray")
    IItemStack[] getItemArray();
    
    Object getInternal();
}
