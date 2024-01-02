package com.blamejared.crafttweaker.mixin.common.access.neoforge;

import net.neoforged.neoforge.common.NeoForgeEventHandler;
import net.neoforged.neoforge.common.loot.LootModifierManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@SuppressWarnings("UnstableApiUsage")
@Mixin(value = NeoForgeEventHandler.class, remap = false)
public interface AccessNeoForgeInternalHandler {
    
    @Invoker(value = "getLootModifierManager", remap = false)
    static LootModifierManager crafttweaker$getLootModifierManager() {
        
        throw new UnsupportedOperationException("Mixed in by Mixin");
    }
}
