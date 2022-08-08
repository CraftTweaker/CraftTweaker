package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientAnySerializer;
import com.faux.ingredientextension.api.ingredient.IngredientExtendable;
import com.faux.ingredientextension.api.ingredient.serializer.IIngredientSerializer;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class IngredientAny extends IngredientExtendable implements IngredientSingleton<IIngredientAny> {
    
    public static final IngredientAny INSTANCE = new IngredientAny();
    
    protected IngredientAny() {
        
        super(Registry.ITEM
                .stream()
                .map(Item::getDefaultInstance)
                .filter(Predicate.not(ItemStack::isEmpty))
                .toArray(ItemStack[]::new));
    }
    
    @Override
    public boolean test(@Nullable ItemStack stack) {
        
        return stack != null && !stack.isEmpty();
    }
    
    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        
        return IngredientAnySerializer.INSTANCE;
    }
    
    @Override
    public IIngredientAny getInstance() {
        
        return IIngredientAny.INSTANCE;
    }
    
}
