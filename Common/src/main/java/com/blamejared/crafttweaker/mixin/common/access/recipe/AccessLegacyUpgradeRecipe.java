package com.blamejared.crafttweaker.mixin.common.access.recipe;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.LegacyUpgradeRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LegacyUpgradeRecipe.class)
public interface AccessLegacyUpgradeRecipe {
    
    @Accessor("base")
    Ingredient crafttweaker$getBase();
    
    @Accessor("addition")
    Ingredient crafttweaker$getAddition();
    
}
