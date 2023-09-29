package com.blamejared.crafttweaker.mixin.common.access.predicate;

import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(StatePropertiesPredicate.Builder.class)
public interface AccessStatePropertiesPredicateBuilder {
    
    @Accessor("matchers")
    ImmutableList.Builder<StatePropertiesPredicate.PropertyMatcher> crafttweaker$getMatchers();
    
}
