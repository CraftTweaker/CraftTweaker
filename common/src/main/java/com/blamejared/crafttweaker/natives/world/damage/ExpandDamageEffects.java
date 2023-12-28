package com.blamejared.crafttweaker.natives.world.damage;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageEffects;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/damage/DamageEffects")
@NativeTypeRegistration(value = DamageEffects.class, zenCodeName = "crafttweaker.api.world.damage.DamageEffects")
@BracketEnum("minecraft:damage/damage_effects")
public class ExpandDamageEffects {
    
    @ZenCodeType.Getter("sound")
    public static SoundEvent sound(DamageEffects internal) {
        
        return internal.sound();
    }
    
}
