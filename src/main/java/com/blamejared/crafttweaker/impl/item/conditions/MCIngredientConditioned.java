package com.blamejared.crafttweaker.impl.item.conditions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientCondition;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.MCIngredientConditioned")
@Document("vanilla/api/items/MCIngredientConditioned")
public class MCIngredientConditioned<T extends IIngredient> implements IIngredient {
    
    private final T base;
    private final IIngredientCondition<T> condition;
    
    public MCIngredientConditioned(T base, IIngredientCondition<T> condition) {
        this.base = base;
        this.condition = condition;
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        return new IngredientConditioned<>(this);
    }
    
    @ZenCodeType.Getter("condition")
    public IIngredientCondition<T> getCondition() {
        return condition;
    }
    
    @Override
    public String getCommandString() {
        return condition.getCommandString(base);
    }
    
    @ZenCodeType.Getter("baseIngredient")
    public T getBaseIngredient() {
        return base;
    }
    
    @Override
    @ZenCodeType.Method
    public boolean matches(IItemStack stack, boolean ignoreDamage) {
        return base.matches(stack, condition.ignoresDamage()) && condition.matches(stack);
    }
    
    @Override
    @ZenCodeType.Getter("items")
    public IItemStack[] getItems() {
        return base.getItems();
    }
    
    @Override
    @ZenCodeType.Caster(implicit = true)
    public MapData asMapData() {
        return base.asMapData();
    }
    
    @Override
    @ZenCodeType.Caster(implicit = true)
    public IData asIData() {
        return base.asIData();
    }
}
