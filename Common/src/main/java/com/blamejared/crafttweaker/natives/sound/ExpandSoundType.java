package com.blamejared.crafttweaker.natives.sound;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/sound/SoundType")
@NativeTypeRegistration(value = SoundType.class, zenCodeName = "crafttweaker.api.sound.SoundType")
public class ExpandSoundType {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("volume")
    public static float getVolume(SoundType internal) {
        
        return internal.getVolume();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("pitch")
    public static float getPitch(SoundType internal) {
        
        return internal.getPitch();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("breakSound")
    public static SoundEvent getBreakSound(SoundType internal) {
        
        return internal.getBreakSound();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("stepSound")
    public static SoundEvent getStepSound(SoundType internal) {
        
        return internal.getStepSound();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("placeSound")
    public static SoundEvent getPlaceSound(SoundType internal) {
        
        return internal.getPlaceSound();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hitSound")
    public static SoundEvent getHitSound(SoundType internal) {
        
        return internal.getHitSound();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("fallSound")
    public static SoundEvent getFallSound(SoundType internal) {
        
        return internal.getFallSound();
    }
    
}
