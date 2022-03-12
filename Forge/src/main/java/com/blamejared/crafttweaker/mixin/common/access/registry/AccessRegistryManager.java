package com.blamejared.crafttweaker.mixin.common.access.registry;

import com.google.common.collect.BiMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = net.minecraftforge.registries.RegistryManager.class, remap = false)
public interface AccessRegistryManager {
    
    @Accessor("registries")
    BiMap<ResourceLocation, ForgeRegistry<? extends IForgeRegistryEntry<?>>> crafttweaker$getRegistries();
    
}
