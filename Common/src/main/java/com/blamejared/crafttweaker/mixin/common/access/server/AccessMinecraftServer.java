package com.blamejared.crafttweaker.mixin.common.access.server;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftServer.class)
public interface AccessMinecraftServer {
    
    @Accessor("resources")
    MinecraftServer.ReloadableResources crafttweaker$getResources();
    
    
}
