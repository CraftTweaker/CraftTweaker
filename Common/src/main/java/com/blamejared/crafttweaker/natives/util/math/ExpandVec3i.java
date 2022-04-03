package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/Vec3i")
@NativeTypeRegistration(value = Vec3i.class, zenCodeName = "crafttweaker.api.util.math.Vec3i")
public class ExpandVec3i {
    
    @ZenCodeType.Method
    public static int compareTo(Vec3i internal, Vec3i other) {
        
        return internal.compareTo(other);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("x")
    public static int getX(Vec3i internal) {
        
        return internal.getX();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("y")
    public static int getY(Vec3i internal) {
        
        return internal.getY();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("z")
    public static int getZ(Vec3i internal) {
        
        return internal.getZ();
    }
    
    @ZenCodeType.Method
    public static Vec3i offset(Vec3i internal, double x, double y, double z) {
        
        return internal.offset(x, y, z);
    }
    
    @ZenCodeType.Method
    public static Vec3i offset(Vec3i internal, int x, int y, int z) {
        
        return internal.offset(x, y, z);
    }
    
    @ZenCodeType.Method
    public static Vec3i offset(Vec3i internal, Vec3i other) {
        
        return internal.offset(other);
    }
    
    @ZenCodeType.Method
    public static Vec3i subtract(Vec3i internal, Vec3i other) {
        
        return internal.subtract(other);
    }
    
    @ZenCodeType.Method
    public static Vec3i multiply(Vec3i internal, int scalar) {
        
        return internal.multiply(scalar);
    }
    
    @ZenCodeType.Method
    public static Vec3i above(Vec3i internal) {
        
        return internal.above();
    }
    
    @ZenCodeType.Method
    public static Vec3i above(Vec3i internal, int distance) {
        
        return internal.above(distance);
    }
    
    @ZenCodeType.Method
    public static Vec3i below(Vec3i internal) {
        
        return internal.below();
    }
    
    @ZenCodeType.Method
    public static Vec3i below(Vec3i internal, int distance) {
        
        return internal.below(distance);
    }
    
    @ZenCodeType.Method
    public static Vec3i north(Vec3i internal) {
        
        return internal.north();
    }
    
    @ZenCodeType.Method
    public static Vec3i north(Vec3i internal, int distance) {
        
        return internal.north(distance);
    }
    
    @ZenCodeType.Method
    public static Vec3i south(Vec3i internal) {
        
        return internal.south();
    }
    
    @ZenCodeType.Method
    public static Vec3i south(Vec3i internal, int distance) {
        
        return internal.south(distance);
    }
    
    @ZenCodeType.Method
    public static Vec3i west(Vec3i internal) {
        
        return internal.west();
    }
    
    @ZenCodeType.Method
    public static Vec3i west(Vec3i internal, int distance) {
        
        return internal.west(distance);
    }
    
    @ZenCodeType.Method
    public static Vec3i east(Vec3i internal) {
        
        return internal.east();
    }
    
    @ZenCodeType.Method
    public static Vec3i east(Vec3i internal, int distance) {
        
        return internal.east(distance);
    }
    
    @ZenCodeType.Method
    public static Vec3i relative(Vec3i internal, Direction direction) {
        
        return internal.relative(direction);
    }
    
    @ZenCodeType.Method
    public static Vec3i relative(Vec3i internal, Direction direction, int distance) {
        
        return internal.relative(direction, distance);
    }
    
    @ZenCodeType.Method
    public static Vec3i relative(Vec3i internal, Direction.Axis axis, int distanec) {
        
        return internal.relative(axis, distanec);
    }
    
    @ZenCodeType.Method
    public static Vec3i cross(Vec3i internal, Vec3i other) {
        
        return internal.cross(other);
    }
    
    @ZenCodeType.Method
    public static boolean closerThan(Vec3i internal, Vec3i other, double maxDistance) {
        
        return internal.closerThan(other, maxDistance);
    }
    
    @ZenCodeType.Method
    public static double distSqr(Vec3i internal, Vec3i other) {
        
        return internal.distSqr(other);
    }
    
    @ZenCodeType.Method
    public static double distToCenterSqr(Vec3i internal, Position position) {
        
        return internal.distToCenterSqr(position);
    }
    
    @ZenCodeType.Method
    public static double distToCenterSqr(Vec3i internal, double x, double y, double z) {
        
        return internal.distToCenterSqr(x, y, z);
    }
    
    @ZenCodeType.Method
    public static int distManhattan(Vec3i internal, Vec3i other) {
        
        return internal.distManhattan(other);
    }
    
    @ZenCodeType.Method
    public static int getValue(Vec3i internal, Direction.Axis axis) {
        
        return internal.get(axis);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("toShortString")
    public static String toShortString(Vec3i internal) {
        
        return internal.toShortString();
    }
    
}
