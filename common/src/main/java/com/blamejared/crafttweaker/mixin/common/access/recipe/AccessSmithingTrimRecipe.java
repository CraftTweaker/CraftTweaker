package com.blamejared.crafttweaker.mixin.common.access.recipe;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingTrimRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SmithingTrimRecipe.class)
public interface AccessSmithingTrimRecipe {
    
    @Invoker("<init>")
    static SmithingTrimRecipe crafttweaker$createSmithingTrimRecipe(Ingredient $$0, Ingredient $$1, Ingredient $$2) {throw new UnsupportedOperationException();}
    
    @Accessor("template")
    Ingredient crafttweaker$getTemplate();
    
    @Accessor("base")
    Ingredient crafttweaker$getBase();
    
    @Accessor("addition")
    Ingredient crafttweaker$getAddition();
    
}
