package com.blamejared.crafttweaker.impl_native.util.math;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.EntityRayTraceResult;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/EntityRayTraceResult")
@NativeTypeRegistration(value = EntityRayTraceResult.class, zenCodeName = "crafttweaker.api.util.math.EntityRayTraceResult")
public class ExpandEntityRayTraceResult {
    
    /**
     * Gets the entity that was hit by this result.
     *
     * @return The entity that was hit by this result.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("entity")
    public static Entity getEntity(EntityRayTraceResult internal) {
        
        return internal.getEntity();
    }
    
}
