package com.blamejared.crafttweaker.impl_native.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.dispenser.IPosition;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A 3-dimensional vector, in a 3-dimensional vector space.
 *
 * This class is immutable, meaning its values cannot change, so it is safe to be stored as a key in maps or in
 * collections.
 *
 * No, it's not an arrow.
 */
@ZenRegister
@Document("vanilla/api/util/MCVector3i")
@NativeTypeRegistration(
        value = Vector3i.class,
        zenCodeName = "crafttweaker.api.util.MCVector3i",
        constructors = @NativeConstructor({
                @NativeConstructor.ConstructorParameter(type = int.class, name = "x"),
                @NativeConstructor.ConstructorParameter(type = int.class, name = "y"),
                @NativeConstructor.ConstructorParameter(type = int.class, name = "z")
        })
)
public class ExpandVector3i {
    
    /**
     * Gets the X coordinate of this vector.
     *
     * @param internal The vector.
     *
     * @return The X coordinate.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("x")
    public static int getX(final Vector3i internal) {
        
        return internal.getX();
    }
    
    /**
     * Gets the Y coordinate of this vector.
     *
     * @param internal The vector.
     *
     * @return The Y coordinate.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("y")
    public static int getY(final Vector3i internal) {
        
        return internal.getY();
    }
    
    /**
     * Gets the Y coordinate of this vector.
     *
     * @param internal The vector.
     *
     * @return The Y coordinate.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("z")
    public static int getZ(final Vector3i internal) {
        
        return internal.getZ();
    }
    
    /**
     * Creates a new Vector3i based on this Vector3i that is one block higher than this Vector3i
     *
     * @return a new Vector3i that is one block higher than this Vector3i
     */
    @ZenCodeType.Method
    public static Vector3i up(final Vector3i internal) {
        
        return internal.up();
    }
    
    /**
     * Creates a new Vector3i based on this Vector3i that is n block(s) higher than this Vector3i
     *
     * @return a new Vector3i that is n block(s) higher than this Vector3i
     *
     * @docParam n 45
     */
    @ZenCodeType.Method
    public static Vector3i up(final Vector3i internal, int n) {
        
        return internal.up(n);
    }
    
    /**
     * Creates a new Vector3i based on this Vector3i that is one block lower than this Vector3i
     *
     * @return a new Vector3i that is one block lower than this Vector3i
     */
    @ZenCodeType.Method
    public static Vector3i down(final Vector3i internal) {
        
        return internal.down();
    }
    
    /**
     * Creates a new Vector3i based on this Vector3i that is n block(s) lower than this Vector3i
     *
     * @return a new Vector3i that is n block(s) lower than this Vector3i
     */
    @ZenCodeType.Method
    public static Vector3i down(final Vector3i internal, int n) {
        
        return internal.down(n);
    }
    
    /**
     * Creates a new Vector3i based on this Vector3i that is one block offset of this Vector3i based on the given {@link MCDirection}
     *
     * @return a new Vector3i that is 1 block offset of this Vector3i
     *
     * @docParam direction <direction:east>
     */
    @ZenCodeType.Method
    public static Vector3i offset(final Vector3i internal, Direction facing, int n) {
        
        return internal.offset(facing, n);
    }
    
    /**
     * Creates a new Vector3i based on the cross product of this position, and the given position
     *
     * @param vec Vector3i to cross product
     *
     * @return a new Vector3i based on the cross product of this Vector3i and the given Vector3i
     *
     * @docParam pos new Vector3i(5, 8, 2);
     */
    @ZenCodeType.Method
    public static Vector3i crossProduct(final Vector3i internal, Vector3i vec) {
        
        return internal.crossProduct(vec);
    }
    
    /**
     * Checks if the given Vector3i is within the specified distance of this Vector3i (this uses the middle of the Vector3i)
     *
     * @param vector   Vector3i to check if it is within the distance
     * @param distance distance to check within
     *
     * @return true if the given Vector3i is within the given distance of this Vector3i
     *
     * @docParam pos new Vector3i(80, 75, 54);
     * @docParam distance 10
     */
    @ZenCodeType.Method
    public static boolean withinDistance(final Vector3i internal, Vector3i vector, double distance) {
        
        return internal.withinDistance(vector, distance);
    }
    
    /**
     * Gets the squared distance of this position to the specified Vector3i, using the center of the Vector3i
     *
     * @param to Vector3i to check against
     *
     * @return the squared distance of this current position and the given Vector3i.
     *
     * @docParam to new Vector3i(256, 128, 10);
     * @docParam useCenter true
     */
    @ZenCodeType.Method
    public static double distanceSq(final Vector3i internal, Vector3i to) {
        
        return internal.distanceSq(to);
    }
    
    /**
     * Gets the squared distance of this position to the specified Vector3i
     *
     * @param position  Vector3i to check against
     * @param useCenter should the center of the coordinate be used? (adds 0.5 to each value)
     *
     * @return the squared distance of this current position and the given Vector3i.
     *
     * @docParam to new Vector3i(256, 128, 10);
     * @docParam useCenter true
     */
    @ZenCodeType.Method
    public static double distanceSq(final Vector3i internal, IPosition position, boolean useCenter) {
        
        return internal.distanceSq(position, useCenter);
    }
    
    /**
     * Gets the squared distance of this position to the specified coordinates
     *
     * @param x         x position to check against
     * @param y         y position to check against
     * @param z         z position to check against
     * @param useCenter should the center of the coordinate be used? (adds 0.5 to each value)
     *
     * @return the squared distance of this current position and the given coordinates.
     *
     * @docParam x 500.25
     * @docParam y 250.75
     * @docParam z 100.20
     * @docParam useCenter false
     */
    @ZenCodeType.Method
    public static double distanceSq(final Vector3i internal, double x, double y, double z, boolean useCenter) {
        
        return internal.distanceSq(x, y, z, useCenter);
    }
    
    /**
     * Gets the Manhattan Distance of this vector compared to a different vector
     *
     * @param vector other vector to get the distance to
     *
     * @return The manhattan distance of the vectors
     *
     * @docParam other new Vector3i(4, 5, 6)
     */
    @ZenCodeType.Method
    public static int manhattanDistance(final Vector3i internal, Vector3i vector) {
        
        return internal.manhattanDistance(vector);
    }
    
    //TODO when this isn't stripped from the server jsut call the internal method
    @ZenCodeType.Method
    @ZenCodeType.Getter("coordinateString")
    public static String getCoordinatesAsString(final Vector3i internal) {
        
        return "" + internal.getX() + ", " + internal.getY() + ", " + internal.getZ();
    }
    
    
    @ZenCodeType.Method
    @ZenCodeType.Caster
    public static Vector3d asVector3i(final Vector3i internal) {
        
        return new Vector3d(internal.getX(), internal.getY(), internal.getZ());
    }
    
}
