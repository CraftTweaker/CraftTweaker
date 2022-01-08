package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/FluidPredicateBuilder")
@NativeTypeRegistration(value = FluidPredicate.Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.FluidPredicateBuilder")
public class ExpandFluidPredicateBuilder {
    
    @ZenCodeType.Method
    public static FluidPredicate.Builder blocks(final FluidPredicate.Builder internal, final Fluid fluid) {
        
        return internal.of(fluid);
    }
    
    @ZenCodeType.Method
    public static FluidPredicate.Builder tag(final FluidPredicate.Builder internal, final MCTag<Fluid> tag) {
        
        return internal.of(tag.getInternal());
    }
    
    @ZenCodeType.Method
    public static FluidPredicate.Builder properties(final FluidPredicate.Builder internal, final StatePropertiesPredicate predicate) {
        
        return internal.setProperties(predicate);
    }
    
    @ZenCodeType.Method
    public static FluidPredicate.Builder properties(final FluidPredicate.Builder internal, final StatePropertiesPredicate.Builder predicate) {
        
        return properties(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static FluidPredicate build(final FluidPredicate.Builder internal) {
        
        return internal.build();
    }
    
}
