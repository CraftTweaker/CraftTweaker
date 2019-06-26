package com.blamejared.crafttweaker.impl.item;

import com.blamejared.crafttweaker.api.item.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;

public class MCItemStackMutable implements IItemStack {
    
    private final ItemStack internal;
    
    public MCItemStackMutable(ItemStack internal) {
        this.internal = internal;
    }
    
    @Override
    public boolean isEmpty() {
        return internal.isEmpty();
    }
    
    @Override
    public String getDisplayName() {
        return internal.getDisplayName().getFormattedText();
    }
    
    @Override
    public int getAmount() {
        return internal.getCount();
    }
    
    @Override
    public IItemStack setAmount(int amount) {
        internal.setCount(amount);
        return this;
    }
    
    @Override
    public IItemStack withDamage(int damage) {
        internal.setDamage(damage);
        return this;
    }
    
    @Override
    public boolean isStackable() {
        return internal.isStackable();
    }
    
    @Override
    public boolean isDamageable() {
        return internal.isDamageable();
    }
    
    @Override
    public boolean isDamaged() {
        return internal.isDamaged();
    }
    
    @Override
    public int getMaxDamage() {
        return internal.getMaxDamage();
    }
    
    @Override
    public String getTranslationKey() {
        return internal.getTranslationKey();
    }
    
    @Override
    public String getCommandString() {
        final StringBuilder sb = new StringBuilder("<item:");
        sb.append(internal.getItem().getRegistryName());
        sb.append(">");
        if(internal.getDamage() > 0)
            sb.append(".withDamage(").append(internal.getDamage()).append(")");
        
        if(getAmount() != 1)
            sb.append(" * ").append(getAmount());
        return sb.toString();
    }
    
    @Override
    public ItemStack getInternal() {
        return internal;
    }
    
    @Override
    public boolean matches(IItemStack stack) {
        return ItemStack.areItemStacksEqual(this.getInternal(), stack.getInternal());
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        return Ingredient.fromStacks(getInternal());
    }
    
    @Override
    public String toString() {
        return getCommandString();
    }
}
