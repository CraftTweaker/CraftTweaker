package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import java.util.stream.Stream;

public abstract class IngredientCraftTweaker<T extends IIngredient> extends Ingredient implements IngredientCraftTweakerBase {
    
    private final T crtIngredient;
    
    protected IngredientCraftTweaker(T crtIngredient, Stream<? extends Value> itemLists) {
        
        super(itemLists);
        this.crtIngredient = crtIngredient;
    }
    
    protected IngredientCraftTweaker(T crtIngredient) {
        
        this(crtIngredient, IngredientCraftTweakerBase.getValues(crtIngredient));
    }
    
    @Override
    public abstract IIngredientSerializer<? extends Ingredient> getSerializer();
    
    @Override
    public T getCrTIngredient() {
        
        return crtIngredient;
    }
    
    @Override
    public boolean isSimple() {
        
        return IngredientCraftTweakerBase.super.isSimple();
    }
    
}
