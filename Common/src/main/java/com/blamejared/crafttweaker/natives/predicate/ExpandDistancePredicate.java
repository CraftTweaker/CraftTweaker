package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/DistancePredicate")
@NativeTypeRegistration(value = DistancePredicate.class, zenCodeName = "crafttweaker.api.predicate.DistancePredicate")
public final class ExpandDistancePredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static DistancePredicate create(final MinMaxBounds.Doubles x, final MinMaxBounds.Doubles y, final MinMaxBounds.Doubles z,
                                           final MinMaxBounds.Doubles horizontal, final MinMaxBounds.Doubles absolute) {
        
        return new DistancePredicate(x, y, z, horizontal, absolute);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DistancePredicate verticalDistance(final MinMaxBounds.Doubles bounds) {
        
        return DistancePredicate.vertical(bounds);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DistancePredicate horizontalDistance(final MinMaxBounds.Doubles bounds) {
        
        return DistancePredicate.horizontal(bounds);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DistancePredicate absoluteDistance(final MinMaxBounds.Doubles bounds) {
        
        return DistancePredicate.absolute(bounds);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DistancePredicate xyz(final MinMaxBounds.Doubles x, final MinMaxBounds.Doubles y, final MinMaxBounds.Doubles z) {
        
        return create(x, y, z, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY);
    }
    
}
