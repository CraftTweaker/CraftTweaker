package com.blamejared.crafttweaker.api.util;


import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.Map;

public class EnchantmentUtil {
    
    public static void setEnchantments(Map<Enchantment, Integer> enchMap, ItemStack stack) {
        
        ListTag listnbt = new ListTag();
        
        for(Map.Entry<Enchantment, Integer> entry : enchMap.entrySet()) {
            Enchantment enchantment = entry.getKey();
            if(enchantment != null) {
                int i = entry.getValue();
                CompoundTag compoundnbt = new CompoundTag();
                compoundnbt.putString("id", String.valueOf(BuiltInRegistries.ENCHANTMENT.getKey(enchantment)));
                compoundnbt.putShort("lvl", (short) i);
                listnbt.add(compoundnbt);
                if(stack.getItem() == Items.ENCHANTED_BOOK) {
                    EnchantedBookItem.addEnchantment(stack, new EnchantmentInstance(enchantment, i));
                }
            }
        }
        
        if(listnbt.isEmpty()) {
            stack.removeTagKey(stack.getItem() == Items.ENCHANTED_BOOK ? "StoredEnchantments" : "Enchantments");
        } else if(stack.getItem() != Items.ENCHANTED_BOOK) {
            stack.addTagElement("Enchantments", listnbt);
        }
        
    }
    
}
