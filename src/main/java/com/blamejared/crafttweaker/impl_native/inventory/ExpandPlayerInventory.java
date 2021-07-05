package com.blamejared.crafttweaker.impl_native.inventory;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/inventory/PlayerInventory")
@NativeTypeRegistration(value = PlayerInventory.class, zenCodeName = "crafttweaker.api.inventory.PlayerInventory")
public class ExpandPlayerInventory {
    
    
    /**
     * Gets the currently held item by the player.
     *
     * @return The currently held stack by the player.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("currentItem")
    public static IItemStack getCurrentItem(PlayerInventory internal) {
        
        return new MCItemStack(internal.getCurrentItem());
    }
    
    /**
     * Gets the first slot in the inventory that is empty.
     *
     * If no slot is found, it returns `-1`.
     *
     * @return The found slot or `-1` if no slot is found.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("firstEmptyStack")
    public static int getFirstEmptyStack(PlayerInventory internal) {
        
        return internal.getFirstEmptyStack();
    }
    
    /**
     * Checks if a stack can be stored in the player's inventory. It first tries to place it in the selected slot in the player's hotbar,
     * then the offhand slot, then any available/empty slot in the player's inventory.
     *
     * @param stack The stack to store.
     *
     * @return The slot that the stack can be stored in, or `-1` if it can't be stored.
     *
     * @docParam stack <item:minecraft:diamond>
     */
    @ZenCodeType.Method
    public static int canStoreIItemStack(PlayerInventory internal, IItemStack stack) {
        
        return internal.storeItemStack(stack.getInternal());
    }
    
    /**
     * Adds the stack to the first empty slot in the player's inventory.
     *
     * @param stack The stack to add.
     *
     * @return True if the stack was added. False otherwise.
     *
     * @docParam stack <item:minecraft:diamond>
     */
    @ZenCodeType.Method
    public static boolean addIItemStackToInventory(PlayerInventory internal, IItemStack stack) {
        
        return internal.addItemStackToInventory(stack.getInternal());
    }
    
    /**
     * Adds the stack to the given slot in the player's inventory.
     *
     * @param slot  The slot to put the stack.
     * @param stack The stack to add.
     *
     * @return True if the stack was inserted. False otherwise.
     *
     * @docParam stack <item:minecraft:diamond>
     */
    @ZenCodeType.Method
    public static boolean add(PlayerInventory internal, int slot, IItemStack stack) {
        
        return internal.add(slot, stack.getInternal());
    }
    
    /**
     * Removes the first instance of the given stack from the inventory.
     *
     * @param stack The stack to remove.
     *
     * @docParam stack <item:minecraft:diamond>
     */
    @ZenCodeType.Method
    public static void deleteStack(PlayerInventory internal, IItemStack stack) {
        
        internal.deleteStack(stack.getInternal());
    }
    
    /**
     * Drop all items in the inventory..
     */
    @ZenCodeType.Method
    public static void dropAllItems(PlayerInventory internal) {
        
        internal.dropAllItems();
    }
    
    /**
     * Checks if the given IItemStack is in the inventory.
     *
     * @param stack The stack to look for.
     *
     * @return True if the stack is found. False otherwise.
     *
     * @docParam stack <item:minecraft:diamond>
     */
    @ZenCodeType.Method
    public static boolean hasIItemStack(PlayerInventory internal, IItemStack stack) {
        
        return internal.hasItemStack(stack.getInternal());
    }
    
    
    /**
     * Gets the IItemStack that is being held by the mouse in a Gui/Container.
     *
     * @return The held IItemStack
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("itemStack")
    public static IItemStack getIItemStack(PlayerInventory internal) {
        
        return new MCItemStack(internal.getItemStack());
    }
    
    /**
     * Sets the IItemStack that is being held by the mouse in a Gui/Container.
     *
     * @param stack The stack to hold.
     *
     * @docParam stack <item:minecraft:dirt>
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("itemStack")
    public static void setIItemStack(PlayerInventory internal, IItemStack stack) {
        
        internal.setItemStack(stack.getInternal());
    }
    
    /**
     * Removes all stacks that match the ingredient.
     *
     * @param ingredient The ingredient to match against.
     *
     * @return True if anything was removed. False otherwise.
     * @docParam ingredient <item:minecraft:diamond>
     */
    @ZenCodeType.Method
    public static boolean remove(PlayerInventory internal, IIngredient ingredient) {
        boolean hit = false;
        for(NonNullList<ItemStack> nonnulllist : internal.allInventories) {
            for(int i = 0; i < nonnulllist.size(); ++i) {
                if(ingredient.matches(new MCItemStackMutable(nonnulllist.get(i)))) {
                    nonnulllist.set(i, ItemStack.EMPTY);
                    hit = true;
                }
            }
        }
        return hit;
    }
    
}
