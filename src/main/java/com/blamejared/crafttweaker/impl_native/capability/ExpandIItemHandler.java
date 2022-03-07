package com.blamejared.crafttweaker.impl_native.capability;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.items.IItemHandler;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/capability/IItemHandler")
@NativeTypeRegistration(value = IItemHandler.class, zenCodeName = "crafttweaker.api.capability.IItemHandler")
public class ExpandIItemHandler {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("slots")
    public static int getSlots(IItemHandler internal) {
        
        return internal.getSlots();
    }
    
    @ZenCodeType.Method
    public static IItemStack getStackInSlot(IItemHandler internal, int slot) {
        
        return new MCItemStack(internal.getStackInSlot(slot));
    }
    
    @ZenCodeType.Method
    public static IItemStack insertItem(IItemHandler internal, int slot, IItemStack stack, @ZenCodeType.OptionalBoolean boolean simulate) {
        
        return new MCItemStack(internal.insertItem(slot, stack.getInternal(), simulate));
    }
    
    @ZenCodeType.Method
    public static IItemStack extractItem(IItemHandler internal, int slot, int amount, @ZenCodeType.OptionalBoolean boolean simulate) {
        
        return new MCItemStack(internal.extractItem(slot, amount, simulate));
    }
    
    @ZenCodeType.Method
    public static int getSlotLimit(IItemHandler internal, int slot) {
        
        return internal.getSlotLimit(slot);
    }
    
    @ZenCodeType.Method
    public static boolean isItemValid(IItemHandler internal, int slot, IItemStack stack) {
        
        return internal.isItemValid(slot, stack.getInternal());
    }
    
}
