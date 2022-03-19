package com.blamejared.crafttweaker.natives.entity.type.player;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/player/Inventory")
@NativeTypeRegistration(value = Inventory.class, zenCodeName = "crafttweaker.api.entity.type.player.Inventory")
public class ExpandInventory {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("selected")
    public static ItemStack getSelected(Inventory internal) {
        
        return internal.getSelected();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("freeSlot")
    public static int getFreeSlot(Inventory internal) {
        
        return internal.getFreeSlot();
    }
    
    @ZenCodeType.Method
    public static void setPickedItem(Inventory internal, ItemStack stack) {
        
        internal.setPickedItem(stack);
    }
    
    @ZenCodeType.Method
    public static void pickSlot(Inventory internal, int index) {
        
        internal.pickSlot(index);
    }
    
    @ZenCodeType.Method
    public static int findSlotMatchingItem(Inventory internal, ItemStack stack) {
        
        return internal.findSlotMatchingItem(stack);
    }
    
    @ZenCodeType.Method
    public static int findSlotMatchingUnusedItem(Inventory internal, ItemStack stack) {
        
        return internal.findSlotMatchingUnusedItem(stack);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("suitableHotbarSlot")
    public static int getSuitableHotbarSlot(Inventory internal) {
        
        return internal.getSuitableHotbarSlot();
    }
    
    @ZenCodeType.Method
    public static int getSlotWithRemainingSpace(Inventory internal, ItemStack stack) {
        
        return internal.getSlotWithRemainingSpace(stack);
    }
    
    @ZenCodeType.Method
    public static boolean add(Inventory internal, ItemStack stack) {
        
        return internal.add(stack);
    }
    
    @ZenCodeType.Method
    public static boolean add(Inventory internal, int index, ItemStack stack) {
        
        return internal.add(index, stack);
    }
    
    @ZenCodeType.Method
    public static void removeItem(Inventory internal, ItemStack stack) {
        
        internal.removeItem(stack);
    }
    
    @ZenCodeType.Method
    public static ItemStack getArmor(Inventory internal, int armorIndex) {
        
        return internal.getArmor(armorIndex);
    }
    
    @ZenCodeType.Method
    public static void hurtArmor(Inventory internal, DamageSource source, float damage, int[] armorSlots) {
        
        internal.hurtArmor(source, damage, armorSlots);
    }
    
    @ZenCodeType.Method
    public static void dropAll(Inventory internal) {
        
        internal.dropAll();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("timesChanged")
    public static int getTimesChanged(Inventory internal) {
        
        return internal.getTimesChanged();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    public static boolean contains(Inventory internal, ItemStack stack) {
        
        return internal.contains(stack);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    public static boolean contains(Inventory internal, MCTag<Item> tag) {
        
        return internal.contains(tag.getTagKey());
    }
    
}
