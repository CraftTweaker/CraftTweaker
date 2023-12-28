package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Optional;

@ZenRegister
@Document("vanilla/api/predicate/MobEffectPredicate")
@NativeTypeRegistration(value = MobEffectsPredicate.MobEffectInstancePredicate.class, zenCodeName = "crafttweaker.api.predicate.MobEffectPredicate")
public final class ExpandMobEffectsPredicateMobEffectInstancePredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static MobEffectsPredicate.MobEffectInstancePredicate any() {
        
        return new MobEffectsPredicate.MobEffectInstancePredicate();
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MobEffectsPredicate.MobEffectInstancePredicate create(final MinMaxBounds.Ints amplifier, final MinMaxBounds.Ints duration, @ZenCodeType.Nullable final Boolean ambient, @ZenCodeType.Nullable final Boolean visible) {
        
        return new MobEffectsPredicate.MobEffectInstancePredicate(amplifier, duration, Optional.ofNullable(ambient), Optional.ofNullable(visible));
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MobEffectsPredicate.MobEffectInstancePredicate create(final MinMaxBounds.Ints amplifier, final MinMaxBounds.Ints duration, final Boolean ambient) {
        
        return create(amplifier, duration, ambient, null);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MobEffectsPredicate.MobEffectInstancePredicate create(final MinMaxBounds.Ints amplifier, final MinMaxBounds.Ints duration) {
        
        return create(amplifier, duration, null, null);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MobEffectsPredicate.MobEffectInstancePredicate ambient() {
        
        return create(MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, true, null);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MobEffectsPredicate.MobEffectInstancePredicate amplifier(final MinMaxBounds.Ints amplifier) {
        
        return create(amplifier, MinMaxBounds.Ints.ANY, null, null);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MobEffectsPredicate.MobEffectInstancePredicate duration(final MinMaxBounds.Ints duration) {
        
        return create(MinMaxBounds.Ints.ANY, duration, null, null);
    }
    
}
