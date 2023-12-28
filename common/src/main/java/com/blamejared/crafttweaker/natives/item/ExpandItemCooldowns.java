package com.blamejared.crafttweaker.natives.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/ItemCooldowns")
@NativeTypeRegistration(value = ItemCooldowns.class, zenCodeName = "crafttweaker.api.item.ItemCooldowns")
public class ExpandItemCooldowns {
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    public static boolean isOnCooldown(ItemCooldowns internal, Item item) {
        
        return internal.isOnCooldown(item);
    }
    
    @ZenCodeType.Method
    public static float getCooldownPercent(ItemCooldowns internal, Item item, float partialTicks) {
        
        return internal.getCooldownPercent(item, partialTicks);
    }
    
    @ZenCodeType.Method
    public static void addCooldown(ItemCooldowns internal, Item item, int ticks) {
        
        internal.addCooldown(item, ticks);
    }
    
    @ZenCodeType.Method
    public static void removeCooldown(ItemCooldowns internal, Item item) {
        
        internal.removeCooldown(item);
    }
    
}
