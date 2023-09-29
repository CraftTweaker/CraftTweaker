package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import org.openzen.zencode.java.ZenCodeType;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ZenRegister
@Document("vanilla/api/predicate/MobEffectsPredicate")
@NativeTypeRegistration(value = MobEffectsPredicate.class, zenCodeName = "crafttweaker.api.predicate.MobEffectsPredicate")
public final class ExpandMobEffectsPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static MobEffectsPredicate create(final Map<MobEffect, MobEffectsPredicate.MobEffectInstancePredicate> map) {
        
        return new MobEffectsPredicate(map.entrySet()
                .stream()
                .map(entry -> Map.entry(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(entry.getKey()), entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MobEffectsPredicate create(final MobEffect effect, final MobEffectsPredicate.MobEffectInstancePredicate predicate) {
        
        return new MobEffectsPredicate(new LinkedHashMap<>(Map.of(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect), predicate))); // To keep parity with vanilla
    }
    
}
