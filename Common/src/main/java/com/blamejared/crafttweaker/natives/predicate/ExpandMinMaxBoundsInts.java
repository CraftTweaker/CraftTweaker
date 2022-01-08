package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.MinMaxBounds;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/IntMinMaxBoundsPredicate")
@NativeTypeRegistration(value = MinMaxBounds.Ints.class, zenCodeName = "crafttweaker.api.predicate.IntMinMaxBoundsPredicate")
public final class ExpandMinMaxBoundsInts {
    
    @ZenCodeType.StaticExpansionMethod
    public static MinMaxBounds.Ints any() {
        
        return MinMaxBounds.Ints.ANY;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MinMaxBounds.Ints exactly(final int value) {
        
        return MinMaxBounds.Ints.exactly(value);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MinMaxBounds.Ints between(final int min, final int max) {
        
        return MinMaxBounds.Ints.between(min, max);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MinMaxBounds.Ints atLeast(final int min) {
        
        return MinMaxBounds.Ints.atLeast(min);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MinMaxBounds.Ints atMost(final int max) {
        
        return MinMaxBounds.Ints.atMost(max);
    }
    
}
