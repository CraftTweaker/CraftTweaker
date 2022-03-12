package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.predicate.AccessStatePropertiesPredicateBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenRegister
@Document("vanilla/api/predicate/builder/StatePropertiesPredicateBuilder")
@NativeTypeRegistration(value = StatePropertiesPredicate.Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.StatePropertiesPredicateBuilder")
public final class ExpandStatePropertiesPredicateBuilder {
    
    @ZenCodeType.Method
    public static StatePropertiesPredicate.Builder property(final StatePropertiesPredicate.Builder internal, final String name, final String value) {
        
        return matching(internal, new StatePropertiesPredicate.ExactPropertyMatcher(name, value));
    }
    
    @ZenCodeType.Method
    public static StatePropertiesPredicate.Builder property(final StatePropertiesPredicate.Builder internal, final String name, final int value) {
        
        return matching(internal, new StatePropertiesPredicate.ExactPropertyMatcher(name, Integer.toString(value)));
    }
    
    @ZenCodeType.Method
    public static StatePropertiesPredicate.Builder property(final StatePropertiesPredicate.Builder internal, final String name, final MinMaxBounds.Ints value) {
        
        return matching(internal, new StatePropertiesPredicate.RangedPropertyMatcher(name, Objects.toString(value.getMin()), Objects.toString(value.getMax())));
    }
    
    @ZenCodeType.Method
    public static StatePropertiesPredicate.Builder property(final StatePropertiesPredicate.Builder internal, final String name, final boolean value) {
        
        return matching(internal, new StatePropertiesPredicate.ExactPropertyMatcher(name, Boolean.toString(value)));
    }
    
    @ZenCodeType.Method
    public static StatePropertiesPredicate build(final StatePropertiesPredicate.Builder internal) {
        
        return internal.build();
    }
    
    private static StatePropertiesPredicate.Builder matching(final StatePropertiesPredicate.Builder internal, final StatePropertiesPredicate.PropertyMatcher matcher) {
        
        ((AccessStatePropertiesPredicateBuilder) internal).crafttweaker$getMatchers().add(matcher);
        return internal;
    }
    
}
