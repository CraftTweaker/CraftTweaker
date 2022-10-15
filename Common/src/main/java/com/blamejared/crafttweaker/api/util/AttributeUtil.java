package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AttributeUtil {
    
    public static void addAttributeModifier(ItemStack stack, Attribute attributeName, AttributeModifier modifier, @Nullable EquipmentSlot equipmentSlot) {
        
        CompoundTag tag = stack.getOrCreateTagElement(IItemStack.CRAFTTWEAKER_DATA_KEY);
        if(!tag.contains("AttributeModifiers", Tag.TAG_LIST)) {
            tag.put("AttributeModifiers", new ListTag());
        }
        
        ListTag listnbt = tag.getList("AttributeModifiers", Tag.TAG_COMPOUND);
        CompoundTag compoundnbt = modifier.save();
        compoundnbt.putString("AttributeName", Registry.ATTRIBUTE.getKey(attributeName)
                .toString());
        if(equipmentSlot != null) {
            compoundnbt.putString("Slot", equipmentSlot.getName());
        }
        
        listnbt.add(compoundnbt);
    }
    
    public static Map<Attribute, List<AttributeModifier>> getAttributeModifiers(ItemStack stack, EquipmentSlot equipmentSlot) {
        
        Map<Attribute, List<AttributeModifier>> map = new HashMap<>();
        CompoundTag tag = stack.getOrCreateTagElement(IItemStack.CRAFTTWEAKER_DATA_KEY);
        if(!tag.contains("AttributeModifiers", Tag.TAG_LIST)) {
            return map;
        }
        ListTag listnbt = tag.getList("AttributeModifiers", Tag.TAG_COMPOUND);
        for(int i = 0; i < listnbt.size(); i++) {
            CompoundTag compoundnbt = listnbt.getCompound(i);
            if(!compoundnbt.contains("Slot", Tag.TAG_STRING) || compoundnbt.getString("Slot")
                    .equals(equipmentSlot.getName())) {
                Optional<Attribute> optional = Registry.ATTRIBUTE
                        .getOptional(ResourceLocation.tryParse(compoundnbt.getString("AttributeName")));
                optional.ifPresent(attribute -> {
                    AttributeModifier attributemodifier = AttributeModifier.load(compoundnbt);
                    if(attributemodifier != null && attributemodifier.getId()
                            .getLeastSignificantBits() != 0L && attributemodifier.getId()
                            .getMostSignificantBits() != 0L) {
                        map.computeIfAbsent(attribute, key -> new ArrayList<>())
                                .add(attributemodifier);
                    }
                });
            }
        }
        return map;
    }
    
}
