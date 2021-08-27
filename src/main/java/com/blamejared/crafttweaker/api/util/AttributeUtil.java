package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AttributeUtil {
    
    public static void addAttributeModifier(ItemStack stack, Attribute attributeName, AttributeModifier modifier, @Nullable EquipmentSlotType equipmentSlot) {
        
        CompoundNBT tag = stack.getOrCreateChildTag(IItemStack.CRAFTTWEAKER_DATA_KEY);
        if(!tag.contains("AttributeModifiers", Constants.NBT.TAG_LIST)) {
            tag.put("AttributeModifiers", new ListNBT());
        }
        
        ListNBT listnbt = tag.getList("AttributeModifiers", Constants.NBT.TAG_COMPOUND);
        CompoundNBT compoundnbt = modifier.write();
        compoundnbt.putString("AttributeName", Registry.ATTRIBUTE.getKey(attributeName).toString());
        if(equipmentSlot != null) {
            compoundnbt.putString("Slot", equipmentSlot.getName());
        }
        
        listnbt.add(compoundnbt);
    }
    
    public static Map<Attribute, List<AttributeModifier>> getAttributeModifiers(ItemStack stack, EquipmentSlotType equipmentSlot) {
        
        Map<Attribute, List<AttributeModifier>> map = new HashMap<>();
        CompoundNBT tag = stack.getChildTag(IItemStack.CRAFTTWEAKER_DATA_KEY);
        if(tag == null || !tag.contains("AttributeModifiers", Constants.NBT.TAG_LIST)) {
            return map;
        }
        ListNBT listnbt = tag.getList("AttributeModifiers", Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < listnbt.size(); i++) {
            CompoundNBT compoundnbt = listnbt.getCompound(i);
            if(!compoundnbt.contains("Slot", Constants.NBT.TAG_STRING) || compoundnbt.getString("Slot")
                    .equals(equipmentSlot.getName())) {
                Optional<Attribute> optional = Registry.ATTRIBUTE.getOptional(ResourceLocation.tryCreate(compoundnbt.getString("AttributeName")));
                optional.ifPresent(attribute -> {
                    AttributeModifier attributemodifier = AttributeModifier.read(compoundnbt);
                    if(attributemodifier != null && attributemodifier.getID()
                            .getLeastSignificantBits() != 0L && attributemodifier.getID()
                            .getMostSignificantBits() != 0L) {
                        map.computeIfAbsent(attribute, key -> new ArrayList<>()).add(attributemodifier);
                    }
                });
            }
        }
        return map;
    }
    
}
