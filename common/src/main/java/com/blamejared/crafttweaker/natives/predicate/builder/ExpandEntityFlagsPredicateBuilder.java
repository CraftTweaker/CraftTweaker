package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/EntityFlagsPredicateBuilder")
@NativeTypeRegistration(value = EntityFlagsPredicate.Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.EntityFlagsPredicateBuilder")
public final class ExpandEntityFlagsPredicateBuilder {
    
    @ZenCodeType.Method
    public static EntityFlagsPredicate.Builder onFire(final EntityFlagsPredicate.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean onFire) {
        
        return internal.setOnFire(onFire);
    }
    
    @ZenCodeType.Method
    public static EntityFlagsPredicate.Builder crouching(final EntityFlagsPredicate.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean crouching) {
        
        return internal.setCrouching(crouching);
    }
    
    @ZenCodeType.Method
    public static EntityFlagsPredicate.Builder sprinting(final EntityFlagsPredicate.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean sprinting) {
        
        return internal.setSprinting(sprinting);
    }
    
    @ZenCodeType.Method
    public static EntityFlagsPredicate.Builder swimming(final EntityFlagsPredicate.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean swimming) {
        
        return internal.setSwimming(swimming);
    }
    
    @ZenCodeType.Method
    public static EntityFlagsPredicate.Builder baby(final EntityFlagsPredicate.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean baby) {
        
        return internal.setIsBaby(baby);
    }
    
    @ZenCodeType.Method
    public static EntityFlagsPredicate build(final EntityFlagsPredicate.Builder internal) {
        
        return internal.build();
    }
    
}
