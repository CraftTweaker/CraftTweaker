package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.world.effect.MobEffect;
import org.openzen.zencode.java.ZenCodeType;

import java.util.LinkedHashMap;
import java.util.Map;

@ZenRegister
@Document("vanilla/api/predicate/MobEffectsPredicate")
@NativeTypeRegistration(value = MobEffectsPredicate.class, zenCodeName = "crafttweaker.api.predicate.MobEffectsPredicate")
public final class ExpandMobEffectsPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static MobEffectsPredicate any() {
        
        return MobEffectsPredicate.ANY;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MobEffectsPredicate create(final Map<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate> map) {
        
        return new MobEffectsPredicate(map);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MobEffectsPredicate create(final MobEffect effect, final MobEffectsPredicate.MobEffectInstancePredicate predicate) {
        
        return new MobEffectsPredicate(new LinkedHashMap<>(Map.of(effect, predicate))); // To keep parity with vanilla
    }
    
}
