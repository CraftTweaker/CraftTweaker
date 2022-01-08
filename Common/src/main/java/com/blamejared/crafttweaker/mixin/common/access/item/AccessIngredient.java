package com.blamejared.crafttweaker.mixin.common.access.item;

import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.stream.Stream;

@Mixin(Ingredient.class)
public interface AccessIngredient {
    
    @Invoker
    static Ingredient callFromValues(Stream<? extends Ingredient.Value> param0) {throw new UnsupportedOperationException();}
    
    @Accessor("values")
    Ingredient.Value[] getValues();
    
}
