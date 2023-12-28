package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.items.IItemHandler;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/capability/IItemHandler")
@NativeTypeRegistration(value = IItemHandler.class, zenCodeName = "crafttweaker.api.capability.IItemHandler")
public class ExpandIItemHandler {
    
    /**
     * Gets the amount of slots in the handler.
     *
     * @return The amount of slots in the handler.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("slots")
    public static int getSlots(IItemHandler internal) {
        
        return internal.getSlots();
    }
    
    /**
     * Gets the stack in the given slot.
     *
     * @param slot The slot to get the stack of.
     *
     * @return The stack in the slot.
     *
     * @docParam slot 1
     */
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    public static IItemStack getStackInSlot(IItemHandler internal, int slot) {
        
        return new MCItemStack(internal.getStackInSlot(slot));
    }
    
    /**
     * Inserts the stack into the given slot and returns the remainder.
     * <p>
     * The remainder returned is how much was not inserted.
     * </p>
     * <p>
     * For example if slot `0` had `63` dirt, and you tried to insert `5` dirt, you will get a remainder of `4` dirt.
     * </p>
     *
     * @param slot     The slot to insert into.
     * @param stack    The stack to insert.
     * @param simulate If the insert should actually happen, if true, will be made.
     *
     * @return The remaining stack that was not inserted.
     *
     * @docParam slot 1
     * @docParam stack <item:minecraft:dirt>
     * @docParam simulate true
     */
    @ZenCodeType.Method
    public static IItemStack insertItem(IItemHandler internal, int slot, IItemStack stack, boolean simulate) {
        
        return new MCItemStack(internal.insertItem(slot, stack.getInternal(), simulate));
    }
    
    /**
     * Extract from the given slot.
     *
     * @param slot     The slot to extract from.
     * @param amount   How much to extract from the slot.
     * @param simulate If the extraction should actually happen, if true, no changes will be made.
     *
     * @return The stack extracted from the slot.
     *
     * @docParam slot 0
     * @docParam amount 5
     * @docParam simulate false
     */
    @ZenCodeType.Method
    public static IItemStack extractItem(IItemHandler internal, int slot, int amount, boolean simulate) {
        
        return new MCItemStack(internal.extractItem(slot, amount, simulate));
    }
    
    /**
     * Gets how much of a stack can fit into the given slot.
     *
     * @param slot The slot to check.
     *
     * @return The max stack size of the given stack.
     *
     * @docParam slot 1
     */
    @ZenCodeType.Method
    public static int getSlotLimit(IItemHandler internal, int slot) {
        
        return internal.getSlotLimit(slot);
    }
    
    /**
     * Checks if the given stack is valid for the given slot.
     *
     * @param slot  The slot to check.
     * @param stack The stack to check.
     *
     * @return true if the stack is valid, false otherwise.
     */
    @ZenCodeType.Method
    public static boolean isItemValid(IItemHandler internal, int slot, IItemStack stack) {
        
        return internal.isItemValid(slot, stack.getInternal());
    }
    
}