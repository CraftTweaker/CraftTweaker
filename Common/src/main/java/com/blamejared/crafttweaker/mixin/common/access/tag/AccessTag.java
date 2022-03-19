package com.blamejared.crafttweaker.mixin.common.access.tag;

import net.minecraft.tags.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Tag.class)
public interface AccessTag {
    
    @Accessor("elements")
    List crafttweaker$getElements();
    
    @Mutable
    @Accessor("elements")
    void crafttweaker$setElements(List elements);
    
}
