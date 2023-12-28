package com.blamejared.crafttweaker.mixin;

import com.google.common.collect.BiMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootContextParamSets.class)
public interface AccessLootContextParamSets {
    
    @Accessor("REGISTRY")
    static BiMap<ResourceLocation, LootContextParamSet> crafttweaker$getREGISTRY() {throw new UnsupportedOperationException();}
    
}
