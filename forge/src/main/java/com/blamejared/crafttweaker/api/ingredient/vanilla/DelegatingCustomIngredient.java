package com.blamejared.crafttweaker.api.ingredient.vanilla;

import com.blamejared.crafttweaker.api.ingredient.vanilla.type.CraftTweakerVanillaIngredient;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.ingredients.AbstractIngredient;
import net.minecraftforge.common.crafting.ingredients.IIngredientSerializer;

import java.util.Objects;

@MethodsReturnNonnullByDefault
class DelegatingCustomIngredient<T extends CraftTweakerVanillaIngredient> extends AbstractIngredient {
    
    private final T internal;
    
    DelegatingCustomIngredient(T internal) {
        
        this.internal = internal;
    }
    
    @Override
    public boolean test(ItemStack stack) {
        
        return internal.test(stack);
    }
    
    @Override
    public ItemStack[] getItems() {
        
        return internal.getMatchingStacks().toArray(ItemStack[]::new);
    }
    
    @Override
    public boolean isSimple() {
        
        return !internal.requiresTesting();
    }
    
    @Override
    public boolean isEmpty() {
        
        return internal.isEmpty();
    }
    
    @Override
    public IIngredientSerializer<? extends Ingredient> serializer() {
        
        return CraftTweakerIngredients.Serializers.of(internal.serializer());
    }
    
    public T internal() {
        
        return internal;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(getClass() != o.getClass()) {
            return false;
        }
        if(!super.equals(o)) {
            return false;
        }
        DelegatingCustomIngredient<?> that = (DelegatingCustomIngredient<?>) o;
        return Objects.equals(internal, that.internal);
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(internal);
    }
    
    @Override
    public String toString() {
        
        final StringBuilder sb = new StringBuilder("DelegatingCustomIngredient{");
        sb.append("internal=").append(internal);
        sb.append('}');
        return sb.toString();
    }
    
}
