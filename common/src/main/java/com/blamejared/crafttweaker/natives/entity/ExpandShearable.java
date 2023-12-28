package com.blamejared.crafttweaker.natives.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Shearable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/Shearable")
@NativeTypeRegistration(value = Shearable.class, zenCodeName = "crafttweaker.api.entity.Shearable")
public class ExpandShearable {
    
    @ZenCodeType.Method
    public static void shear(Shearable internal, SoundSource source) {
        
        internal.shear(source);
    }
    
    @ZenCodeType.Getter("readyForShearing")
    public static boolean readyForShearing(Shearable internal) {
        
        return internal.readyForShearing();
    }
    
}
