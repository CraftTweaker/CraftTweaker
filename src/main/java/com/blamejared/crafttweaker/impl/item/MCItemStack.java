package com.blamejared.crafttweaker.impl.item;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.actions.items.ActionSetFood;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.food.MCFood;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.crafting.NBTIngredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
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
    public IItemStack setAmount(int amount) {
    
        ItemStack newStack = getInternal().copy();
        newStack.setCount(amount);
        return new MCItemStack(newStack);
    }
    
    @Override
    public IItemStack withDamage(int damage) {
    
        final ItemStack copy = getInternal().copy();
        copy.setDamage(damage);
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
    public MCFood getFood() {
    
        final Food food = getInternal().getItem().getFood();
        return food == null ? null : new MCFood(food);
    }
    
    @Override
    public void setFood(MCFood food) {
    
        CraftTweakerAPI.apply(new ActionSetFood(this, food));
    }
    
    @Override
    public String getCommandString() {
    
        final StringBuilder sb = new StringBuilder("<item:");
        sb.append(getInternal().getItem().getRegistryName());
        sb.append(">");
        
        if(getInternal().getTag() != null) {
            MapData data = (MapData) NBTConverter.convert(getInternal().getTag()).copyInternal();
            //Damage is special case, if we have more special cases we can handle them here.
            if(getInternal().getItem().isDamageable()) {
                data.remove("Damage");
            }
            if(!data.isEmpty()) {
                sb.append(".withTag(");
                sb.append(data.asString());
                sb.append(")");
            }
        }
    
        if(getInternal().getDamage() > 0) {
            sb.append(".withDamage(").append(getInternal().getDamage()).append(")");
        }
    
        if(getAmount() != 1) {
            sb.append(" * ").append(getAmount());
        }
        return sb.toString();
    }
    
    @Override
    public ItemStack getInternal() {
    
        return internal;
    }
    
    @Override
    public int getDamage() {
    
        return internal.getDamage();
    }
    
    @Override
    public IItemStack mutable() {
    
        return new MCItemStackMutable(internal);
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
    
        if(getInternal().isEmpty()) {
            return Ingredient.EMPTY;
        }
        if(!getInternal().hasTag()) {
            return Ingredient.fromStacks(getInternal());
        }
        return new NBTIngredient(getInternal()) {};
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
        final ItemStack thatStack = ((MCItemStack) o).internal;
        final ItemStack thisStack = this.internal;
    
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
    
        return Objects.hash(internal.getCount(), internal.getItem(), internal.getTag());
    }
    
}
