package com.blamejared.crafttweaker.mixin.common.access.entity;

import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FallingBlockEntity.class)
public interface AccessFallingBlockEntity {
    
    @Accessor("blockState")
    void crafttweaker$setBlockState(BlockState value);
    
}
