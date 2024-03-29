package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.predicate.AccessStatePropertiesPredicateBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenRegister
@Document("vanilla/api/predicate/builder/StatePropertiesPredicateBuilder")
@NativeTypeRegistration(value = StatePropertiesPredicate.Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.StatePropertiesPredicateBuilder")
public final class ExpandStatePropertiesPredicateBuilder {
    
    @ZenCodeType.Method
    public static StatePropertiesPredicate build(final StatePropertiesPredicate.Builder internal) {
        
        return internal.build().orElseThrow(() -> new RuntimeException("Error while building StatePropertiesPredicate!"));
    }
    
    private static StatePropertiesPredicate.Builder matching(final StatePropertiesPredicate.Builder internal, final StatePropertiesPredicate.PropertyMatcher matcher) {
        
        ((AccessStatePropertiesPredicateBuilder) internal).crafttweaker$getMatchers().add(matcher);
        return internal;
    }
    
}
