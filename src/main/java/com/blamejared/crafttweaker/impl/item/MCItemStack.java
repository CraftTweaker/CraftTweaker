package com.blamejared.crafttweaker.impl.item;

import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.ingredients.IngredientNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.StringTextComponent;

public class MCItemStack implements IItemStack {
    
    private final ItemStack internal;
    
    public MCItemStack(ItemStack internal) {
        this.internal = internal.copy();
    }
    
    @Override
    public IItemStack setDisplayName(String name) {
        ItemStack newStack = internal.copy();
        newStack.setDisplayName(new StringTextComponent(name));
        return new MCItemStack(newStack);
    }
    
    @Override
    public IItemStack setAmount(int amount) {
        ItemStack newStack = internal.copy();
        newStack.setCount(amount);
        return new MCItemStack(newStack);
    }
    
    @Override
    public IItemStack withDamage(int damage) {
        final ItemStack copy = internal.copy();
        copy.setDamage(damage);
        return new MCItemStack(copy);
    }
    
    @Override
    public IItemStack withTag(MapData tag) {
        final ItemStack copy = internal.copy();
        copy.setTag(tag.getInternal());
        return new MCItemStack(copy);
    }
    
    @Override
    public String getCommandString() {
        final StringBuilder sb = new StringBuilder("<item:");
        sb.append(internal.getItem().getRegistryName());
        sb.append(">");
        
        if(internal.getTag() != null) {
            sb.append(".withTag(");
            sb.append(NBTConverter.convert(internal.getTag()).asString());
            sb.append(")");
        }
        
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
    public Ingredient asVanillaIngredient() {
        return new IngredientNBT(getInternal());
    }
    
    @Override
    public String toString() {
        return getCommandString();
    }
    
    @Override
    public IItemStack[] getItems() {
        return new IItemStack[]{this};
    }
}
