package com.blamejared.crafttweaker.api.util;


import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public final class ItemStackUtil {
    
    public static String getCommandString(final ItemStack stack) {
        
        return getCommandString(stack, false);
    }
    
    public static String getCommandString(final ItemStack stack, final boolean mutable) {
        
        final StringBuilder sb = new StringBuilder("<item:");
        sb.append(Services.REGISTRY.getRegistryKey(stack.getItem()));
        sb.append('>');
        
        if(stack.getTag() != null) {
            
            MapData data = TagToDataConverter.convertCompound(stack.getTag()).copyInternal();
            //Damage is special case, if we have more special cases we can handle them here.
            if(stack.getItem().canBeDepleted()) {
                
                data.remove("Damage");
            }
            if(!data.isEmpty()) {
                
                sb.append(".withTag(");
                sb.append(data.asString());
                sb.append(')');
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
    
    // TODO("This is a copy of IItemStack#matches written to avoid object creation: find a way to avoid code duplication")
    public static boolean areStacksTheSame(final ItemStack first, final ItemStack second) {
    
        if(first.isEmpty() != second.isEmpty()) {
            return false;
        }
        if(first.getItem() != second.getItem()) {
            return false;
        }
        if(first.getCount() > second.getCount()) {
            return false;
        }
        if(first.getDamageValue() != second.getDamageValue()) {
            return false;
        }
        
        final CompoundTag firstTag = first.getTag();
        final CompoundTag secondTag = second.getTag();
        
        // Note: different from original
        if(firstTag == null) {
            return true;
        }
        if(secondTag == null) {
            return false;
        }
        // The original code checks if they are both null and returns true if so, otherwise it converts both of them to
        // MapData and then checks again if the first tag is null. The only possibility is if firstTag is actually null,
        // so we can simplify the check. Also, if the first tag is not null, the second tag cannot be null, otherwise
        // there is no match. We can account for that too.
        
        return firstTag.equals(secondTag);
    }
    
}
