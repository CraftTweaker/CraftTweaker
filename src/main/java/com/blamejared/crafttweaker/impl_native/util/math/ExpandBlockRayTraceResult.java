package com.blamejared.crafttweaker.impl_native.util.math;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/BlockRayTraceResult")
@NativeTypeRegistration(value = BlockRayTraceResult.class, zenCodeName = "crafttweaker.api.util.math.BlockRayTraceResult")
public class ExpandBlockRayTraceResult {
    
    /**
     * Returns a new BlockRayTraceResult based on this result with the given face.
     *
     * @param newFace The new face.
     *
     * @return a new BlockRayTraceResult based on this result with the given face.
     *
     * @docParam newFace MCDirection.north
     */
    @ZenCodeType.Method
    public static BlockRayTraceResult withFace(BlockRayTraceResult internal, MCDirection newFace) {
        
        return internal.withFace(newFace.getInternal());
    }
    
    /**
     * Returns a new BlockRayTraceResult based on this result with the given position.
     *
     * @param pos The new position.
     *
     * @return a new BlockRayTraceResult based on this result with the given position.
     *
     * @docParam pos new BlockPos(1, 2, 3)
     */
    @ZenCodeType.Method
    public static BlockRayTraceResult withPosition(BlockRayTraceResult internal, BlockPos pos) {
        
        return internal.withPosition(pos);
    }
    
    /**
     * Gets the position that was hit.
     *
     * @return The position that was hit.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("pos")
    public static BlockPos getPos(BlockRayTraceResult internal) {
        
        return internal.getPos();
    }
    
    /**
     * Gets the face that was hit.
     *
     * @return The face that was hit.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("face")
    public static MCDirection getFace(BlockRayTraceResult internal) {
        
        return MCDirection.get(internal.getFace());
    }
    
    /**
     * Results if the result is inside of a block.
     *
     * This is used for scaffolding.
     *
     * @return True if it is inside. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("inside")
    public static boolean isInside(BlockRayTraceResult internal) {
        
        return internal.isInside();
    }
    
}
