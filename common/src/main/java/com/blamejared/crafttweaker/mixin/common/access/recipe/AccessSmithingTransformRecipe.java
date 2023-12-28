package com.blamejared.crafttweaker.mixin.common.access.recipe;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.item.crafting.SmithingTrimRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SmithingTransformRecipe.class)
public interface AccessSmithingTransformRecipe {
    
    @Accessor("template")
    Ingredient crafttweaker$getTemplate();
    
    @Accessor("base")
    Ingredient crafttweaker$getBase();
    
    @Accessor("addition")
    Ingredient crafttweaker$getAddition();
    
}
