package com.blamejared.crafttweaker.natives.util;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/HitResult")
@NativeTypeRegistration(value = HitResult.class, zenCodeName = "crafttweaker.api.util.HitResult")
public class ExpandHitResult {
    
    @ZenCodeType.Method
    public static double distanceTo(HitResult internal, Entity entity) {
        
        return internal.distanceTo(entity);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("type")
    public static HitResult.Type getType(HitResult internal) {
        
        return internal.getType();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("location")
    public static Vec3 getLocation(HitResult internal) {
        
        return internal.getLocation();
    }
    
}
