package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/DamageSourcePredicateBuilder")
@NativeTypeRegistration(value = DamageSourcePredicate.Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.DamageSourcePredicateBuilder")
public final class ExpandDamageSourcePredicateBuilder {
    
    @ZenCodeType.Method
    public static DamageSourcePredicate.Builder isProjectile(final DamageSourcePredicate.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean isProjectile) {
        
        return internal.isProjectile(isProjectile);
    }
    
    @ZenCodeType.Method
    public static DamageSourcePredicate.Builder isExplosion(final DamageSourcePredicate.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean isExplosion) {
        
        return internal.isExplosion(isExplosion);
    }
    
    @ZenCodeType.Method
    public static DamageSourcePredicate.Builder bypassesArmor(final DamageSourcePredicate.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean bypassesArmor) {
        
        return internal.bypassesArmor(bypassesArmor);
    }
    
    @ZenCodeType.Method
    public static DamageSourcePredicate.Builder bypassesInvulnerability(final DamageSourcePredicate.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean bypassesInvulnerability) {
        
        return internal.bypassesInvulnerability(bypassesInvulnerability);
    }
    
    @ZenCodeType.Method
    public static DamageSourcePredicate.Builder bypassesMagic(final DamageSourcePredicate.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean bypassesMagic) {
        
        return internal.bypassesMagic(bypassesMagic);
    }
    
    @ZenCodeType.Method
    public static DamageSourcePredicate.Builder isFire(final DamageSourcePredicate.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean isFire) {
        
        return internal.isFire(isFire);
    }
    
    @ZenCodeType.Method
    public static DamageSourcePredicate.Builder isMagic(final DamageSourcePredicate.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean isMagic) {
        
        return internal.isMagic(isMagic);
    }
    
    @ZenCodeType.Method
    public static DamageSourcePredicate.Builder isLightning(final DamageSourcePredicate.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean isLightning) {
        
        return internal.isLightning(isLightning);
    }
    
    @ZenCodeType.Method
    public static DamageSourcePredicate.Builder direct(final DamageSourcePredicate.Builder internal, final EntityPredicate entityPredicate) {
        
        return internal.direct(entityPredicate);
    }
    
    @ZenCodeType.Method
    public static DamageSourcePredicate.Builder direct(final DamageSourcePredicate.Builder internal, final EntityPredicate.Builder entityPredicate) {
        
        return internal.direct(entityPredicate);
    }
    
    @ZenCodeType.Method
    public static DamageSourcePredicate.Builder source(final DamageSourcePredicate.Builder internal, final EntityPredicate entityPredicate) {
        
        return internal.source(entityPredicate);
    }
    
    @ZenCodeType.Method
    public static DamageSourcePredicate.Builder source(final DamageSourcePredicate.Builder internal, final EntityPredicate.Builder entityPredicate) {
        
        return internal.source(entityPredicate);
    }
    
    @ZenCodeType.Method
    public static DamageSourcePredicate build(final DamageSourcePredicate.Builder internal) {
        
        return internal.build();
    }
    
}
