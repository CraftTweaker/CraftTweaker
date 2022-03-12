package com.blamejared.crafttweaker.mixin.common.access.tag;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.StaticTagHelper;
import net.minecraft.tags.StaticTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Set;

@Mixin(StaticTags.class)
public interface AccessStaticTags {
    
    @Accessor("HELPERS_IDS")
    static Set<ResourceKey<?>> crafttweaker$getHELPERS_IDS() {throw new UnsupportedOperationException();}
    
    @Accessor("HELPERS")
    static List<StaticTagHelper<?>> crafttweaker$getHELPERS() {throw new UnsupportedOperationException();}
    
}
