package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/LocationPredicate")
@NativeTypeRegistration(value = LocationPredicate.class, zenCodeName = "crafttweaker.api.predicate.LocationPredicate")
public final class ExpandLocationPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static LocationPredicate.Builder create() {
        
        return LocationPredicate.Builder.location();
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LocationPredicate at(final MinMaxBounds.Doubles x, final MinMaxBounds.Doubles y, final MinMaxBounds.Doubles z) {
        
        return create().setX(x).setY(y).setZ(z).build();
    }
    
}
