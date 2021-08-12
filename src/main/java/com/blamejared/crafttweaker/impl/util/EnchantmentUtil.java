package com.blamejared.crafttweaker.impl.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.registry.Registry;

import java.util.Map;

public class EnchantmentUtil {
    
    public static void setEnchantments(Map<Enchantment, Integer> enchMap, ItemStack stack) {
        
        ListNBT listnbt = new ListNBT();
        
        for(Map.Entry<Enchantment, Integer> entry : enchMap.entrySet()) {
            Enchantment enchantment = entry.getKey();
            if(enchantment != null) {
                int i = entry.getValue();
                CompoundNBT compoundnbt = new CompoundNBT();
                compoundnbt.putString("id", String.valueOf((Object) Registry.ENCHANTMENT.getKey(enchantment)));
                compoundnbt.putShort("lvl", (short) i);
                listnbt.add(compoundnbt);
                if(stack.getItem() == Items.ENCHANTED_BOOK) {
                    EnchantedBookItem.addEnchantment(stack, new EnchantmentData(enchantment, i));
                }
            }
        }
        
        if(listnbt.isEmpty()) {
            stack.removeChildTag(stack.getItem() == Items.ENCHANTED_BOOK ? "StoredEnchantments" : "Enchantments");
        } else if(stack.getItem() != Items.ENCHANTED_BOOK) {
            stack.setTagInfo("Enchantments", listnbt);
        }
        
    }
    
}
