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
        
        // Differently from other "any"s, this is still useful as it allows to specify that we want a player, with no
        // other condition. Blame Mojang for making stuff weird, I guess
        return create().build();
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static PlayerPredicate.Builder create() {
        
        return PlayerPredicate.Builder.player();
    }
    
}
