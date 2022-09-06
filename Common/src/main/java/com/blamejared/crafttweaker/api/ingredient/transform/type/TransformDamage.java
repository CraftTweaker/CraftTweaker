package com.blamejared.crafttweaker.api.ingredient.transform.type;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.transform.IIngredientTransformer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformDamageSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.transform.type.TransformDamage")
@Document("vanilla/api/ingredient/transform/type/TransformDamage")
public class TransformDamage<T extends IIngredient> implements IIngredientTransformer<T> {
    
    private final int amount;
    
    public TransformDamage(int amount) {
        
        this.amount = amount;
    }
    
    public int getAmount() {
        
        return amount;
    }
    
    @Override
    public IItemStack transform(IItemStack stack) {
        
        final ItemStack internal = stack.getImmutableInternal();
        final int newDamage = internal.getDamageValue() + amount;
        if(internal.getMaxDamage() < newDamage) {
            return IItemStack.empty();
        }
        internal.setDamageValue(newDamage);
        return IItemStack.of(internal.copy());
    }
    
    @Override
    public String getCommandString(T transformedIngredient) {
        
        if(this.amount == 1) {
            return transformedIngredient.getCommandString() + ".transformDamage()";
        }
        return String.format("%s.transformDamage(%s)", transformedIngredient.getCommandString(), amount);
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    public IIngredientTransformerSerializer getSerializer() {
        
        return TransformDamageSerializer.INSTANCE;
    }
    
    
}
