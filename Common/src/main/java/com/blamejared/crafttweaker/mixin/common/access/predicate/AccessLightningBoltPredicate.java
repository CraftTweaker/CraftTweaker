package com.blamejared.crafttweaker.mixin.common.access.predicate;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.LighthingBoltPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LighthingBoltPredicate.class)
public interface AccessLightningBoltPredicate {
    
    @Invoker("<init>")
    static LighthingBoltPredicate crafttweaker$of(final MinMaxBounds.Ints ints, final EntityPredicate entityPredicate) {
        
        throw new UnsupportedOperationException();
    }
    
}
