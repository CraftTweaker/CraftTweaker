package com.blamejared.crafttweaker.api.util;


import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public final class ItemStackUtil {
    
    public static String getCommandString(final ItemStack stack) {
        
        return getCommandString(stack, false);
    }
    
    public static String getCommandString(final ItemStack stack, final boolean mutable) {
        
        final StringBuilder sb = new StringBuilder("<item:").append(Registry.ITEM.getKey(stack.getItem())).append('>');
        
        final CompoundTag tag;
        if((tag = stack.getTag()) != null) {
            
            IData data = Objects.requireNonNull(TagToDataConverter.convert(tag)).copyInternal();
            
            //Damage is special case, if we have more special cases we can handle them here.
            if(stack.getItem().canBeDepleted()) {
                
                data.remove("Damage");
            }
            if(!data.isEmpty()) {
                
                sb.append(".withTag(").append(data.asString()).append(')');
            }
        }
        
        if(stack.getDamageValue() > 0) {
            
            sb.append(".withDamage(").append(stack.getDamageValue()).append(')');
        }
        
        if(!stack.isEmpty() && stack.getCount() != 1) {
            
            sb.append(" * ").append(stack.getCount());
        }
        
        if(mutable) {
            
            sb.append(".mutable()");
        }
        
        return sb.toString();
    }
    
    public static boolean areStacksTheSame(final ItemStack first, final ItemStack second) {
        
        return areStacksTheSame(first, second, false);
    }
    
    public static boolean areStacksTheSame(final ItemStack first, final ItemStack second, final boolean ignoreDamage) {
        
        if(first.isEmpty() != second.isEmpty()) {
            return false;
        }
        if(first.getItem() != second.getItem()) {
            return false;
        }
        if(first.getCount() > second.getCount()) {
            return false;
        }
        if(!ignoreDamage && first.getDamageValue() != second.getDamageValue()) {
            return false;
        }
        
        final CompoundTag firstTag = first.getTag();
        final CompoundTag secondTag = second.getTag();
        
        // Note: different from original
        // The original code checks if they are both null and returns true if so, otherwise it converts both of them to
        // MapData and then checks again if the first tag is null. The only possibility is if firstTag is actually null,
        // so we can simplify the check. Also, if the first tag is not null, the second tag cannot be null, otherwise
        // there is no match. We can account for that too.
        if(firstTag == null) {
            return true;
        }
        if(secondTag == null) {
            return false;
        }
        
        if(!ignoreDamage) {
            return firstTag.equals(secondTag);
        }
        
        final Tag firstDamage = firstTag.get("Damage");
        final Tag secondDamage = secondTag.get("Damage");
        
        try {
            firstTag.remove("Damage");
            secondTag.remove("Damage");
            
            return firstTag.equals(secondTag);
        } finally {
            if(firstDamage != null) {
                firstTag.put("Damage", firstDamage);
            }
            if(secondDamage != null) {
                secondTag.put("Damage", secondDamage);
            }
        }
    }
    
}
