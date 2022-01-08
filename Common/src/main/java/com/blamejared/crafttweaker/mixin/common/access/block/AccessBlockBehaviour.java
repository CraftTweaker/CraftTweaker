package com.blamejared.crafttweaker.mixin.common.access.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.class)
public interface AccessBlockBehaviour {
    
    @Accessor("friction")
    float getFriction();
    
    @Accessor("friction")
    void setFriction(float value);
    
    @Accessor("speedFactor")
    float getSpeedFactor();
    
    @Accessor("speedFactor")
    void setSpeedFactor(float value);
    
    @Accessor("jumpFactor")
    float getJumpFactor();
    
    @Accessor("jumpFactor")
    void setJumpFactor(float value);
    
    @Accessor("hasCollision")
    boolean getHasCollision();
    
    @Accessor("hasCollision")
    void setHasCollision(boolean value);
    
    @Accessor("explosionResistance")
    float getExplosionResistance();
    
    @Accessor("explosionResistance")
    void setExplosionResistance(float value);
    
    @Accessor("material")
    Material getMaterial();
    
    @Accessor("material")
    void setMaterial(Material value);
    
}

