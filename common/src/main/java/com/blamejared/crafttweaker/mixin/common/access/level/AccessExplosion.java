package com.blamejared.crafttweaker.mixin.common.access.level;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Explosion.class)
public interface AccessExplosion {
    
    @Accessor("damageSource")
    DamageSource crafttweaker$getDamageSource();
    
}
