package com.blamejared.crafttweaker.mixin.common.access.entity;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AttributeModifier.class)
public interface AccessAttributeModifier {
    
    @Accessor("name")
    String crafttweaker$getName();
    
}
