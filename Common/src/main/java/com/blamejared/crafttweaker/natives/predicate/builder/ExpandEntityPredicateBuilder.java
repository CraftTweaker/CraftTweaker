package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.PlayerPredicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/EntityPredicateBuilder")
@NativeTypeRegistration(value = EntityPredicate.Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.EntityPredicateBuilder")
public final class ExpandEntityPredicateBuilder {
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder entityType(final EntityPredicate.Builder internal, final EntityTypePredicate predicate) {
        
        return internal.entityType(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder distance(final EntityPredicate.Builder internal, final DistancePredicate predicate) {
        
        return internal.distance(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder located(final EntityPredicate.Builder internal, final LocationPredicate predicate) {
        
        return internal.located(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder located(final EntityPredicate.Builder internal, final LocationPredicate.Builder predicate) {
        
        return located(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder steppingOn(final EntityPredicate.Builder internal, final LocationPredicate predicate) {
        
        return internal.steppingOn(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder steppingOn(final EntityPredicate.Builder internal, final LocationPredicate.Builder predicate) {
        
        return steppingOn(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder effects(final EntityPredicate.Builder internal, final MobEffectsPredicate predicate) {
        
        return internal.effects(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder nbt(final EntityPredicate.Builder internal, final NbtPredicate predicate) {
        
        return internal.nbt(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder flags(final EntityPredicate.Builder internal, final EntityFlagsPredicate predicate) {
        
        return internal.flags(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder flags(final EntityPredicate.Builder internal, final EntityFlagsPredicate.Builder predicate) {
        
        return flags(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder equipment(final EntityPredicate.Builder internal, final EntityEquipmentPredicate predicate) {
        
        return internal.equipment(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder equipment(final EntityPredicate.Builder internal, final EntityEquipmentPredicate.Builder predicate) {
        
        return equipment(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder subPredicate(final EntityPredicate.Builder internal, final EntitySubPredicate predicate) {
        
        return internal.subPredicate(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder subPredicate(final EntityPredicate.Builder internal, final PlayerPredicate.Builder predicate) {
        
        return subPredicate(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder vehicle(final EntityPredicate.Builder internal, final EntityPredicate predicate) {
        
        return internal.vehicle(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder vehicle(final EntityPredicate.Builder internal, final EntityPredicate.Builder predicate) {
        
        return vehicle(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder passenger(final EntityPredicate.Builder internal, final EntityPredicate predicate) {
        
        return internal.passenger(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder passenger(final EntityPredicate.Builder internal, final EntityPredicate.Builder predicate) {
        
        return passenger(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder target(final EntityPredicate.Builder internal, final EntityPredicate predicate) {
        
        return internal.targetedEntity(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder target(final EntityPredicate.Builder internal, final EntityPredicate.Builder predicate) {
        
        return target(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static EntityPredicate.Builder team(final EntityPredicate.Builder internal, final String team) {
        
        return internal.team(team);
    }
    
    @ZenCodeType.Method
    public static EntityPredicate build(final EntityPredicate.Builder internal) {
        
        return internal.build();
    }
    
}
