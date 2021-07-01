package com.blamejared.crafttweaker.impl_native.inventory;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashSet;
import java.util.List;

@ZenRegister
@Document("vanilla/api/inventory/IInventory")
@NativeTypeRegistration(value = IInventory.class, zenCodeName = "crafttweaker.api.inventory.IInventory")
public class ExpandIInventory {
    
    /**
     * Gets how many slots this inventory has.
     *
     * Example:
     * A hopper will return `5`
     *
     * @return The amount of slots this inventory has.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("inventorySize")
    public static int getInventorySize(IInventory internal) {
        
        return internal.getSizeInventory();
    }
    
    /**
     * Checks if this inventory is empty.
     *
     * @return True if this inventory is empty. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isEmpty")
    public static boolean isEmpty(IInventory internal) {
        
        return internal.isEmpty();
    }
    
    /**
     * Gets the IItemStack in the given slot.
     *
     * @param index The index to get the stack from.
     *
     * @return The IItemStack in the slot.
     *
     * @docParam index 2
     */
    @ZenCodeType.Method
    public static IItemStack getStackInSlot(IInventory internal, int index) {
        
        return new MCItemStack(internal.getStackInSlot(index));
    }
    
    /**
     * Decreases the stack size of the stack in the given slot by the given count.
     *
     * @param index The slot index to decrement.
     * @param count How much should be removed.
     *
     * @return A new stack with how much was removed.
     *
     * @docParam index 2
     * @docParam count 2
     */
    @ZenCodeType.Method
    public static IItemStack decrStackSize(IInventory internal, int index, @ZenCodeType.OptionalInt(1) int count) {
        
        return new MCItemStack(internal.decrStackSize(index, count));
    }
    
    /**
     * Removes the IItemStack from the given slot and returns it.
     *
     * @param index The slot index to remove.
     *
     * @return The removed stack from the slot.
     *
     * @docParam index 2
     */
    @ZenCodeType.Method
    public static IItemStack removeStackFromSlot(IInventory internal, int index) {
        
        return new MCItemStack(internal.removeStackFromSlot(index));
    }
    
    /**
     * Sets the contents of the given slot to the given IItemStack.
     *
     * @param index The slot index to set.
     * @param stack The IItemStack to put in the slot.
     *
     * @docParam index 2
     * @docParam stack <item:minecraft:diamond>
     */
    @ZenCodeType.Method
    public static void setInventorySlotContents(IInventory internal, int index, IItemStack stack) {
        
        internal.setInventorySlotContents(index, stack.getInternal());
    }
    
    /**
     * Gets the max stack size that is allowed in this inventory.
     *
     * This is nearly always 64, but some inventories like the Beacon and Compostor have a limit of 1.
     *
     * @return The max stack size allowed in the inventory.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("inventoryStackLimit")
    public static int getInventoryStackLimit(IInventory internal) {
        
        return internal.getInventoryStackLimit();
    }
    
    /**
     * Used by tile entities to ensure that chunks are up to date when they are saved to disk.
     */
    @ZenCodeType.Method
    public static void markDirty(IInventory internal) {
        
        internal.markDirty();
    }
    
    /**
     * Checks if this IInventory can be used by the player.
     *
     * @param player The player to check if they can use this inventory.
     *
     * @return True if the player can use this inventory. False otherwise.
     *
     * @docParam player player
     */
    @ZenCodeType.Method
    public static boolean isUsableByPlayer(IInventory internal, PlayerEntity player) {
        
        return internal.isUsableByPlayer(player);
    }
    
    /**
     * Marks the inventory as opened, this is used by chests and barrels to determine
     * if they should have an open texture / model, but other inventories may use it in a different way.
     *
     * @param player The player that opened the inventory.
     *
     * @docParam player player
     */
    @ZenCodeType.Method
    public static void openInventory(IInventory internal, PlayerEntity player) {
        
        internal.openInventory(player);
    }
    
    /**
     * Marks the inventory as closed, this is used by chests and barrels to determine
     * if they should have a closed texture / model, but other inventories may use it in a different way.
     *
     * @param player The player that opened the inventory.
     *
     * @docParam player player
     */
    @ZenCodeType.Method
    public static void closeInventory(IInventory internal, PlayerEntity player) {
        
        internal.closeInventory(player);
    }
    
    /**
     * Checks if the given stack is valid for the given slot index.
     *
     * @param index The slot index to check.
     * @param stack The stack to check.
     *
     * @return True if the stack is valid. False otherwise.
     *
     * @docParam index 2
     * @docParam stack <item:minecraft:dirt>
     */
    @ZenCodeType.Method
    public static boolean isItemValidForSlot(IInventory internal, int index, IItemStack stack) {
        
        return internal.isItemValidForSlot(index, stack.getInternal());
    }
    
    /**
     * Counts how many of the given Item is in this inventory.
     *
     * NOTE: This does not work for IItemStacks, so all NBT will be ignored.
     *
     * @param item The Item to look for.
     *
     * @return The amount of the Item in this inventory.
     *
     * @docParam item <item:minecraft:dirt>
     */
    @ZenCodeType.Method
    public static int count(IInventory internal, Item item) {
        
        return internal.count(item);
    }
    
    /**
     * Checks if this inventory has any of the given Items.
     *
     * NOTE: This does not work for IItemStacks, so all NBT will be ignored.
     *
     * @param list The Items to look for.
     *
     * @return True if this inventory has any of the items. False otherwise.
     *
     * @docParam list [<item:minecraft:diamond>]
     */
    @ZenCodeType.Method
    public static boolean hasAny(IInventory internal, List<Item> list) {
        
        return internal.hasAny(new HashSet<>(list));
    }
    
    /**
     * Clears this inventory of all items.
     */
    @ZenCodeType.Method
    public static void clear(IInventory internal) {
        
        internal.clear();
    }
    
}
