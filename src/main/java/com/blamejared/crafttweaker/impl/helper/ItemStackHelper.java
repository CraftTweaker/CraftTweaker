package com.blamejared.crafttweaker.impl.helper;

import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker.impl.data.MapData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public final class ItemStackHelper {
    
    public static String getCommandString(final ItemStack stack) {
    
        final StringBuilder sb = new StringBuilder("<item:");
        sb.append(stack.getItem().getRegistryName());
        sb.append('>');
    
        if(stack.getTag() != null) {
            MapData data = (MapData) NBTConverter.convert(stack.getTag()).copyInternal();
            //Damage is special case, if we have more special cases we can handle them here.
            if(stack.getItem().isDamageable()) {
                data.remove("Damage");
            }
            if(!data.isEmpty()) {
                sb.append(".withTag(");
                sb.append(data.asString());
                sb.append(')');
            }
        }
    
        if(stack.getDamage() > 0) {
            sb.append(".withDamage(").append(stack.getDamage()).append(')');
        }
    
        if(!stack.isEmpty()) {
            if(stack.getCount() != 1) {
                sb.append(" * ").append(stack.getCount());
            }
        }
        return sb.toString();
    }
    
    // TODO("This is a copy of IItemStack#matches written to avoid object creation: find a way to avoid code duplication")
    public static boolean areStacksTheSame(final ItemStack first, final ItemStack second) {
        
        if (first.isEmpty() != second.isEmpty()) return false;
        if (first.getItem() != second.getItem()) return false;
        if (first.getCount() > second.getCount()) return false;
        if (first.getDamage() != second.getDamage()) return false;
        
        final CompoundNBT firstTag = first.getTag();
        final CompoundNBT secondTag = second.getTag();
    
        // Note: different from original
        if (firstTag == null) return true;
        if (secondTag == null) return false;
        // The original code checks if they are both null and returns true if so, otherwise it converts both of them to
        // MapData and then checks again if the first tag is null. The only possibility is if firstTag is actually null,
        // so we can simplify the check. Also, if the first tag is not null, the second tag cannot be null, otherwise
        // there is no match. We can account for that too.
        
        return firstTag.equals(secondTag);
    }
    
}
