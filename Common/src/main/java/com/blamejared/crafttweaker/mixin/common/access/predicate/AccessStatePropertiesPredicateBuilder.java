package com.blamejared.crafttweaker.mixin.common.access.predicate;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(StatePropertiesPredicate.Builder.class)
public interface AccessStatePropertiesPredicateBuilder {
    
    @Accessor
    List<Object> getMatchers();
    
}
