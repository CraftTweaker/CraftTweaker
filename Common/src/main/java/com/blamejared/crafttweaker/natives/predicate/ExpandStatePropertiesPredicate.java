package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/StatePropertiesPredicate")
@NativeTypeRegistration(value = StatePropertiesPredicate.class, zenCodeName = "crafttweaker.api.predicate.StatePropertiesPredicate")
public final class ExpandStatePropertiesPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static StatePropertiesPredicate any() {
        
        return StatePropertiesPredicate.ANY;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static StatePropertiesPredicate.Builder create() {
        
        return StatePropertiesPredicate.Builder.properties();
    }
    
}
