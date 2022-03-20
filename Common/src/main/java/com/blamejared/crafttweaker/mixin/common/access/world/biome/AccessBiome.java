package com.blamejared.crafttweaker.mixin.common.access.world.biome;

import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Biome.class)
public interface AccessBiome {
    
    @Invoker("getBiomeCategory")
    Biome.BiomeCategory crafttweaker$callGetBiomeCategory();
    
}
