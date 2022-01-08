package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.MinMaxBounds;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/DoubleMinMaxBoundsPredicate")
@NativeTypeRegistration(value = MinMaxBounds.Doubles.class, zenCodeName = "crafttweaker.api.predicate.DoubleMinMaxBoundsPredicate")
public final class ExpandMinMaxBoundsDoubles {
    
    @ZenCodeType.StaticExpansionMethod
    public static MinMaxBounds.Doubles any() {
        
        return MinMaxBounds.Doubles.ANY;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MinMaxBounds.Doubles exactly(final double value) {
        
        return MinMaxBounds.Doubles.exactly(value);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MinMaxBounds.Doubles between(final double min, final double max) {
        
        return MinMaxBounds.Doubles.between(min, max);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MinMaxBounds.Doubles atLeast(final double min) {
        
        return MinMaxBounds.Doubles.atLeast(min);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MinMaxBounds.Doubles atMost(final double max) {
        
        return MinMaxBounds.Doubles.atMost(max);
    }
    
}
