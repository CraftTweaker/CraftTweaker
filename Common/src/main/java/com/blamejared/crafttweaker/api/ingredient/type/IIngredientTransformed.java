package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.transform.IIngredientTransformer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.type.IIngredientTransformed")
@Document("vanilla/api/ingredient/type/IIngredientTransformed")
public class IIngredientTransformed<T extends IIngredient> implements IIngredient {
    
    public static final ResourceLocation ID = CraftTweakerConstants.rl("transformed");
    
    private final T base;
    private final IIngredientTransformer<T> transformer;
    
    public IIngredientTransformed(T base, IIngredientTransformer<T> transformer) {
        
        this.base = base;
        this.transformer = transformer;
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        
        return Services.REGISTRY.getIngredientTransformed(this);
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
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        IIngredientTransformed<?> that = (IIngredientTransformed<?>) o;
        
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
