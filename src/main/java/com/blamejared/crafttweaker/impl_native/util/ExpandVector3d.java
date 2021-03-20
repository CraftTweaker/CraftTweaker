package com.blamejared.crafttweaker.impl_native.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
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
@Document("vanilla/api/util/MCVector3d")
@NativeTypeRegistration(
        value = Vector3d.class,
        zenCodeName = "crafttweaker.api.util.MCVector3d",
        constructors = @NativeConstructor({
                @NativeConstructor.ConstructorParameter(type = double.class, name = "x"),
                @NativeConstructor.ConstructorParameter(type = double.class, name = "y"),
                @NativeConstructor.ConstructorParameter(type = double.class, name = "z")
        })
)
public class ExpandVector3d {
    /**
     * Gets the X coordinate of this vector.
     *
     * @param internal The vector.
     * @return The X coordinate.
     */
    @ZenCodeType.Getter("x")
    public static double getX(final Vector3d internal) {
        return internal.getX();
    }

    /**
     * Gets the Y coordinate of this vector.
     *
     * @param internal The vector.
     * @return The Y coordinate.
     */
    @ZenCodeType.Getter("y")
    public static double getY(final Vector3d internal) {
        return internal.getY();
    }

    /**
     * Gets the Z coordinate of this vector.
     *
     * @param internal The vector.
     * @return The Z coordinate.
     */
    @ZenCodeType.Getter("z")
    public static double getZ(final Vector3d internal) {
        return internal.getZ();
    }

    /**
     * Subtracts this vector from the <code>other</code> vector and returns the result.
     *
     * @param internal The vector.
     * @param other The vector from which this vector should be subtracted from.
     * @return The result.
     */
    @ZenCodeType.Method("subtractReverse")
    public static Vector3d subtractReverse(final Vector3d internal, final Vector3d other) {
        return internal.subtractReverse(other);
    }

    /**
     * Normalizes the current vector, making it of unit length.
     *
     * @param internal The vector.
     * @return The normalized vector.
     */
    @ZenCodeType.Method("normalize")
    public static Vector3d normalize(final Vector3d internal) {
        return internal.normalize();
    }

    /**
     * Performs a dot product between this vector and <code>other</code>.
     *
     * @param internal This vector.
     * @param other The other vector.
     * @return The dot product.
     */
    @ZenCodeType.Method("dot")
    public static double dot(final Vector3d internal, final Vector3d other) {
        return internal.dotProduct(other);
    }

    /**
     * Performs the cross product between this vector and <code>other</code>.
     *
     * @param internal This vector.
     * @param other The other vector.
     * @return The cross product.
     */
    @ZenCodeType.Method("cross")
    public static Vector3d cross(final Vector3d internal, final Vector3d other) {
        return internal.crossProduct(other);
    }

    /**
     * Multiplies the two vectors member by member, computing what is known as the Hadamard product.
     *
     * @param internal This vector.
     * @param other The other vector.
     * @return The Hadamard product.
     */
    @ZenCodeType.Method("times")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    public static Vector3d times(final Vector3d internal, final Vector3d other) {
        return internal.mul(other);
    }

    /**
     * Multiplies this vector with the vector <code>(x, y, z)</code>, computing what is known as the Hadamard product.
     *
     * @param internal This vector.
     * @param x The x component of the other vector.
     * @param y The y component of the other vector.
     * @param z The z component of the other vector.
     * @return The Hadamard product.
     */
    @ZenCodeType.Method("times")
    public static Vector3d times(final Vector3d internal, final double x, final double y, final double z) {
        return internal.mul(x, y, z);
    }

    /**
     * Adds this vector to the <code>other</code> vector, computing their sum member by member.
     *
     * @param internal This vector.
     * @param other The other vector.
     * @return The sum.
     */
    @ZenCodeType.Method("plus")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.ADD)
    public static Vector3d plus(final Vector3d internal, final Vector3d other) {
        return internal.add(other);
    }

    /**
     * Adds this vector to the vector <code>(x, y, z)</code>, computing their sum member by member.
     *
     * @param internal This vector.
     * @param x The x component of the other vector.
     * @param y The y component of the other vector.
     * @param z The z component of the other vector.
     * @return The sum.
     */
    @ZenCodeType.Method("plus")
    public static Vector3d plus(final Vector3d internal, final double x, final double y, final double z) {
        return internal.add(x, y, z);
    }

    /**
     * Subtracts <code>other</code> from this vector.
     *
     * @param internal This vector.
     * @param other The other vector.
     * @return The subtraction result.
     */
    @ZenCodeType.Method("minus")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.SUB)
    public static Vector3d minus(final Vector3d internal, final Vector3d other) {
        return internal.subtract(other);
    }

    /**
     * Subtracts the vector <code>(x, y, z)</code> from this vector.
     *
     * @param internal This vector.
     * @param x The x component of the other vector.
     * @param y The y component of the other vector.
     * @param z The z component of the other vector.
     * @return The subtraction result.
     */
    @ZenCodeType.Method("minus")
    public static Vector3d minus(final Vector3d internal, final double x, final double y, final double z) {
        return internal.subtract(x, y, z);
    }

    /**
     * Gets the coordinate of this vector identified by the specified index.
     *
     * Namely, 0 corresponds to the X coordinate, 1 to Y, and 2 to Z.
     *
     * @param internal This vector.
     * @param index The coordinate index.
     * @return The value of the corresponding coordinate.
     */
    @ZenCodeType.Method("getCoordinate")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    public static double getCoordinate(final Vector3d internal, final int index) {
        switch (index) {
            case 0: return internal.getX();
            case 1: return internal.getY();
            case 2: return internal.getZ();
        }
        throw new ArrayIndexOutOfBoundsException(index);
    }

    /**
     * Gets the coordinate of this vector that corresponds to the given {@link Direction.Axis}.
     *
     * @param internal This vector.
     * @param axis The axis.
     * @return The value of the corresponding coordinate.
     */
    @ZenCodeType.Method("getCoordinate")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    public static double getCoordinate(final Vector3d internal, final Direction.Axis axis) {
        return internal.getCoordinate(axis);
    }

    /**
     * Computes the Euclidean distance between this vector and the other vector.
     *
     * @param internal This vector.
     * @param other The other vector.
     * @return The Euclidean distance between them.
     */
    @ZenCodeType.Method("distanceTo")
    public static double distanceTo(final Vector3d internal, final Vector3d other) {
        return internal.distanceTo(other);
    }

    /**
     * Computes the squared Euclidean distance between this vector and the other vector.
     *
     * This method is faster and less error-prone than calling <code>distanceTo</code> and squaring the result.
     *
     * @param internal This vector.
     * @param other The other vector.
     * @return The squared Euclidean distance between them.
     */
    @ZenCodeType.Method("squareDistanceTo")
    public static double squareDistanceTo(final Vector3d internal, final Vector3d other) {
        return internal.squareDistanceTo(other);
    }

    /**
     * Scales the current vector by the given <code>factor</code>.
     *
     * @param internal This vector.
     * @param factor The factor.
     * @return The scaled vector.
     */
    @ZenCodeType.Method("scale")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    public static Vector3d scale(final Vector3d internal, final double factor) {
        return internal.scale(factor);
    }

    /**
     * Inverts the vector, effectively flipping it.
     *
     * This is equivalent to a scale by -1, or a rotation of pi radians around the origin.
     *
     * @param internal This vector.
     * @return The inverted vector.
     */
    @ZenCodeType.Getter("inverse")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.NEG)
    public static Vector3d invert(final Vector3d internal) {
        return internal.inverse();
    }

    /**
     * Gets the length, also known as magnitude, of the vector.
     *
     * @param internal This vector.
     * @return The magnitude.
     */
    @ZenCodeType.Getter("magnitude")
    public static double magnitude(final Vector3d internal) {
        return internal.length();
    }

    /**
     * Gets the squared length, also known as squared magnitude, of the vector.
     *
     * This method is faster and less error-prone than calling <code>magnitude</code> and squaring the result.
     *
     * @param internal This vector.
     * @return The squared magnitude.
     */
    @ZenCodeType.Getter("magnitudeSquared")
    public static double magnitudeSquared(final Vector3d internal) {
        return internal.lengthSquared();
    }
}
