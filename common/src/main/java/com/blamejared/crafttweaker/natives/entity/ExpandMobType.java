package com.blamejared.crafttweaker.natives.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.MobType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/MobType")
@NativeTypeRegistration(value = MobType.class, zenCodeName = "crafttweaker.api.entity.MobType")
public class ExpandMobType {
    
    @ZenCodeType.StaticExpansionMethod
    public static MobType getUndefined() {
        
        return MobType.UNDEFINED;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MobType getUndead() {
        
        return MobType.UNDEAD;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MobType getArthropod() {
        
        return MobType.ARTHROPOD;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MobType getIllager() {
        
        return MobType.ILLAGER;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MobType getWater() {
        
        return MobType.WATER;
    }
    
}
