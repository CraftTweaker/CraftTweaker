package com.blamejared.crafttweaker.impl.item;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.ingredient.PartialNBTIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.AttributeUtil;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.helper.ItemStackHelper;
import com.blamejared.crafttweaker.impl.util.EnchantmentUtil;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.StringTextComponent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class MCItemStack implements IItemStack {
    
    public static Supplier<MCItemStack> EMPTY = () -> new MCItemStack(ItemStack.EMPTY);
    private final ItemStack internal;
    
    public MCItemStack(ItemStack internal) {
        
        this.internal = internal;
    }
    
    @Override
    public IItemStack copy() {
        
        return new MCItemStack(getInternal().copy());
    }
    
    @Override
    public IItemStack setDisplayName(String name) {
        
        ItemStack newStack = getInternal().copy();
        newStack.setDisplayName(new StringTextComponent(name));
        return new MCItemStack(newStack);
    }
    
    @Override
    public IItemStack withDisplayName(MCTextComponent text) {
        
        ItemStack newStack = getInternal().copy();
        newStack.setDisplayName(text.getInternal());
        return new MCItemStack(newStack);
    }
    
    @Override
    public IItemStack setAmount(int amount) {
        
        ItemStack newStack = getInternal().copy();
        newStack.setCount(amount);
        return new MCItemStack(newStack);
    }
    
    @Override
    public IItemStack grow(int amount) {
        
        ItemStack newStack = getInternal().copy();
        newStack.grow(amount);
        return new MCItemStack(newStack);
    }
    
    @Override
    public IItemStack shrink(int amount) {
        
        ItemStack newStack = getInternal().copy();
        newStack.shrink(amount);
        return new MCItemStack(newStack);
    }
    
    @Override
    public IItemStack withDamage(int damage) {
        
        final ItemStack copy = getInternal().copy();
        copy.setDamage(damage);
        return new MCItemStack(copy);
    }
    
    @Override
    public IItemStack withAttributeModifier(Attribute attribute, String uuid, String name, double value, AttributeModifier.Operation operation, EquipmentSlotType[] slotTypes, boolean preserveDefaults) {
        
        final ItemStack copy = getInternal().copy();
        for(EquipmentSlotType slotType : slotTypes) {
            if(preserveDefaults) {
                AttributeUtil.addAttributeModifier(copy, attribute, new AttributeModifier(UUID.fromString(uuid), name, value, operation), slotType);
            } else {
                copy.addAttributeModifier(attribute, new AttributeModifier(UUID.fromString(uuid), name, value, operation), slotType);
            }
            
        }
        return new MCItemStack(copy);
    }
    
    @Override
    public IItemStack withAttributeModifier(Attribute attribute, String name, double value, AttributeModifier.Operation operation, EquipmentSlotType[] slotTypes, boolean preserveDefaults) {
        
        final ItemStack copy = getInternal().copy();
        for(EquipmentSlotType slotType : slotTypes) {
            if(preserveDefaults) {
                AttributeUtil.addAttributeModifier(copy, attribute, new AttributeModifier(name, value, operation), slotType);
            } else {
                copy.addAttributeModifier(attribute, new AttributeModifier(name, value, operation), slotType);
            }
        }
        return new MCItemStack(copy);
    }
    
    @Override
    public IItemStack withTag(IData tag) {
        
        final ItemStack copy = getInternal().copy();
        if(!(tag instanceof MapData)) {
            tag = new MapData(tag.asMap());
        }
        copy.setTag(((MapData) tag).getInternal());
        return new MCItemStack(copy);
    }
    
    @Override
    public IItemStack withoutTag() {
        
        final ItemStack copy = getImmutableInternal();
        copy.setTag(null);
        return new MCItemStack(copy);
    }
    
    @Override
    public String getCommandString() {
        
        return ItemStackHelper.getCommandString(this.getInternal());
    }
    
    @Override
    public ItemStack getInternal() {
        
        return internal;
    }
    
    @Override
    public ItemStack getImmutableInternal() {
        
        // Gotta copy here to ensure that what we have is truly immutable
        return internal.copy();
    }
    
    @Override
    public int getDamage() {
        
        return getInternal().getDamage();
    }
    
    
    @Override
    public IItemStack setEnchantments(Map<Enchantment, Integer> enchantments) {
        
        ItemStack newStack = getInternal().copy();
        EnchantmentUtil.setEnchantments(enchantments, newStack);
        return new MCItemStack(newStack);
    }
    
    @Override
    public IItemStack withEnchantment(Enchantment enchantment, int level) {
        
        ItemStack newStack = getInternal().copy();
        Map<Enchantment, Integer> enchantments = getEnchantments();
        enchantments.put(enchantment, level);
        EnchantmentUtil.setEnchantments(enchantments, newStack);
        return new MCItemStack(newStack);
    }
    
    @Override
    public IItemStack removeEnchantment(Enchantment enchantment) {
        
        ItemStack newStack = getInternal().copy();
        Map<Enchantment, Integer> enchantments = getEnchantments();
        enchantments.remove(enchantment);
        EnchantmentUtil.setEnchantments(enchantments, newStack);
        return new MCItemStack(newStack);
    }
    
    @Override
    public IItemStack mutable() {
        
        return new MCItemStackMutable(getInternal());
    }
    
    @Override
    public IItemStack asImmutable() {
        
        return this;
    }
    
    @Override
    public boolean isImmutable() {
        
        return true;
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        
        if(getInternal().isEmpty()) {
            return Ingredient.EMPTY;
        }
        if(!getInternal().hasTag()) {
            return Ingredient.fromStacks(getImmutableInternal());
        }
        return new PartialNBTIngredient(getImmutableInternal());
    }
    
    @Override
    public String toString() {
        
        return getCommandString();
    }
    
    @Override
    public IItemStack[] getItems() {
        
        return new IItemStack[] {this.copy()};
    }
    
    @Override
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        //Implemented manually instead of using ItemStack.areItemStacksEqual to ensure it
        // stays the same as hashCode even if MC's impl would change
        final ItemStack thatStack = ((MCItemStack) o).getInternal();
        final ItemStack thisStack = getInternal();
        
        if(thisStack.isEmpty()) {
            return thatStack.isEmpty();
        }
        
        if(thisStack.getCount() != thatStack.getCount()) {
            return false;
        }
        
        if(!Objects.equals(thisStack.getItem(), thatStack.getItem())) {
            return false;
        }
        
        if(!Objects.equals(thisStack.getTag(), thatStack.getTag())) {
            return false;
        }
        
        return thisStack.areCapsCompatible(thatStack);
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(getInternal().getCount(), getInternal().getItem(), getInternal().getTag());
    }
    
}
