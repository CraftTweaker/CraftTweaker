package com.blamejared.crafttweaker.mixin.common.access.tag;

import net.minecraft.tags.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Tag.class)
public interface AccessTag {
    
    @Accessor("elements")
    <T> List<T> crafttweaker$getElements();
    
    @Mutable
    @Accessor("elements")
    <T> void crafttweaker$setElements(List<T> elements);
    
}
