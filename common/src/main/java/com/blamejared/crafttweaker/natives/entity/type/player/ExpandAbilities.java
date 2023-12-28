package com.blamejared.crafttweaker.natives.entity.type.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Abilities;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/player/Abilities")
@NativeTypeRegistration(value = Abilities.class, zenCodeName = "crafttweaker.api.entity.type.player.Abilities")
public class ExpandAbilities {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("flyingSpeed")
    public static float getFlyingSpeed(Abilities internal) {
        
        return internal.getFlyingSpeed();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("flyingSpeed")
    public static void setFlyingSpeed(Abilities internal, float param0) {
        
        internal.setFlyingSpeed(param0);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("walkingSpeed")
    public static float getWalkingSpeed(Abilities internal) {
        
        return internal.getWalkingSpeed();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("walkingSpeed")
    public static void setWalkingSpeed(Abilities internal, float param0) {
        
        internal.setWalkingSpeed(param0);
    }
    
}
