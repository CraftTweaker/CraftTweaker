package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/FluidPredicate")
@NativeTypeRegistration(value = FluidPredicate.class, zenCodeName = "crafttweaker.api.predicate.FluidPredicate")
public final class ExpandFluidPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static FluidPredicate any() {
        
        return FluidPredicate.ANY;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static FluidPredicate.Builder create() {
        
        return FluidPredicate.Builder.fluid();
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static FluidPredicate.Builder create(final Fluid fluid) {
        
        return create().of(fluid);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static FluidPredicate.Builder create(final MCTag<Fluid> tag) {
        
        return create().of(tag.getInternal());
    }
    
}
