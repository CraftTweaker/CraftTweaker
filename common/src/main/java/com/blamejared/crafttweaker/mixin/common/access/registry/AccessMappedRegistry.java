package com.blamejared.crafttweaker.mixin.common.access.registry;

import net.minecraft.core.MappedRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MappedRegistry.class)
public interface AccessMappedRegistry {
    
    @Accessor("frozen")
    boolean crafttweaker$isFrozen();
    
    @Accessor("frozen")
    void crafttweaker$setFrozen(boolean frozen);
    
}
