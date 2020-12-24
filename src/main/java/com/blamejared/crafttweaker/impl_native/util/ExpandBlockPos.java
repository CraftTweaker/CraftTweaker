package com.blamejared.crafttweaker.impl_native.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.math.BlockPos;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents a position of a block in the world
 *
 * @docParam this new BlockPos(0, 1, 2)
 */
@ZenRegister
@Document("vanilla/api/util/BlockPos")
@NativeTypeRegistration(value = BlockPos.class, zenCodeName = "crafttweaker.api.util.BlockPos", constructors = @NativeConstructor({@NativeConstructor.ConstructorParameter(name = "x", type = int.class), @NativeConstructor.ConstructorParameter(name = "y", type = int.class), @NativeConstructor.ConstructorParameter(name = "z", type = int.class)}))
public class ExpandBlockPos {
    
    
    @ZenCodeType.Caster
    public static long toLong(BlockPos internal) {
        return internal.toLong();
    }
    
    /**
     * Adds the given values to this position, and returns a new position with the new values.
     *
     * @param x x value to add
     * @param y y value to add
     * @param z z value to add
     * @return a new position based on values of provided values and this position
     * @docParam x 50.21
     * @docParam y -20.8
     * @docParam z -25.2
     */
    @ZenCodeType.Method
    public static BlockPos add(BlockPos internal, double x, double y, double z) {
        return x == 0.0D && y == 0.0D && z == 0.0D ? internal : new BlockPos((double) getX(internal) + x, (double) getY(internal) + y, (double) getZ(internal) + z);
    }
    
    /**
     * Adds the given values to this position, and returns a new position with the new values.
     *
     * @param x x value to add
     * @param y y value to add
     * @param z z value to add
     * @return a new position based on values of provided values and this position
     * @docParam x 50
     * @docParam y -20
     * @docParam z -25
     */
    @ZenCodeType.Method
    public static BlockPos add(BlockPos internal, int x, int y, int z) {
        return x == 0 && y == 0 && z == 0 ? internal : new BlockPos(getX(internal) + x, getY(internal) + y, getZ(internal) + z);
    }
    
    /**
     * Adds two positions together and returns the result.
     *
     * @param pos other position to add
     * @return new {@link BlockPos} with the added values.
     * @docParam pos new BlockPos(3, 2, 1)
     */
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.ADD)
    public static BlockPos add(BlockPos internal, BlockPos pos) {
        return internal.add(pos);
    }
    
    /**
     * Subtracts two positions together and returns the result.
     *
     * @param pos other position to remove
     * @return new {@link BlockPos} with the removed values.
     * @docParam pos new BlockPos(2, 1, 3)
     */
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.SUB)
    public static BlockPos subtract(BlockPos internal, BlockPos pos) {
        return internal.subtract(pos);
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is one block higher than this BlockPos
     *
     * @return a new BlockPos that is one block higher than this BlockPos
     */
    @ZenCodeType.Method
    public static BlockPos up(BlockPos internal) {
        return internal.up();
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is n block(s) higher than this BlockPos
     *
     * @return a new BlockPos that is n block(s) higher than this BlockPos
     * @docParam n 45
     */
    @ZenCodeType.Method
    public static BlockPos up(BlockPos internal, int n) {
        return internal.up(n);
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is one block lower than this BlockPos
     *
     * @return a new BlockPos that is one block lower than this BlockPos
     */
    @ZenCodeType.Method
    public static BlockPos down(BlockPos internal) {
        return internal.down();
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is n block(s) lower than this BlockPos
     *
     * @return a new BlockPos that is n block(s) lower than this BlockPos
     */
    @ZenCodeType.Method
    public static BlockPos down(BlockPos internal, int n) {
        return internal.down(n);
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is one block north of this BlockPos
     *
     * @return a new BlockPos that is one block north of this BlockPos
     */
    @ZenCodeType.Method
    public static BlockPos north(BlockPos internal) {
        return internal.north();
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is n block(s) north of this BlockPos
     *
     * @return a new BlockPos that is n block(s) north of this BlockPos
     * @docParam n 10
     */
    @ZenCodeType.Method
    public static BlockPos north(BlockPos internal, int n) {
        return internal.north(n);
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is one block south of this BlockPos
     *
     * @return a new BlockPos that is one block south of this BlockPos
     */
    @ZenCodeType.Method
    public static BlockPos south(BlockPos internal) {
        return internal.south();
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is n block(s) south of this BlockPos
     *
     * @return a new BlockPos that is n block(s) south of this BlockPos
     * @docParam n 12
     */
    @ZenCodeType.Method
    public static BlockPos south(BlockPos internal, int n) {
        return internal.south(n);
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is one block west of this BlockPos
     *
     * @return a new BlockPos that is one block west of this BlockPos
     */
    @ZenCodeType.Method
    public static BlockPos west(BlockPos internal) {
        return internal.west();
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is n block(s) west of this BlockPos
     *
     * @return a new BlockPos that is n block(s) west of this BlockPos
     * @docParam n 120
     */
    @ZenCodeType.Method
    public static BlockPos west(BlockPos internal, int n) {
        return internal.west(n);
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is one block east of this BlockPos
     *
     * @return a new BlockPos that is one block east of this BlockPos
     */
    @ZenCodeType.Method
    public static BlockPos east(BlockPos internal) {
        return internal.east();
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is n block(s) east of this BlockPos
     *
     * @return a new BlockPos that is n block(s) east of this BlockPos
     * @docParam n 2
     */
    @ZenCodeType.Method
    public static BlockPos east(BlockPos internal, int n) {
        return internal.east(n);
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is one block offset of this BlockPos based on the given {@link MCDirection}
     *
     * @return a new BlockPos that is 1 block offset of this BlockPos
     * @docParam direction <direction:east>
     */
    @ZenCodeType.Method
    public static BlockPos offset(BlockPos internal, MCDirection direction) {
        return offset(internal, direction, 1);
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is n block(s) offset of this BlockPos based on the given {@link MCDirection}
     *
     * @return a new BlockPos that is n block(s) offset of this BlockPos
     * @docParam direction <direction:south>
     * @docParam n 3
     */
    @ZenCodeType.Method
    public static BlockPos offset(BlockPos internal, MCDirection direction, int n) {
        return internal.offset(direction.getInternal(), n);
    }
    
    /**
     * Creates a new BlockPos based on the cross product of this position, and the given position
     *
     * @param pos BlockPos to cross product
     * @return a new BlockPos based on the cross product of this BlockPos and the given BlockPos
     * @docParam pos new BlockPos(5, 8, 2);
     */
    @ZenCodeType.Method
    public static BlockPos crossProduct(BlockPos internal, BlockPos pos) {
        return internal.crossProduct(pos);
    }
    
    @ZenCodeType.Getter("x")
    public static int getX(BlockPos internal) {
        return internal.getX();
    }
    
    @ZenCodeType.Getter("y")
    public static int getY(BlockPos internal) {
        return internal.getY();
    }
    
    @ZenCodeType.Getter("z")
    public static int getZ(BlockPos internal) {
        return internal.getZ();
    }
    
    /**
     * Checks if the given BlockPos is within the specified distance of this BlockPos (this uses the middle of the BlockPos)
     *
     * @param pos      BlockPos to check if it is within the distance
     * @param distance distance to check within
     * @return true if the given BlockPos is within the given distance of this BlockPos
     * @docParam pos new BlockPos(80, 75, 54);
     * @docParam distance 10
     */
    @ZenCodeType.Method
    public static boolean withinDistance(BlockPos internal, BlockPos pos, double distance) {
        return internal.withinDistance(pos, distance);
    }
    
    /**
     * Gets the squared distance of this position to the specified BlockPos, using the center of the BlockPos
     *
     * @param to BlockPos to check against
     * @return the squared distance of this current position and the given BlockPos.
     * @docParam to new BlockPos(256, 128, 10);
     * @docParam useCenter true
     */
    @ZenCodeType.Method
    public static double distanceSq(BlockPos internal, BlockPos to) {
        return internal.distanceSq(to);
    }
    
    /**
     * Gets the squared distance of this position to the specified BlockPos
     *
     * @param to        BlockPos to check against
     * @param useCenter should the center of the coordinate be used? (adds 0.5 to each value)
     * @return the squared distance of this current position and the given BlockPos.
     * @docParam to new BlockPos(256, 128, 10);
     * @docParam useCenter true
     */
    @ZenCodeType.Method
    public static double distanceSq(BlockPos internal, BlockPos to, boolean useCenter) {
        return internal.distanceSq(getX(to), getY(to), getZ(to), useCenter);
    }
    
    /**
     * Gets the squared distance of this position to the specified coordinates
     *
     * @param x         x position to check against
     * @param y         y position to check against
     * @param z         z position to check against
     * @param useCenter should the center of the coordinate be used? (adds 0.5 to each value)
     * @return the squared distance of this current position and the given coordinates.
     * @docParam x 500.25
     * @docParam y 250.75
     * @docParam z 100.20
     * @docParam useCenter false
     */
    @ZenCodeType.Method
    public static double distanceSq(BlockPos internal, double x, double y, double z, boolean useCenter) {
        return internal.distanceSq(x, y, z, useCenter);
    }
    
    /**
     * Gets the Manhattan Distance of this pos compared to a different position
     *
     * @param other other position to get the distance to
     * @return The manhattan distance of the positions
     * @docParam other new BlockPos(4, 5, 6)
     */
    @ZenCodeType.Method
    public static int manhattanDistance(BlockPos internal, BlockPos other) {
        return internal.manhattanDistance(other);
    }
}
