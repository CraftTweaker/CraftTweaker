package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.AxisCycle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/MutableBlockPos")
@NativeTypeRegistration(value = BlockPos.MutableBlockPos.class, zenCodeName = "crafttweaker.api.util.math.MutableBlockPos")
public class ExpandMutableBlockPos {
    
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos setValue(BlockPos.MutableBlockPos internal, int x, int y, int z) {
        
        return internal.set(x, y, z);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos setValue(BlockPos.MutableBlockPos internal, double x, double y, double z) {
        
        return internal.set(x, y, z);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos setValue(BlockPos.MutableBlockPos internal, Vec3i value) {
        
        return internal.set(value);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos setValue(BlockPos.MutableBlockPos internal, long value) {
        
        return internal.set(value);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos setValue(BlockPos.MutableBlockPos internal, AxisCycle axisCycle, int x, int y, int z) {
        
        return internal.set(axisCycle, x, y, z);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos setWithOffset(BlockPos.MutableBlockPos internal, Vec3i other, Direction direction) {
        
        return internal.setWithOffset(other, direction);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos setWithOffset(BlockPos.MutableBlockPos internal, Vec3i other, int x, int y, int z) {
        
        return internal.setWithOffset(other, x, y, z);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos setWithOffset(BlockPos.MutableBlockPos internal, Vec3i other, Vec3i offset) {
        
        return internal.setWithOffset(other, offset);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos move(BlockPos.MutableBlockPos internal, Direction direction) {
        
        return internal.move(direction);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos move(BlockPos.MutableBlockPos internal, Direction direction, int distance) {
        
        return internal.move(direction, distance);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos move(BlockPos.MutableBlockPos internal, int x, int y, int z) {
        
        return internal.move(x, y, z);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos move(BlockPos.MutableBlockPos internal, Vec3i other) {
        
        return internal.move(other);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos clamp(BlockPos.MutableBlockPos internal, Direction.Axis axis, int min, int max) {
        
        return internal.clamp(axis, min, max);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos setX(BlockPos.MutableBlockPos internal, int x) {
        
        return internal.setX(x);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos setY(BlockPos.MutableBlockPos internal, int y) {
        
        return internal.setY(y);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos setZ(BlockPos.MutableBlockPos internal, int z) {
        
        return internal.setZ(z);
    }
    
    @ZenCodeType.Method
    public static BlockPos.MutableBlockPos mutable(BlockPos.MutableBlockPos internal) {
        
        return internal.mutable();
    }
    
}
