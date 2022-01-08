package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/DamageSourcePredicate")
@NativeTypeRegistration(value = DamageSourcePredicate.class, zenCodeName = "crafttweaker.api.predicate.DamageSourcePredicate")
public final class ExpandDamageSourcePredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSourcePredicate any() {
        
        return DamageSourcePredicate.ANY;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSourcePredicate.Builder create() {
        
        return DamageSourcePredicate.Builder.damageType();
    }
    
}
