package com.blamejared.crafttweaker.impl.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.math.BlockPos;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents a position of a block in the world
 *
 * @docParam this new BlockPos(0, 1, 2)
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.util.BlockPos")
@Document("vanilla/util/BlockPos")
public class CTBlockPos {
    
    private BlockPos internal;
    
    public CTBlockPos(BlockPos internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public CTBlockPos(int x, int y, int z) {
        this.internal = new BlockPos(x, y, z);
    }
    
    @ZenCodeType.Caster
    public long toLong() {
        return internal.toLong();
    }
    
    /**
     * Adds the given values to this position, and returns a new position with the new values.
     *
     * @param x x value to add
     * @param y y value to add
     * @param z z value to add
     *
     * @return a new position based on values of provided values and this position
     *
     * @docParam x 50.21
     * @docParam y -20.8
     * @docParam z -25.2
     */
    @ZenCodeType.Method
    public CTBlockPos add(double x, double y, double z) {
        return x == 0.0D && y == 0.0D && z == 0.0D ? this : new CTBlockPos(new BlockPos((double) this.getX() + x, (double) this.getY() + y, (double) this.getZ() + z));
    }
    
    /**
     * Adds the given values to this position, and returns a new position with the new values.
     *
     * @param x x value to add
     * @param y y value to add
     * @param z z value to add
     *
     * @return a new position based on values of provided values and this position
     *
     * @docParam x 50
     * @docParam y -20
     * @docParam z -25
     */
    @ZenCodeType.Method
    public CTBlockPos add(int x, int y, int z) {
        return x == 0 && y == 0 && z == 0 ? this : new CTBlockPos(new BlockPos(this.getX() + x, this.getY() + y, this.getZ() + z));
        
    }
    
    /**
     * Adds two positions together and returns the result.
     *
     * @param pos other position to add
     *
     * @return new {@link CTBlockPos} with the added values.
     *
     * @docParam pos new BlockPos(3, 2, 1)
     */
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.ADD)
    public CTBlockPos add(CTBlockPos pos) {
        return add(pos);
    }
    
    /**
     * Subtracts two positions together and returns the result.
     *
     * @param pos other position to remove
     *
     * @return new {@link CTBlockPos} with the removed values.
     *
     * @docParam pos new BlockPos(2, 1, 3)
     */
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.SUB)
    public CTBlockPos subtract(CTBlockPos pos) {
        return this.add(-pos.getX(), -pos.getY(), -pos.getZ());
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is one block higher than this BlockPos
     *
     * @return a new BlockPos that is one block higher than this BlockPos
     */
    @ZenCodeType.Method
    public BlockPos up() {
        return internal.up();
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is n block(s) higher than this BlockPos
     *
     * @return a new BlockPos that is n block(s) higher than this BlockPos
     *
     * @docParam n 45
     */
    @ZenCodeType.Method
    public BlockPos up(int n) {
        return internal.up(n);
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is one block lower than this BlockPos
     *
     * @return a new BlockPos that is one block lower than this BlockPos
     */
    @ZenCodeType.Method
    public BlockPos down() {
        return internal.down();
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is n block(s) lower than this BlockPos
     *
     * @return a new BlockPos that is n block(s) lower than this BlockPos
     */
    public BlockPos down(int n) {
        return internal.down(n);
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is one block north of this BlockPos
     *
     * @return a new BlockPos that is one block north of this BlockPos
     */
    @ZenCodeType.Method
    public BlockPos north() {
        return internal.north();
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is n block(s) north of this BlockPos
     *
     * @return a new BlockPos that is n block(s) north of this BlockPos
     *
     * @docParam n 10
     */
    @ZenCodeType.Method
    public BlockPos north(int n) {
        return internal.north(n);
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is one block south of this BlockPos
     *
     * @return a new BlockPos that is one block south of this BlockPos
     */
    @ZenCodeType.Method
    public BlockPos south() {
        return internal.south();
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is n block(s) south of this BlockPos
     *
     * @return a new BlockPos that is n block(s) south of this BlockPos
     *
     * @docParam n 12
     */
    @ZenCodeType.Method
    public BlockPos south(int n) {
        return internal.south(n);
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is one block west of this BlockPos
     *
     * @return a new BlockPos that is one block west of this BlockPos
     */
    @ZenCodeType.Method
    public BlockPos west() {
        return internal.west();
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is n block(s) west of this BlockPos
     *
     * @return a new BlockPos that is n block(s) west of this BlockPos
     *
     * @docParam n 120
     */
    @ZenCodeType.Method
    public BlockPos west(int n) {
        return internal.west(n);
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is one block east of this BlockPos
     *
     * @return a new BlockPos that is one block east of this BlockPos
     */
    @ZenCodeType.Method
    public BlockPos east() {
        return internal.east();
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is n block(s) east of this BlockPos
     *
     * @return a new BlockPos that is n block(s) east of this BlockPos
     *
     * @docParam n 2
     */
    @ZenCodeType.Method
    public BlockPos east(int n) {
        return internal.east(n);
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is one block offset of this BlockPos based on the given {@link CTDirection}
     *
     * @return a new BlockPos that is 1 block offset of this BlockPos
     *
     * @docParam direction <direction:east>
     */
    @ZenCodeType.Method
    public CTBlockPos offset(CTDirection direction) {
        return offset(direction, 1);
    }
    
    /**
     * Creates a new BlockPos based on this BlockPos that is n block(s) offset of this BlockPos based on the given {@link CTDirection}
     *
     * @return a new BlockPos that is n block(s) offset of this BlockPos
     *
     * @docParam direction <direction:south>
     * @docParam n 3
     */
    @ZenCodeType.Method
    public CTBlockPos offset(CTDirection direction, int n) {
        return n == 0 ? this : new CTBlockPos(new BlockPos(this.getX() + direction.getXOffset() * n, this.getY() + direction.getYOffset() * n, this.getZ() + direction.getZOffset() * n));
        
    }
    
    /**
     * Creates a new BlockPos based on the cross product of this position, and the given position
     *
     * @param pos BlockPos to cross product
     *
     * @return a new BlockPos based on the cross product of this BlockPos and the given BlockPos
     *
     * @docParam pos new BlockPos(5, 8, 2);
     */
    @ZenCodeType.Method
    public CTBlockPos crossProduct(CTBlockPos pos) {
        return new CTBlockPos(this.getY() * pos.getZ() - this.getZ() * pos.getY(), this.getZ() * pos.getX() - this.getX() * pos.getZ(), this.getX() * pos.getY() - this.getY() * pos.getX());
    }
    
    @ZenCodeType.Getter("x")
    public int getX() {
        return internal.getX();
    }
    
    @ZenCodeType.Getter("y")
    public int getY() {
        return internal.getY();
    }
    
    @ZenCodeType.Getter("z")
    public int getZ() {
        return internal.getZ();
    }
    
    /**
     * Checks if the given BlockPos is within the specified distance of this BlockPos (this uses the middle of the BlockPos)
     *
     * @param pos      BlockPos to check if it is within the distance
     * @param distance distance to check within
     *
     * @return true if the given BlockPos is within the given distance of this BlockPos
     *
     * @docParam pos new BlockPos(80, 75, 54);
     * @docParam distance 10
     */
    @ZenCodeType.Method
    public boolean withinDistance(CTBlockPos pos, double distance) {
        return this.distanceSq(pos.getX(), pos.getY(), pos.getZ(), true) < distance * distance;
    }
    
    /**
     * Gets the squared distance of this position to the specified BlockPos, using the center of the BlockPos
     *
     * @param to BlockPos to check against
     *
     * @return the squared distance of this current position and the given BlockPos.
     *
     * @docParam to new BlockPos(256, 128, 10);
     * @docParam useCenter true
     */
    @ZenCodeType.Method
    public double distanceSq(CTBlockPos to) {
        return this.distanceSq(to, true);
    }
    
    /**
     * Gets the squared distance of this position to the specified BlockPos
     *
     * @param to        BlockPos to check against
     * @param useCenter should the center of the coordinate be used? (adds 0.5 to each value)
     *
     * @return the squared distance of this current position and the given BlockPos.
     *
     * @docParam to new BlockPos(256, 128, 10);
     * @docParam useCenter true
     */
    @ZenCodeType.Method
    public double distanceSq(CTBlockPos to, boolean useCenter) {
        return this.distanceSq(to.getX(), to.getY(), to.getZ(), useCenter);
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
    public double distanceSq(double x, double y, double z, boolean useCenter) {
        return internal.distanceSq(x, y, z, useCenter);
    }
    
    /**
     * Gets the Manhattan Distance of this pos compared to a different position
     *
     * @param other other position to get the distance to
     *
     * @return The manhattan distance of the positions
     *
     * @docParam other new BlockPos(4, 5, 6)
     */
    @ZenCodeType.Method
    public int manhattanDistance(CTBlockPos other) {
        return internal.manhattanDistance(other.getInternal());
    }
    
    public BlockPos getInternal() {
        return internal;
    }
}
