package com.blamejared.crafttweaker.mixin.common.access.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.class)
public interface AccessBlockBehaviour {
    
    @Accessor("friction")
    float getFriction();
    
    @Mutable
    @Accessor("friction")
    void setFriction(float value);
    
    @Accessor("speedFactor")
    float getSpeedFactor();
    
    @Mutable
    @Accessor("speedFactor")
    void setSpeedFactor(float value);
    
    @Accessor("jumpFactor")
    float getJumpFactor();
    
    @Mutable
    @Accessor("jumpFactor")
    void setJumpFactor(float value);
    
    @Accessor("hasCollision")
    boolean getHasCollision();
    
    @Mutable
    @Accessor("hasCollision")
    void setHasCollision(boolean value);
    
    @Accessor("explosionResistance")
    float getExplosionResistance();
    
    @Mutable
    @Accessor("explosionResistance")
    void setExplosionResistance(float value);
    
    @Accessor("material")
    Material getMaterial();
    
    @Mutable
    @Accessor("material")
    void setMaterial(Material value);
    
}

