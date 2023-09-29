package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/EntityFlagsPredicate")
@NativeTypeRegistration(value = EntityFlagsPredicate.class, zenCodeName = "crafttweaker.api.predicate.EntityFlagsPredicate")
public final class ExpandEntityFlagsPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static EntityFlagsPredicate.Builder create() {
        
        return EntityFlagsPredicate.Builder.flags();
    }
    
}
