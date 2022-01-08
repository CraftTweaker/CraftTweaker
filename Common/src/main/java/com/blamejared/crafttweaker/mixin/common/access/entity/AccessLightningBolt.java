package com.blamejared.crafttweaker.mixin.common.access.entity;

import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LightningBolt.class)
public interface AccessLightningBolt {
    
    @Accessor("visualOnly")
    boolean isVisualOnly();
    
}
