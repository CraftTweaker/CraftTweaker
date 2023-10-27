package com.blamejared.crafttweaker.mixin.common.access.loot;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifierManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = LootModifierManager.class, remap = false)
public interface AccessLootModifierManager {
    
    @Accessor(value = "modifiers", remap = false)
    Map<ResourceLocation, IGlobalLootModifier> crafttweaker$getModifiers();
    
    @Accessor(value = "modifiers", remap = false)
    void crafttweaker$setModifiers(final Map<ResourceLocation, IGlobalLootModifier> map);
}
