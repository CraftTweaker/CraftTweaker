package com.blamejared.crafttweaker.mixin.common.access.forge;

import net.minecraftforge.common.ForgeInternalHandler;
import net.minecraftforge.common.loot.LootModifierManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = ForgeInternalHandler.class, remap = false)
public interface AccessForgeInternalHandler {
    
    @Invoker(value = "getLootModifierManager", remap = false)
    static LootModifierManager crafttweaker$getLootModifierManager() {
        
        throw new UnsupportedOperationException("Mixed in by Mixin");
    }
}
