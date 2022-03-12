package com.blamejared.crafttweaker.mixin.common.access.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.class)
public interface AccessBlockBehaviour {
    
    @Accessor("friction")
    float crafttweaker$getFriction();
    
    @Mutable
    @Accessor("friction")
    void crafttweaker$setFriction(float value);
    
    @Accessor("speedFactor")
    float crafttweaker$getSpeedFactor();
    
    @Mutable
    @Accessor("speedFactor")
    void crafttweaker$setSpeedFactor(float value);
    
    @Accessor("jumpFactor")
    float crafttweaker$getJumpFactor();
    
    @Mutable
    @Accessor("jumpFactor")
    void crafttweaker$setJumpFactor(float value);
    
    @Accessor("hasCollision")
    boolean crafttweaker$getHasCollision();
    
    @Mutable
    @Accessor("hasCollision")
    void crafttweaker$setHasCollision(boolean value);
    
    @Accessor("explosionResistance")
    float crafttweaker$getExplosionResistance();
    
    @Mutable
    @Accessor("explosionResistance")
    void crafttweaker$setExplosionResistance(float value);
    
    @Accessor("material")
    Material crafttweaker$getMaterial();
    
    @Mutable
    @Accessor("material")
    void crafttweaker$setMaterial(Material value);
    
}

