package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public final class ItemStackUtil {
    
    public static String getCommandString(final ItemStack stack) {
        
        return getCommandString(stack, false);
    }
    
    public static String getCommandString(final ItemStack stack, final boolean mutable) {
        
        final StringBuilder sb = new StringBuilder("<item:").append(BuiltInRegistries.ITEM.getKey(stack.getItem()))
                .append('>');
        
        final CompoundTag tag;
        if((tag = stack.getTag()) != null) {
            
            final IData data = Objects.requireNonNull(TagToDataConverter.convert(tag)).copyInternal();
            
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
            
            // Another option would be to mark it as mutable from the start: which one do we prefer?
            sb.insert(0, '(').append(").mutable()");
        }
        
        return sb.toString();
    }
    
    public static boolean areStacksTheSame(final ItemStack first, final ItemStack second) {
        
        return areStacksTheSame(first, second, false);
    }
    
    public static boolean areStacksTheSame(final ItemStack first, final ItemStack second, final boolean ignoreDamage) {
        
        return areStacksTheSame(first, second, ignoreDamage, false);
    }
    
    public static boolean areStacksTheSame(final ItemStack first, final ItemStack second, final boolean ignoreDamage, final boolean partial) {
        
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
        
        // Note: different from original (see comment at the end of the method)
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
        
        final Tag firstDamage = firstTag.get("Damage");
        final Tag secondDamage = secondTag.get("Damage");
        
        try {
            if(ignoreDamage) {
                firstTag.remove("Damage");
                secondTag.remove("Damage");
            }
            
            // We want to perform the full check only if partial matching is not required. In fact, checking tag
            // equality means making a full comparison of the two compound's maps. In Java's wisdom, this is done by
            // looping over all keys and checking with get and contains... which is literally what we'd end up doing in
            // a partial match. It's very interesting that HashMap does not override this behavior to check for buckets.
            if(!partial) {
                return firstTag.equals(secondTag);
            }
            
            return areStackTagsPartiallyEqual(firstTag, secondTag);
        } finally {
            // We set the values we obtained back into the tag. If we ignored damage, then the removal operation did not
            // occur. Setting the values again results in a no-op then, the equivalent of map.put("a", map.get("a")).
            // If that's not the case, on the other hand, we reset the tag to how it was before
            if(firstDamage != null) {
                firstTag.put("Damage", firstDamage);
            }
            if(secondDamage != null) {
                secondTag.put("Damage", secondDamage);
            }
        }
        
        /*
        ItemStack stack1 = getInternal();
        ItemStack stack2 = stack.getInternal();
        
        if(stack1.isEmpty() != stack2.isEmpty()) {
            return false;
        }
        if(stack1.getItem() != stack2.getItem()) {
            return false;
        }
        if(stack1.getCount() > stack2.getCount()) {
            return false;
        }
        // This is really just an early exit, since damage is NBT based, it is checked again in the NBT contains
        if(!ignoreDamage) {
            if(stack1.getDamageValue() != stack2.getDamageValue()) {
                return false;
            }
        }
        CompoundTag stack1Tag = stack1.getTag();
        CompoundTag stack2Tag = stack2.getTag();
        if(stack1Tag == null && stack2Tag == null) {
            return true;
        }
        
        // Lets just use the partial nbt
        IData stack2Data = TagToDataConverter.convert(stack2Tag);
        IData stack1Data = TagToDataConverter.convert(stack1Tag);
        if(stack1Data == null) {
            return true;
        }
        if(ignoreDamage) {
            stack1Data = stack1Data.copyInternal();
            stack1Data.remove("Damage");
            if(stack2Data != null) {
                stack2Data = stack2Data.copyInternal();
                stack2Data.remove("Damage");
            }
        }
        return stack2Data != null && stack2Data.contains(stack1Data);
        */
    }
    
    private static boolean areStackTagsPartiallyEqual(final CompoundTag partial, final CompoundTag actual) {
        
        if(partial == actual) {
            return true;
        }
        if(partial == null || actual == null) {
            return false;
        }
        
        // TODO("If we figure out a way to avoid having to convert everything to IData, let's use that")
        final IData partialData = TagToDataConverter.convert(partial);
        final IData actualData = TagToDataConverter.convert(actual);
        
        assert partialData != null && actualData != null;
        
        return actualData.contains(partialData);
    }
    
}
