package com.blamejared.crafttweaker.mixin.common.access.loot;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(LootContext.class)
public interface AccessLootContext {
    
    @Accessor("params")
    Map<LootContextParam<?>, Object> crafttweaker$getParams();
    
    @Accessor("dynamicDrops")
    Map<ResourceLocation, LootContext.DynamicDrop> crafttweaker$getDynamicDrops();
    
}
