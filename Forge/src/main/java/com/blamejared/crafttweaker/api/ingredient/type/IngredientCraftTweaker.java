package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import java.util.Arrays;
import java.util.stream.Stream;

public abstract class IngredientCraftTweaker<T extends IIngredient> extends Ingredient implements IngredientCraftTweakerBase {
    
    private final T crtIngredient;
    
    protected IngredientCraftTweaker(T crtIngredient, Stream<? extends Value> itemLists) {
        
        super(itemLists);
        this.crtIngredient = crtIngredient;
    }
    
    protected IngredientCraftTweaker(T crtIngredient) {
        
        this(crtIngredient, getValues(crtIngredient.getItems()));
    }
    
    @Override
    public abstract IIngredientSerializer<? extends Ingredient> getSerializer();
    
    @Override
    public T getCrTIngredient() {
        
        return crtIngredient;
    }
    
    private static Stream<Value> getValues(IItemStack[] items) {
        
        // TODO This may cause issues since we have such a big value array, it needs more investigation
        return Arrays.stream(items)
                .map(IIngredient::getItems)
                .flatMap(Arrays::stream)
                .map(IItemStack::getInternal)
                .map(ItemValue::new);
    }
    
    @Override
    public boolean isSimple() {
        
        return IngredientCraftTweakerBase.super.isSimple();
    }
    
}
