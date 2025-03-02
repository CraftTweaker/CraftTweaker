package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/EntityEquipmentPredicate")
@NativeTypeRegistration(value = EntityEquipmentPredicate.class, zenCodeName = "crafttweaker.api.predicate.EntityEquipmentPredicate")
public final class ExpandEntityEquipmentPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static EntityEquipmentPredicate captain() {
        
        return EntityEquipmentPredicate.captainPredicate(CraftTweakerAPI.getAccessibleElementsProvider()
                .lookupOrThrow(Registries.BANNER_PATTERN));
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static EntityEquipmentPredicate.Builder create() {
        
        return EntityEquipmentPredicate.Builder.equipment();
    }
    
    @ZenCodeType.Method
    public static boolean matches(EntityEquipmentPredicate internal, @ZenCodeType.Nullable Entity entity) {
        
        return internal.matches(entity);
    }
    
}
