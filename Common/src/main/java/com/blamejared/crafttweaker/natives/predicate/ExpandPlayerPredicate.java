package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.PlayerPredicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/PlayerPredicate")
@NativeTypeRegistration(value = PlayerPredicate.class, zenCodeName = "crafttweaker.api.predicate.PlayerPredicate")
public final class ExpandPlayerPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static PlayerPredicate any() {
        
        return PlayerPredicate.ANY;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static PlayerPredicate.Builder create() {
        
        return PlayerPredicate.Builder.player();
    }
    
}
