package com.blamejared.crafttweaker.natives.util;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/EntityHitResult")
@NativeTypeRegistration(value = EntityHitResult.class, zenCodeName = "crafttweaker.api.util.EntityHitResult")
public class ExpandEntityHitResult {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("type")
    public static Entity getEntity(EntityHitResult internal) {
        
        return internal.getEntity();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("type")
    public static HitResult.Type getType(EntityHitResult internal) {
        
        return internal.getType();
    }
    
}
