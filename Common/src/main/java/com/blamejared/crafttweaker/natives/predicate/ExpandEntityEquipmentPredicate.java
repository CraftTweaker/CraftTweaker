package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/EntityEquipmentPredicate")
@NativeTypeRegistration(value = EntityEquipmentPredicate.class, zenCodeName = "crafttweaker.api.predicate.EntityEquipmentPredicate")
public final class ExpandEntityEquipmentPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static EntityEquipmentPredicate any() {
        
        return EntityEquipmentPredicate.ANY;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static EntityEquipmentPredicate captain() {
        
        return EntityEquipmentPredicate.CAPTAIN;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static EntityEquipmentPredicate.Builder create() {
        
        return EntityEquipmentPredicate.Builder.equipment();
    }
    
}
