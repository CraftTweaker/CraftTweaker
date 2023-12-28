package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.LightPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/LightPredicateBuilder")
@NativeTypeRegistration(value = LightPredicate.Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.LightPredicateBuilder")
public final class ExpandLightPredicateBuilder {
    
    @ZenCodeType.Method
    public static LightPredicate.Builder composite(final LightPredicate.Builder internal, final MinMaxBounds.Ints composite) {
        
        return internal.setComposite(composite);
    }
    
    @ZenCodeType.Method
    public static LightPredicate build(final LightPredicate.Builder internal) {
        
        return internal.build();
    }
    
}
