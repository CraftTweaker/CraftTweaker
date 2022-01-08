package com.blamejared.crafttweaker.api.item.modifier;

import com.blamejared.crafttweaker.api.item.attribute.ItemAttributeModifierBase;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Collection;

public class FabricItemAttributeModifier implements ItemAttributeModifierBase {
    
    private final ItemStack stack;
    private final EquipmentSlot slotType;
    private final Multimap<Attribute, AttributeModifier> originalModifiers;
    private Multimap<Attribute, AttributeModifier> unmodifiableModifiers;
    @Nullable
    private Multimap<Attribute, AttributeModifier> modifiableModifiers;
    
    public FabricItemAttributeModifier(ItemStack stack, EquipmentSlot slotType, Multimap<Attribute, AttributeModifier> originalModifiers) {
        
        this.stack = stack;
        this.slotType = slotType;
        this.unmodifiableModifiers = this.originalModifiers = originalModifiers;
    }
    
    public Multimap<Attribute, AttributeModifier> getModifiers() {
        
        return this.unmodifiableModifiers;
    }
    
    public Multimap<Attribute, AttributeModifier> getOriginalModifiers() {
        
        return this.originalModifiers;
    }
    
    private Multimap<Attribute, AttributeModifier> getModifiableMap() {
        
        if(this.modifiableModifiers == null) {
            this.modifiableModifiers = HashMultimap.create(this.originalModifiers);
            this.unmodifiableModifiers = Multimaps.unmodifiableMultimap(this.modifiableModifiers);
        }
        return this.modifiableModifiers;
    }
    
    public boolean addModifier(Attribute attribute, AttributeModifier modifier) {
        
        return getModifiableMap().put(attribute, modifier);
    }
    
    public boolean removeModifier(Attribute attribute, AttributeModifier modifier) {
        
        return getModifiableMap().remove(attribute, modifier);
    }
    
    public Collection<AttributeModifier> removeAttribute(Attribute attribute) {
        
        return getModifiableMap().removeAll(attribute);
    }
    
    public void clearModifiers() {
        
        getModifiableMap().clear();
    }
    
    public EquipmentSlot getSlotType() {
        
        return this.slotType;
    }
    
    public ItemStack getItemStack() {
        
        return this.stack;
    }
    
}
