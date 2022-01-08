package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/Vec3")
@NativeTypeRegistration(value = Vec3.class, zenCodeName = "crafttweaker.api.util.math.Vec3",
        constructors = @NativeConstructor({
                @NativeConstructor.ConstructorParameter(type = double.class, name = "x"),
                @NativeConstructor.ConstructorParameter(type = double.class, name = "y"),
                @NativeConstructor.ConstructorParameter(type = double.class, name = "z")
        }))
public class ExpandVec3 {
    
    @ZenCodeType.Method
    public static Vec3 vectorTo(Vec3 internal, Vec3 other) {
        
        return internal.vectorTo(other);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("normalize")
    public static Vec3 normalize(Vec3 internal) {
        
        return internal.normalize();
    }
    
    @ZenCodeType.Method
    public static double dot(Vec3 internal, Vec3 other) {
        
        return internal.dot(other);
    }
    
    @ZenCodeType.Method
    public static Vec3 cross(Vec3 internal, Vec3 other) {
        
        return internal.cross(other);
    }
    
    @ZenCodeType.Method
    public static Vec3 subtract(Vec3 internal, Vec3 other) {
        
        return internal.subtract(other);
    }
    
    @ZenCodeType.Method
    public static Vec3 subtract(Vec3 internal, double x, double y, double z) {
        
        return internal.subtract(x, y, z);
    }
    
    @ZenCodeType.Method
    public static Vec3 add(Vec3 internal, Vec3 other) {
        
        return internal.add(other);
    }
    
    @ZenCodeType.Method
    public static Vec3 add(Vec3 internal, double x, double y, double z) {
        
        return internal.add(x, y, z);
    }
    
    @ZenCodeType.Method
    public static boolean closerThan(Vec3 internal, Position position, double maxDistance) {
        
        return internal.closerThan(position, maxDistance);
    }
    
    @ZenCodeType.Method
    public static double distanceTo(Vec3 internal, Vec3 other) {
        
        return internal.distanceTo(other);
    }
    
    @ZenCodeType.Method
    public static double distanceToSqr(Vec3 internal, Vec3 other) {
        
        return internal.distanceToSqr(other);
    }
    
    @ZenCodeType.Method
    public static double distanceToSqr(Vec3 internal, double x, double y, double z) {
        
        return internal.distanceToSqr(x, y, z);
    }
    
    @ZenCodeType.Method
    public static Vec3 scale(Vec3 internal, double scalar) {
        
        return internal.scale(scalar);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("reverse")
    public static Vec3 reverse(Vec3 internal) {
        
        return internal.reverse();
    }
    
    @ZenCodeType.Method
    public static Vec3 multiply(Vec3 internal, Vec3 other) {
        
        return internal.multiply(other);
    }
    
    @ZenCodeType.Method
    public static Vec3 multiply(Vec3 internal, double x, double y, double z) {
        
        return internal.multiply(x, y, z);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("length")
    public static double length(Vec3 internal) {
        
        return internal.length();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("lengthSqr")
    public static double lengthSqr(Vec3 internal) {
        
        return internal.lengthSqr();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("horizontalDistance")
    public static double horizontalDistance(Vec3 internal) {
        
        return internal.horizontalDistance();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("horizontalDistanceSqr")
    public static double horizontalDistanceSqr(Vec3 internal) {
        
        return internal.horizontalDistanceSqr();
    }
    
    @ZenCodeType.Method
    public static Vec3 lerp(Vec3 internal, Vec3 other, double value) {
        
        return internal.lerp(other, value);
    }
    
    @ZenCodeType.Method
    public static Vec3 xRot(Vec3 internal, float pitch) {
        
        return internal.xRot(pitch);
    }
    
    @ZenCodeType.Method
    public static Vec3 yRot(Vec3 internal, float yaw) {
        
        return internal.yRot(yaw);
    }
    
    @ZenCodeType.Method
    public static Vec3 zRot(Vec3 internal, float roll) {
        
        return internal.zRot(roll);
    }
    
    @ZenCodeType.Method
    public static double getValue(Vec3 internal, Direction.Axis axis) {
        
        return internal.get(axis);
    }
    
}
