package com.blamejared.crafttweaker.mixin.common.access.loot;

import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(LootContext.class)
public interface AccessLootContext {
    
    @Accessor("params")
    LootParams crafttweaker$getParams();
    
}
