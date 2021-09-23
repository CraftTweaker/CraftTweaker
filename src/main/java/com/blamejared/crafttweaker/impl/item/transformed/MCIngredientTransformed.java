package com.blamejared.crafttweaker.impl.item.transformed;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformer;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.MCIngredientTransformed")
@Document("vanilla/api/items/MCIngredientTransformed")
public class MCIngredientTransformed<T extends IIngredient> implements IIngredient {
    
    private final T base;
    private final IIngredientTransformer<T> transformer;
    
    public MCIngredientTransformed(T base, IIngredientTransformer<T> transformer) {
        
        this.base = base;
        this.transformer = transformer;
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        
        return new IngredientTransformed<>(this);
    }
    
    @ZenCodeType.Getter("transformer")
    public IIngredientTransformer<T> getTransformer() {
        
        return transformer;
    }
    
    @Override
    public IItemStack getRemainingItem(IItemStack stack) {
        
        return transformer.transform(stack);
    }
    
    @Override
    public String getCommandString() {
        
        return transformer.getCommandString(base);
    }
    
    @ZenCodeType.Getter("baseIngredient")
    public T getBaseIngredient() {
        
        return base;
    }
    
    @Override
    @ZenCodeType.Method
    public boolean matches(IItemStack stack, boolean ignoreDamage) {
        
        return base.matches(stack, ignoreDamage);
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
    
    @Override
    public boolean equals(Object o) {
    
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        MCIngredientTransformed<?> that = (MCIngredientTransformed<?>) o;
    
        if(!base.equals(that.base)) {
            return false;
        }
        return transformer.equals(that.transformer);
    }
    
    @Override
    public int hashCode() {
        
        int result = base.hashCode();
        result = 31 * result + transformer.hashCode();
        return result;
    }
    
}
