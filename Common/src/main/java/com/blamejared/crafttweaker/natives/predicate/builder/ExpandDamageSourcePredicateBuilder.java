package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.TagPredicate;
import net.minecraft.world.damagesource.DamageType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/DamageSourcePredicateBuilder")
@NativeTypeRegistration(value = DamageSourcePredicate.Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.DamageSourcePredicateBuilder")
public final class ExpandDamageSourcePredicateBuilder {
    
    @ZenCodeType.Method
    public static DamageSourcePredicate.Builder tag(DamageSourcePredicate.Builder internal, TagPredicate<DamageType> tag) {
        
        return internal.tag(tag);
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
