package com.blamejared.crafttweaker.impl.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a cardinal direction (north, south, east, west) and (up and down).
 *
 * @docParam this <direction:north>
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.util.Direction")
@Document("vanilla/api/util/Direction")
@ZenWrapper(wrappedClass = "net.minecraft.util.Direction", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.getInternal.toString()")
public class MCDirection {
    
    private static final Map<Direction, MCDirection> DIRECTION_MAP = Util.make(new HashMap<>(), map -> {
        map.put(Direction.NORTH, new MCDirection(Direction.NORTH));
        map.put(Direction.SOUTH, new MCDirection(Direction.SOUTH));
        map.put(Direction.EAST, new MCDirection(Direction.EAST));
        map.put(Direction.WEST, new MCDirection(Direction.WEST));
        map.put(Direction.UP, new MCDirection(Direction.UP));
        map.put(Direction.DOWN, new MCDirection(Direction.DOWN));
    });
    
    private Direction internal;
    
    public MCDirection(Direction internal) {
        this.internal = internal;
    }
    
    // TODO add this when we have an Entity wrapper
    
    //    public static Direction[] getFacingDirections(Entity entityIn) {
    //        return Direction.getFacingDirections(entityIn);
    //    }
    
    /**
     * Get the Index of this direction (0-5). The order is D-U-N-S-W-E
     *
     * @return index of the direction
     */
    @ZenCodeType.Getter("index")
    public int getIndex() {
        return internal.getIndex();
    }
    
    /**
     * Get the index of this horizontal direction (0-3). The order is S-W-N-E
     *
     * @return horizontal index of the direction
     */
    @ZenCodeType.Getter("horizontalIndex")
    public int getHorizontalIndex() {
        return internal.getHorizontalIndex();
    }
    
    /**
     * Gets the offset of this axis (is it pointing towards position or negative). down is negative, up is positive (-y for down, +y for up)
     *
     * @return +1 if positive, -1 if negative
     */
    @ZenCodeType.Getter("axisOffset")
    public int getAxisOffset() {
        return internal.getAxisDirection().getOffset();
    }
    
    /**
     * Gets the opposite of this Direction (north returns south)
     *
     * @return The opposite of this direction.
     */
    @ZenCodeType.Getter("opposite")
    public MCDirection getOpposite() {
        return DIRECTION_MAP.get(internal.getOpposite());
    }
    
    /**
     * Rotates this direction around a given Axis
     *
     * @param axis the Axis to rotate around
     *
     * @return the rotated Direction
     *
     * @docParam axis <directionaxis:north>
     */
    @ZenCodeType.Method
    public MCDirection rotateAround(MCDirectionAxis axis) {
        return DIRECTION_MAP.get(internal.rotateAround(axis.getInternal()));
    }
    
    /**
     * Rotates this direction on the Y axis
     *
     * @return the direction that rotated on the Y axis of this direction
     */
    @ZenCodeType.Method
    public MCDirection rotateY() {
        return DIRECTION_MAP.get(internal.rotateY());
    }
    
    /**
     * Rotates this direction counter-clock wise on the Y axis
     *
     * @return the direction that is counter clockwise on the Y axis
     */
    @ZenCodeType.Method
    public MCDirection rotateYCCW() {
        return DIRECTION_MAP.get(internal.rotateYCCW());
    }
    
    /**
     * Gets the X offset of this direction
     *
     * @return X offset of this direction
     */
    @ZenCodeType.Getter("xOffset")
    public int getXOffset() {
        return internal.getXOffset();
    }
    
    /**
     * Gets the Y offset of this direction
     *
     * @return Y offset of this direction
     */
    @ZenCodeType.Getter("yOffset")
    public int getYOffset() {
        return internal.getYOffset();
    }
    
    /**
     * Gets the Z offset of this direction
     *
     * @return Z offset of this direction
     */
    @ZenCodeType.Getter("zOffset")
    public int getZOffset() {
        return internal.getZOffset();
    }
    
    /**
     * Gets the direction axis of this direction
     *
     * @return a {@link MCDirectionAxis} of this axis
     */
    @ZenCodeType.Getter("axis")
    public MCDirectionAxis getAxis() {
        return MCDirectionAxis.getAxis(internal.getAxis());
    }
    
    /**
     * Gets the horizontal angle of this direction
     *
     * @return this direction's horizontal angle
     */
    @ZenCodeType.Getter("horizontalAngle")
    public float getHorizontalAngle() {
        return internal.getHorizontalAngle();
    }
    
    /**
     * Gets the name of this direction
     *
     * @return name of this direction
     */
    @ZenCodeType.Getter("name")
    public String getName() {
        return internal.getName();
    }
    
    public Direction getInternal() {
        return internal;
    }
    
    @Override
    public int hashCode() {
        return internal.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof MCDirection && internal.equals(((MCDirection) obj).getInternal());
    }
}
