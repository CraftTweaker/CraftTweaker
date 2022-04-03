package com.blamejared.crafttweaker.mixin.common.access.item;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BucketItem.class)
public interface AccessBucketItem {
    
    @Accessor("content")
    Fluid crafttweaker$getContent();
    
}
