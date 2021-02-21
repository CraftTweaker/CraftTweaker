package com.blamejared.crafttweaker.impl_native.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import org.openzen.zencode.java.ZenCodeType;

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
    @ZenCodeType.Getter("x")
    public static double getX(final Vector3d internal) {
        return internal.getX();
    }

    @ZenCodeType.Getter("y")
    public static double getY(final Vector3d internal) {
        return internal.getY();
    }

    @ZenCodeType.Getter("z")
    public static double getZ(final Vector3d internal) {
        return internal.getZ();
    }

    @ZenCodeType.Method("subtractReverse")
    public static Vector3d subtractReverse(final Vector3d internal, final Vector3d other) {
        return internal.subtractReverse(other);
    }

    @ZenCodeType.Method("normalize")
    public static Vector3d normalize(final Vector3d internal) {
        return internal.normalize();
    }

    @ZenCodeType.Method("dot")
    public static double dot(final Vector3d internal, final Vector3d other) {
        return internal.dotProduct(other);
    }

    @ZenCodeType.Method("cross")
    public static Vector3d cross(final Vector3d internal, final Vector3d other) {
        return internal.crossProduct(other);
    }

    @ZenCodeType.Method("times")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    public static Vector3d times(final Vector3d internal, final Vector3d other) {
        return internal.mul(other);
    }

    @ZenCodeType.Method("times")
    public static Vector3d times(final Vector3d internal, final double x, final double y, final double z) {
        return internal.mul(x, y, z);
    }

    @ZenCodeType.Method("plus")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.ADD)
    public static Vector3d plus(final Vector3d internal, final Vector3d other) {
        return internal.add(other);
    }

    @ZenCodeType.Method("plus")
    public static Vector3d plus(final Vector3d internal, final double x, final double y, final double z) {
        return internal.add(x, y, z);
    }

    @ZenCodeType.Method("minus")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.SUB)
    public static Vector3d minus(final Vector3d internal, final Vector3d other) {
        return internal.subtract(other);
    }

    @ZenCodeType.Method("minus")
    public static Vector3d minus(final Vector3d internal, final double x, final double y, final double z) {
        return internal.subtract(x, y, z);
    }

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

    @ZenCodeType.Method("getCoordinate")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    public static double getCoordinate(final Vector3d internal, final Direction.Axis axis) {
        return internal.getCoordinate(axis);
    }

    @ZenCodeType.Method("distanceTo")
    public static double distanceTo(final Vector3d internal, final Vector3d other) {
        return internal.distanceTo(other);
    }

    @ZenCodeType.Method("squareDistanceTo")
    public static double squareDistanceTo(final Vector3d internal, final Vector3d other) {
        return internal.squareDistanceTo(other);
    }

    @ZenCodeType.Method("scale")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    public static Vector3d scale(final Vector3d internal, final double factor) {
        return internal.scale(factor);
    }

    @ZenCodeType.Getter("inverse")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.NEG)
    public static Vector3d invert(final Vector3d internal) {
        return internal.inverse();
    }

    @ZenCodeType.Getter("magnitude")
    public static double magnitude(final Vector3d internal) {
        return internal.length();
    }

    @ZenCodeType.Getter("magnitudeSquared")
    public static double magnitudeSquared(final Vector3d internal) {
        return internal.lengthSquared();
    }
}
