package com.blamejared.crafttweaker.impl_native.util.math;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/RayTraceResult")
@NativeTypeRegistration(value = RayTraceResult.class, zenCodeName = "crafttweaker.api.util.math.RayTraceResult")
public class ExpandRayTraceResult {
    
    /**
     * Gets the distance from this RayTraceResult to the given Entity.
     *
     * @param entity The entity to get the distance to.
     *
     * @return The distance from this result to the given entity.
     *
     * @docParam entity event.entity
     */
    @ZenCodeType.Method
    public static double distanceTo(RayTraceResult internal, Entity entity) {
        
        return internal.func_237486_a_(entity);
    }
    
    /**
     * Gets the type of this RayTraceResult. This can be used to determine if this is a BlockRayTraceResult, an EntityRayTraceResult or if the trace missed.
     *
     * @return The type of this RayTraceResult.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("type")
    public static RayTraceResult.Type getType(RayTraceResult internal) {
        
        return internal.getType();
    }
    
    /**
     * Gets the hit vector of this result.
     *
     * @return The hit vector of this result.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("hitVec")
    public static Vector3d getHitVec(RayTraceResult internal) {
        
        return internal.getHitVec();
    }
    
}
