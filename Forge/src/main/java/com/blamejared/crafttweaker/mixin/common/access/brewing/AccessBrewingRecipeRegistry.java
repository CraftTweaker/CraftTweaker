package com.blamejared.crafttweaker.mixin.common.access.brewing;

import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(BrewingRecipeRegistry.class)
public interface AccessBrewingRecipeRegistry {
    
    @Accessor(value = "recipes", remap = false)
    static List<IBrewingRecipe> getRecipes() {
        
        throw new UnsupportedOperationException();
    }
    
}
