package com.blamejared.crafttweaker.mixin.common.access.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.BlockStateBase.class)
public interface AccessBlockStateBase {
    
    @Accessor("lightEmission")
    int crafttweaker$getLightEmission();
    
    @Mutable
    @Accessor("lightEmission")
    void crafttweaker$setLightEmission(int lightEmission);
    
    @Accessor("useShapeForLightOcclusion")
    boolean crafttweaker$isUseShapeForLightOcclusion();
    
    @Mutable
    @Accessor("useShapeForLightOcclusion")
    void crafttweaker$setUseShapeForLightOcclusion(boolean useShapeForLightOcclusion);
    
    @Accessor("isAir")
    boolean crafttweaker$isIsAir();
    
    @Mutable
    @Accessor("isAir")
    void crafttweaker$setIsAir(boolean isAir);
    
    @Accessor("destroySpeed")
    float crafttweaker$getDestroySpeed();
    
    @Mutable
    @Accessor("destroySpeed")
    void crafttweaker$setDestroySpeed(float destroySpeed);
    
    @Accessor("requiresCorrectToolForDrops")
    boolean crafttweaker$isRequiresCorrectToolForDrops();
    
    @Mutable
    @Accessor("requiresCorrectToolForDrops")
    void crafttweaker$setRequiresCorrectToolForDrops(boolean requiresCorrectToolForDrops);
    
    @Accessor("canOcclude")
    boolean crafttweaker$isCanOcclude();
    
    @Mutable
    @Accessor("canOcclude")
    void crafttweaker$setCanOcclude(boolean canOcclude);
    
}
