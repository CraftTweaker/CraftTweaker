package com.blamejared.crafttweaker.impl.item;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.actions.items.ActionSetFood;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.food.MCFood;
import com.blamejared.crafttweaker.impl.ingredients.IngredientNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.StringTextComponent;
import org.openzen.zencode.java.*;

import java.util.*;

public class MCItemStackMutable implements IItemStack {
    
    private final ItemStack internal;
    
    public MCItemStackMutable(ItemStack internal) {
        this.internal = internal;
    }

    @Override
    public IItemStack copy() {
        return new MCItemStackMutable(internal.copy());
    }

    @Override
    public IItemStack setDisplayName(String name) {
        getInternal().setDisplayName(new StringTextComponent(name));
        return this;
    }
    
    @Override
    public IItemStack setAmount(int amount) {
        getInternal().setCount(amount);
        return this;
    }
    
    @Override
    public IItemStack withDamage(int damage) {
        getInternal().setDamage(damage);
        return this;
    }
    @Override
    public MCFood getFood() {
        return new MCFood(getInternal().getItem().getFood());
    }
    
    @Override
    public void setFood(MCFood food) {
        CraftTweakerAPI.apply(new ActionSetFood(this, food));
    }
    
    @Override
    public IItemStack withTag(IData tag) {
        if(!(tag instanceof MapData)) {
            tag = new MapData(tag.asMap());
        }
        getInternal().setTag(((MapData) tag).getInternal());
        return this;
    }
    
    @Override
    public String getCommandString() {
        final StringBuilder sb = new StringBuilder("<item:");
        sb.append(internal.getItem().getRegistryName());
        sb.append(">");
    
        if(getInternal().getTag() != null) {
            MapData data = (MapData) NBTConverter.convert(getInternal().getTag()).copyInternal();
            //Damage is special case, if we have more special cases we can handle them here.
            if(getInternal().isDamaged()) {
                data.remove("Damage");
            }
            if(!data.isEmpty()) {
                sb.append(".withTag(");
                sb.append(data.asString());
                sb.append(")");
            }
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
    public int getDamage() {
        return internal.getDamage();
    }
    
    @Override
    public IItemStack mutable() {
        return this;
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        if(getInternal().isEmpty()){
            return Ingredient.EMPTY;
        }
        if(!getInternal().hasTag()){
            return Ingredient.fromStacks(getInternal());
        }
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
    
    @Override
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        
        //Implemented manually instead of using ItemStack.areItemStacksEqual to ensure it
        // stays the same as hashCode even if MC's impl would change
        final ItemStack thatStack = ((MCItemStackMutable) o).internal;
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
