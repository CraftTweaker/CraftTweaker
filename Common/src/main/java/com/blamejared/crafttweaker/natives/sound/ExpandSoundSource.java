package com.blamejared.crafttweaker.natives.sound;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.sounds.SoundSource;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/sound/SoundSource")
@NativeTypeRegistration(value = SoundSource.class, zenCodeName = "crafttweaker.api.sound.SoundSource")
@BracketEnum("minecraft:sound/source")
public class ExpandSoundSource {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(SoundSource internal) {
        
        return internal.getName();
    }
    
}
