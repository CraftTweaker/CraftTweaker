package com.blamejared.crafttweaker.impl.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a direction axis (X, Y, Z)
 *
 * @docParam this <directionaxis:x>
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.util.DirectionAxis")
@Document("vanilla/util/DirectionAxis")
@ZenWrapper(wrappedClass = "net.minecraft.util.Direction.Axis", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.getCommandString()")
public class MCDirectionAxis implements CommandStringDisplayable {
    
    private static final Map<Direction.Axis, MCDirectionAxis> AXIS_MAP = Util.make(new HashMap<>(), map -> {
        map.put(Direction.Axis.X, new MCDirectionAxis(Direction.Axis.X));
        map.put(Direction.Axis.Y, new MCDirectionAxis(Direction.Axis.Y));
        map.put(Direction.Axis.Z, new MCDirectionAxis(Direction.Axis.Z));
    });
    
    private Direction.Axis internal;
    
    public MCDirectionAxis(Direction.Axis internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Getter("vertical")
    public boolean isVertical() {
        return internal.isVertical();
    }
    
    @ZenCodeType.Getter("horizontal")
    public boolean isHorizontal() {
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
    public int getCoordinate(int x, int y, int z) {
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
    public double getCoordinate(double x, double y, double z) {
        return internal.getCoordinate(x, y, z);
    }
    
    /**
     * Get the name of this Axis ("X", "Y" or "Z")
     *
     * @return "X", "Y" or "Z"
     */
    @ZenCodeType.Getter("name")
    public String name() {
        return internal.name();
    }
    
    /**
     * Gets the enum ordinal of this axis
     *
     * @return 0 for X, 1 for Y, 2 for Z
     */
    @ZenCodeType.Getter("ordinal")
    public int ordinal() {
        return internal.ordinal();
    }
    
    public Direction.Axis getInternal() {
        return internal;
    }
    
    public static MCDirectionAxis getAxis(Direction.Axis axis) {
        return AXIS_MAP.get(axis);
    }

    @Override
    public String getCommandString() {
        return "<directionaxis:" + internal.getName2() + ">";
    }
}
