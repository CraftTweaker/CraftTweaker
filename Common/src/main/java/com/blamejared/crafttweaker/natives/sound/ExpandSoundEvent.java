package com.blamejared.crafttweaker.natives.sound;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/sound/SoundEvent")
@NativeTypeRegistration(value = SoundEvent.class, zenCodeName = "crafttweaker.api.sound.SoundEvent")
public class ExpandSoundEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("location")
    public static ResourceLocation getLocation(SoundEvent internal) {
        
        return internal.getLocation();
    }
    
}
