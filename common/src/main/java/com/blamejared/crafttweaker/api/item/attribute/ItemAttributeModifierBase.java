package com.blamejared.crafttweaker.api.item.attribute;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

// Doesn't need to be exposed to ZC, it is in the api package for other mods to potentially use
public interface ItemAttributeModifierBase {
    
    Multimap<Attribute, AttributeModifier> getModifiers();
    
    Multimap<Attribute, AttributeModifier> getOriginalModifiers();
    
    boolean addModifier(Attribute attribute, AttributeModifier modifier);
    
    boolean removeModifier(Attribute attribute, AttributeModifier modifier);
    
    Collection<AttributeModifier> removeAttribute(Attribute attribute);
    
    void clearModifiers();
    
    EquipmentSlot getSlotType();
    
    ItemStack getItemStack();
    
}
