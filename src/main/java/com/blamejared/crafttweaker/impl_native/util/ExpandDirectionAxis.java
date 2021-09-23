package com.blamejared.crafttweaker.impl_native.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.Direction;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents a direction axis (X, Y, Z)
 *
 * @docParam this <directionaxis:x>
 */
@ZenRegister
@NativeTypeRegistration(value = Direction.Axis.class, zenCodeName = "crafttweaker.api.util.DirectionAxis")
@Document("vanilla/api/util/DirectionAxis")
public class ExpandDirectionAxis {
    
    @ZenCodeType.Getter("vertical")
    public static boolean isVertical(Direction.Axis internal) {
        
        return internal.isVertical();
    }
    
    @ZenCodeType.Getter("horizontal")
    public static boolean isHorizontal(Direction.Axis internal) {
        
        return internal.isHorizontal();
    }
    
    /**
     * Gets the coordinate of this axis based on the given values, if this axis is "X", then it will return the value of the "x" parameter
     *
     * @param x x value of the coordinate
     * @param y y value of the coordinate
     * @param z z value of the coordinate
     *
     * @return value of the coordinate
     *
     * @docParam x 1
     * @docParam y 2
     * @docParam z 3
     */
    @ZenCodeType.Method
    public static int getCoordinate(Direction.Axis internal, int x, int y, int z) {
        
        return internal.getCoordinate(x, y, z);
    }
    
    /**
     * Gets the coordinate of this axis based on the given values, if this axis is "X", then it will return the value of the "x" parameter
     *
     * @param x x value of the coordinate
     * @param y y value of the coordinate
     * @param z z value of the coordinate
     *
     * @return value of the coordinate
     *
     * @docParam x 1.2
     * @docParam y 2.5
     * @docParam z 3.87
     */
    @ZenCodeType.Method
    public static double getCoordinate(Direction.Axis internal, double x, double y, double z) {
        
        return internal.getCoordinate(x, y, z);
    }
    
    /**
     * Get the name of this Axis ("X", "Y" or "Z")
     *
     * @return "X", "Y" or "Z"
     */
    @ZenCodeType.Getter("name")
    public static String name(Direction.Axis internal) {
        
        return internal.name();
    }
    
    /**
     * Gets the enum ordinal of this axis
     *
     * @return 0 for X, 1 for Y, 2 for Z
     */
    @ZenCodeType.Getter("ordinal")
    public static int ordinal(Direction.Axis internal) {
        
        return internal.ordinal();
    }
    
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Direction.Axis internal) {
        
        return "<directionAxis:" + internal.getName2() + ">";
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public static boolean equals(Direction.Axis internal, Object o) {
        
        return internal.equals(o);
    }
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hashCode")
    public static int hashCode(Direction.Axis internal) {
        
        return internal.hashCode();
    }
    
}
