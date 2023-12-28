package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/FishingHookPredicate")
@NativeTypeRegistration(value = FishingHookPredicate.class, zenCodeName = "crafttweaker.api.predicate.FishingHookPredicate")
public final class ExpandFishingHookPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static FishingHookPredicate any() {
        
        return FishingHookPredicate.ANY;
    }
    
    // No real way to have a 'create' method and follow other conventions
    
    @ZenCodeType.StaticExpansionMethod
    public static FishingHookPredicate inOpenWaters(@ZenCodeType.OptionalBoolean(true) final boolean inOpenWaters) {
        
        return FishingHookPredicate.inOpenWater(inOpenWaters);
    }
    
}
