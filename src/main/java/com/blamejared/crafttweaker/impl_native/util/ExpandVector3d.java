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
    public static double getX(final Vector3d $this) {
        return $this.getX();
    }

    @ZenCodeType.Getter("y")
    public static double getY(final Vector3d $this) {
        return $this.getY();
    }

    @ZenCodeType.Getter("z")
    public static double getZ(final Vector3d $this) {
        return $this.getZ();
    }

    @ZenCodeType.Method("subtractReverse")
    public static Vector3d subtractReverse(final Vector3d $this, final Vector3d other) {
        return $this.subtractReverse(other);
    }

    @ZenCodeType.Method("normalize")
    public static Vector3d normalize(final Vector3d $this) {
        return $this.normalize();
    }

    @ZenCodeType.Method("dot")
    public static double dot(final Vector3d $this, final Vector3d other) {
        return $this.dotProduct(other);
    }

    @ZenCodeType.Method("cross")
    public static Vector3d cross(final Vector3d $this, final Vector3d other) {
        return $this.crossProduct(other);
    }

    @ZenCodeType.Method("times")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    public static Vector3d mul(final Vector3d $this, final Vector3d other) {
        return $this.mul(other);
    }

    @ZenCodeType.Method("times")
    public static Vector3d mul(final Vector3d $this, final double x, final double y, final double z) {
        return $this.mul(x, y, z);
    }

    @ZenCodeType.Method("add")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.ADD)
    public static Vector3d add(final Vector3d $this, final Vector3d other) {
        return $this.add(other);
    }

    @ZenCodeType.Method("add")
    public static Vector3d add(final Vector3d $this, final double x, final double y, final double z) {
        return $this.add(x, y, z);
    }

    @ZenCodeType.Method("subtract")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.SUB)
    public static Vector3d sub(final Vector3d $this, final Vector3d other) {
        return $this.subtract(other);
    }

    @ZenCodeType.Method("subtract")
    public static Vector3d sub(final Vector3d $this, final double x, final double y, final double z) {
        return $this.subtract(x, y, z);
    }

    @ZenCodeType.Method("getCoordinate")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    public static double getCoordinate(final Vector3d $this, final int index) {
        switch (index) {
            case 0: return $this.getX();
            case 1: return $this.getY();
            case 2: return $this.getZ();
        }
        throw new ArrayIndexOutOfBoundsException(index);
    }

    @ZenCodeType.Method("getCoordinate")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    public static double getCoordinate(final Vector3d $this, final Direction.Axis axis) {
        return $this.getCoordinate(axis);
    }

    @ZenCodeType.Method("distanceTo")
    public static double distanceTo(final Vector3d $this, final Vector3d other) {
        return $this.distanceTo(other);
    }

    @ZenCodeType.Method("squareDistanceTo")
    public static double squareDistanceTo(final Vector3d $this, final Vector3d other) {
        return $this.squareDistanceTo(other);
    }

    @ZenCodeType.Method("scale")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    public static Vector3d scale(final Vector3d $this, final double factor) {
        return $this.scale(factor);
    }

    @ZenCodeType.Getter("inverse")
    @ZenCodeType.Operator(ZenCodeType.OperatorType.NEG)
    public static Vector3d invert(final Vector3d $this) {
        return $this.inverse();
    }

    @ZenCodeType.Getter("magnitude")
    public static double magnitude(final Vector3d $this) {
        return $this.length();
    }

    @ZenCodeType.Getter("magnitudeSquared")
    public static double magnitudeSquared(final Vector3d $this) {
        return $this.lengthSquared();
    }
}
