package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/BlockPos")
@NativeTypeRegistration(value = BlockPos.class, zenCodeName = "crafttweaker.api.util.math.BlockPos", constructors = {
        @NativeConstructor(value = {
                @NativeConstructor.ConstructorParameter(name = "x", type = Integer.class, description = "The x value of the blockpos", examples = "0"),
                @NativeConstructor.ConstructorParameter(name = "y", type = Integer.class, description = "The y value of the blockpos", examples = "1"),
                @NativeConstructor.ConstructorParameter(name = "z", type = Integer.class, description = "The z value of the blockpos", examples = "2")
        }, description = "Creates a new BlockPos using the provided values."),
        @NativeConstructor(value = {
                @NativeConstructor.ConstructorParameter(name = "x", type = Double.class, description = "The x value of the blockpos", examples = "0"),
                @NativeConstructor.ConstructorParameter(name = "y", type = Double.class, description = "The y value of the blockpos", examples = "1"),
                @NativeConstructor.ConstructorParameter(name = "z", type = Double.class, description = "The z value of the blockpos", examples = "2")
        }, description = "Creates a new BlockPos using the provided values. Note, the values will be floored down, providing 0.85 is the same as providing 0."),
        @NativeConstructor(value = {
                @NativeConstructor.ConstructorParameter(name = "vector", type = Vec3.class, description = "The vector to copy the values of.", examples = "new Vec3(0, 1, 2)"),
        }, description = "Creates a new BlockPos using the values of the given vector. Note, the values will be floored down, providing 0.85 is the same as providing 0."),
        @NativeConstructor(value = {
                @NativeConstructor.ConstructorParameter(name = "position", type = Position.class, description = "The position to copy the values of.", examples = "new Vec3(0, 1, 2)"),
        }, description = "Creates a new BlockPos using the values of the given position. Note, the values will be floored down, providing 0.85 is the same as providing 0."),
        @NativeConstructor(value = {
                @NativeConstructor.ConstructorParameter(name = "vector", type = Vec3i.class, description = "The vector to copy the values of.", examples = "new BlockPos(0, 1, 2)"),
        }, description = "Creates a new BlockPos using the values of the given vector.")
})
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
