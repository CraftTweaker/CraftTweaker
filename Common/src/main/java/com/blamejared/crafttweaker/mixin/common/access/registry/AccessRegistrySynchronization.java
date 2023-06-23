package com.blamejared.crafttweaker.mixin.common.access.registry;

import net.minecraft.core.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.stream.Stream;

@Mixin(RegistrySynchronization.class)
public interface AccessRegistrySynchronization {
    
    @Invoker("ownedNetworkableRegistries")
    static Stream<RegistryAccess.RegistryEntry<?>> crafttweaker$callOwnedNetworkableRegistries(RegistryAccess $$0) {
        
        throw new UnsupportedOperationException();
    }
    
}
