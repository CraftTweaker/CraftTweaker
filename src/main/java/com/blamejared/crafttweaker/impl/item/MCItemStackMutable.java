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
        if(tag instanceof MapData)
            getInternal().setTag(((MapData) tag).getInternal());
        else {
            //TODO: What do we do if it's not a map?
            getInternal().setTag(((MapData) tag.asMap()).getInternal());
        }
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
    
}
