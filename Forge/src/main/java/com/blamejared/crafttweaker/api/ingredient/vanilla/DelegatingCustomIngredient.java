package com.blamejared.crafttweaker.api.ingredient.vanilla;

import com.blamejared.crafttweaker.api.ingredient.vanilla.type.CraftTweakerVanillaIngredient;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.ingredients.AbstractIngredient;
import net.minecraftforge.common.crafting.ingredients.IIngredientSerializer;

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
    public IIngredientSerializer<? extends Ingredient> serializer() {
        
        return CraftTweakerIngredients.Serializers.of(internal.serializer());
    }
    
    public T internal() {
        
        return internal;
    }
    
}
