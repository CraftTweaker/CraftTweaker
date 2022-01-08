package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.LightPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/LightPredicate")
@NativeTypeRegistration(value = LightPredicate.class, zenCodeName = "crafttweaker.api.predicate.LightPredicate")
public final class ExpandLightPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static LightPredicate any() {
        
        return LightPredicate.ANY;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LightPredicate.Builder create() {
        
        return LightPredicate.Builder.light();
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LightPredicate.Builder create(final MinMaxBounds.Ints level) {
        
        return LightPredicate.Builder.light().setComposite(level);
    }
    
}
