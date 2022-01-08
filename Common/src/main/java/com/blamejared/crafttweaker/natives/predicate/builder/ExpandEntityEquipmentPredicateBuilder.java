package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/EntityEquipmentPredicateBuilder")
@NativeTypeRegistration(value = EntityEquipmentPredicate.Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.EntityEquipmentPredicateBuilder")
public final class ExpandEntityEquipmentPredicateBuilder {
    
    @ZenCodeType.Method
    public static EntityEquipmentPredicate.Builder head(final EntityEquipmentPredicate.Builder internal, final ItemPredicate predicate) {
        
        return internal.head(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityEquipmentPredicate.Builder head(final EntityEquipmentPredicate.Builder internal, final ItemPredicate.Builder predicate) {
        
        return head(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static EntityEquipmentPredicate.Builder chest(final EntityEquipmentPredicate.Builder internal, final ItemPredicate predicate) {
        
        return internal.chest(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityEquipmentPredicate.Builder chest(final EntityEquipmentPredicate.Builder internal, final ItemPredicate.Builder predicate) {
        
        return chest(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static EntityEquipmentPredicate.Builder legs(final EntityEquipmentPredicate.Builder internal, final ItemPredicate predicate) {
        
        return internal.legs(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityEquipmentPredicate.Builder legs(final EntityEquipmentPredicate.Builder internal, final ItemPredicate.Builder predicate) {
        
        return legs(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static EntityEquipmentPredicate.Builder feet(final EntityEquipmentPredicate.Builder internal, final ItemPredicate predicate) {
        
        return internal.feet(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityEquipmentPredicate.Builder feet(final EntityEquipmentPredicate.Builder internal, final ItemPredicate.Builder predicate) {
        
        return feet(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static EntityEquipmentPredicate.Builder mainHand(final EntityEquipmentPredicate.Builder internal, final ItemPredicate predicate) {
        
        return internal.mainhand(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityEquipmentPredicate.Builder mainHand(final EntityEquipmentPredicate.Builder internal, final ItemPredicate.Builder predicate) {
        
        return mainHand(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static EntityEquipmentPredicate.Builder offHand(final EntityEquipmentPredicate.Builder internal, final ItemPredicate predicate) {
        
        return internal.offhand(predicate);
    }
    
    @ZenCodeType.Method
    public static EntityEquipmentPredicate.Builder offHand(final EntityEquipmentPredicate.Builder internal, final ItemPredicate.Builder predicate) {
        
        return offHand(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static EntityEquipmentPredicate build(final EntityEquipmentPredicate.Builder internal) {
        
        return internal.build();
    }
    
}
