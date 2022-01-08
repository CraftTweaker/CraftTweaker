package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Set;

@ZenRegister
@Document("vanilla/api/world/Container")
@NativeTypeRegistration(value = Container.class, zenCodeName = "crafttweaker.api.world.Container")
public class ExpandContainer {
    
    /**
     * Gets the size (how many slots) of this Container
     *
     * @return The amount of slots this Container has.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("containerSize")
    public static int getContainerSize(Container internal) {
        
        return internal.getContainerSize();
    }
    
    /**
     * Checks if this Container is empty.
     *
     * @return True if empty. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isEmpty")
    public static boolean isEmpty(Container internal) {
        
        return internal.isEmpty();
    }
    
    /**
     * Gets the ItemStack in the given slot.
     *
     * @param index The slot index to get.
     *
     * @return the ItemStack in the given slot.
     */
    @ZenCodeType.Method
    public static ItemStack getItem(Container internal, int index) {
        
        return internal.getItem(index);
    }
    
    @ZenCodeType.Method
    public static ItemStack removeItem(Container internal, int var1, int var2) {
        
        return internal.removeItem(var1, var2);
    }
    
    @ZenCodeType.Method
    public static ItemStack removeItemNoUpdate(Container internal, int index) {
        
        return internal.removeItemNoUpdate(index);
    }
    
    @ZenCodeType.Method
    public static void setItem(Container internal, int index, ItemStack stack) {
        
        internal.setItem(index, stack);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxStackSize")
    public static int getMaxStackSize(Container internal) {
        
        return internal.getMaxStackSize();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("changed")
    public static void setChanged(Container internal) {
        
        internal.setChanged();
    }
    
    @ZenCodeType.Method
    public static boolean stillValid(Container internal, Player player) {
        
        return internal.stillValid(player);
    }
    
    @ZenCodeType.Method
    public static void startOpen(Container internal, Player player) {
        
        internal.startOpen(player);
    }
    
    @ZenCodeType.Method
    public static void stopOpen(Container internal, Player player) {
        
        internal.stopOpen(player);
    }
    
    @ZenCodeType.Method
    public static boolean canPlaceItem(Container internal, int index, ItemStack stack) {
        
        return internal.canPlaceItem(index, stack);
    }
    
    @ZenCodeType.Method
    public static int countItem(Container internal, Item item) {
        
        return internal.countItem(item);
    }
    
    @ZenCodeType.Method
    public static boolean hasAnyOf(Container internal, Set<Item> items) {
        
        return internal.hasAnyOf(items);
    }
    
}
