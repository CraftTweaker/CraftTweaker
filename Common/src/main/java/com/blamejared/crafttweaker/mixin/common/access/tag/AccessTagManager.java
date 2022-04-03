package com.blamejared.crafttweaker.mixin.common.access.tag;

import net.minecraft.core.RegistryAccess;
import net.minecraft.tags.TagManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TagManager.class)
public interface AccessTagManager {
    
    @Accessor("registryAccess")
    RegistryAccess crafttweaker$getRegistryAccess();
    
}
