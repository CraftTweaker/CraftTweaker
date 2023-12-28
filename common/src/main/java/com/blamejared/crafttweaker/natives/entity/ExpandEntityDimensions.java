package com.blamejared.crafttweaker.natives.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/EntityDimensions")
@NativeTypeRegistration(value = EntityDimensions.class, zenCodeName = "crafttweaker.api.entity.EntityDimensions")
public class ExpandEntityDimensions {
    
    @ZenCodeType.Method
    public static AABB makeBoundingBox(EntityDimensions internal, Vec3 vec) {
        
        return internal.makeBoundingBox(vec);
    }
    
    @ZenCodeType.Method
    public static AABB makeBoundingBox(EntityDimensions internal, double x, double y, double z) {
        
        return internal.makeBoundingBox(x, y, z);
    }
    
    @ZenCodeType.Method
    public static EntityDimensions scale(EntityDimensions internal, float factor) {
        
        return internal.scale(factor);
    }
    
    @ZenCodeType.Method
    public static EntityDimensions scale(EntityDimensions internal, float widthFactor, float heightFactor) {
        
        return internal.scale(widthFactor, heightFactor);
    }
    
}
