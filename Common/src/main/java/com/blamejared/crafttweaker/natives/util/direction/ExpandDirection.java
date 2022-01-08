package com.blamejared.crafttweaker.natives.util.direction;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/direction/Direction")
@NativeTypeRegistration(value = Direction.class, zenCodeName = "crafttweaker.api.util.Direction")
@BracketEnum("minecraft:direction")
public class ExpandDirection {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("rotation")
    public static Quaternion getRotation(Direction internal) {
        
        return internal.getRotation();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("axisDirection")
    public static Direction.AxisDirection getAxisDirection(Direction internal) {
        
        return internal.getAxisDirection();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("opposite")
    public static Direction getOpposite(Direction internal) {
        
        return internal.getOpposite();
    }
    
    @ZenCodeType.Method
    public static Direction getClockWise(Direction internal, Direction.Axis axis) {
        
        return internal.getClockWise(axis);
    }
    
    @ZenCodeType.Method
    public static Direction getCounterClockWise(Direction internal, Direction.Axis axis) {
        
        return internal.getCounterClockWise(axis);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("clockWise")
    public static Direction getClockWise(Direction internal) {
        
        return internal.getClockWise();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("counterClockWise")
    public static Direction getCounterClockWise(Direction internal) {
        
        return internal.getCounterClockWise();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("stepX")
    public static int getStepX(Direction internal) {
        
        return internal.getStepX();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("stepY")
    public static int getStepY(Direction internal) {
        
        return internal.getStepY();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("stepZ")
    public static int getStepZ(Direction internal) {
        
        return internal.getStepZ();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("step")
    public static Vector3f step(Direction internal) {
        
        return internal.step();
    }
    
    @ZenCodeType.Method
    public static String getName(Direction internal) {
        
        return internal.getName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("axis")
    public static Direction.Axis getAxis(Direction internal) {
        
        return internal.getAxis();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("toYRot")
    public static float toYRot(Direction internal) {
        
        return internal.toYRot();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("normal")
    public static Vec3i getNormal(Direction internal) {
        
        return internal.getNormal();
    }
    
    @ZenCodeType.Method
    public static boolean isFacingAngle(Direction internal, float degrees) {
        
        return internal.isFacingAngle(degrees);
    }
    
    
}
