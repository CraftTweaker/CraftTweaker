package com.blamejared.crafttweaker.mixin.common.access.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.stream.Stream;

@Mixin(Ingredient.class)
public interface AccessIngredient {
    
    @Invoker("fromValues")
    static Ingredient crafttweaker$callFromValues(Stream<? extends Ingredient.Value> param0) {throw new UnsupportedOperationException();}
    
    @Accessor("values")
    Ingredient.Value[] crafttweaker$getValues();
    
    @Accessor("itemStacks")
    ItemStack[] crafttweaker$getItemStacks();
    
    @Accessor("itemStacks")
    void crafttweaker$setItemStacks(ItemStack[] itemStacks);
    
}
