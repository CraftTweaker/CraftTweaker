package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Rotation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/BlockPos")
@NativeTypeRegistration(value = BlockPos.class, zenCodeName = "crafttweaker.api.util.math.BlockPos")
public class ExpandBlockPos {
    
    @ZenCodeType.Method
    public static long asLong(BlockPos internal) {
        
        return internal.asLong();
    }
    
    @ZenCodeType.Method
    public static BlockPos offset(BlockPos internal, double x, double y, double z) {
        
        return internal.offset(x, y, z);
    }
    
    @ZenCodeType.Method
    public static BlockPos offset(BlockPos internal, int x, int y, int z) {
        
        return internal.offset(x, y, z);
    }
    
    @ZenCodeType.Method
    public static BlockPos offset(BlockPos internal, Vec3i other) {
        
        return internal.offset(other);
    }
    
    @ZenCodeType.Method
    public static BlockPos subtract(BlockPos internal, Vec3i other) {
        
        return internal.subtract(other);
    }
    
    @ZenCodeType.Method
    public static BlockPos multiply(BlockPos internal, int scalar) {
        
        return internal.multiply(scalar);
    }
    
    @ZenCodeType.Method
    public static BlockPos above(BlockPos internal) {
        
        return internal.above();
    }
    
    @ZenCodeType.Method
    public static BlockPos above(BlockPos internal, int distance) {
        
        return internal.above(distance);
    }
    
    @ZenCodeType.Method
    public static BlockPos below(BlockPos internal) {
        
        return internal.below();
    }
    
    @ZenCodeType.Method
    public static BlockPos below(BlockPos internal, int distance) {
        
        return internal.below(distance);
    }
    
    @ZenCodeType.Method
    public static BlockPos north(BlockPos internal) {
        
        return internal.north();
    }
    
    @ZenCodeType.Method
    public static BlockPos north(BlockPos internal, int distance) {
        
        return internal.north(distance);
    }
    
    @ZenCodeType.Method
    public static BlockPos south(BlockPos internal) {
        
        return internal.south();
    }
    
    @ZenCodeType.Method
    public static BlockPos south(BlockPos internal, int distance) {
        
        return internal.south(distance);
    }
    
    @ZenCodeType.Method
    public static BlockPos west(BlockPos internal) {
        
        return internal.west();
    }
    
    @ZenCodeType.Method
    public static BlockPos west(BlockPos internal, int distance) {
        
        return internal.west(distance);
    }
    
    @ZenCodeType.Method
    public static BlockPos east(BlockPos internal) {
        
        return internal.east();
    }
    
    @ZenCodeType.Method
    public static BlockPos east(BlockPos internal, int distance) {
        
        return internal.east(distance);
    }
    
    @ZenCodeType.Method
    public static BlockPos relative(BlockPos internal, Direction direction) {
        
        return internal.relative(direction);
    }
    
    @ZenCodeType.Method
    public static BlockPos relative(BlockPos internal, Direction direction, int distance) {
        
        return internal.relative(direction, distance);
    }
    
    @ZenCodeType.Method
    public static BlockPos relative(BlockPos internal, Direction.Axis axis, int distance) {
        
        return internal.relative(axis, distance);
    }
    
    @ZenCodeType.Method
    public static BlockPos rotate(BlockPos internal, Rotation rotation) {
        
        return internal.rotate(rotation);
    }
    
    @ZenCodeType.Method
    public static BlockPos cross(BlockPos internal, Vec3i other) {
        
        return internal.cross(other);
    }
    
    @ZenCodeType.Method
    public static BlockPos atY(BlockPos internal, int value) {
        
        return internal.atY(value);
    }
    
    @ZenCodeType.Method
    public static BlockPos asImmutable(BlockPos internal) {
        
        return internal.immutable();
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos asMutable(BlockPos internal) {
        
        return internal.mutable();
    }
    
    
}
