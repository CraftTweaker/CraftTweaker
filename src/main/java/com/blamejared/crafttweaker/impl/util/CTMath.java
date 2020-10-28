package com.blamejared.crafttweaker.impl.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.util.Math")
public class CTMath {
    @ZenCodeType.Field
    public static final double PI = Math.PI;

    @ZenCodeType.Field
    public static final double E = Math.E;

    @ZenCodeType.Method
    public static double max(double d1, double d2)
    {
        return Math.max(d1, d2);
    }

    @ZenCodeType.Method
    public static float max(float f1, float f2)
    {
        return Math.max(f1, f2);
    }

    @ZenCodeType.Method
    public static long max(long l1, long l2)
    {
        return Math.max(l1, l2);
    }

    @ZenCodeType.Method
    public static int max(int i1, int i2)
    {
        return Math.max(i1, i2);
    }

    @ZenCodeType.Method
    public static double min(double d1, double d2)
    {
        return Math.min(d1, d2);
    }

    @ZenCodeType.Method
    public static float min(float f1, float f2)
    {
        return Math.min(f1, f2);
    }

    @ZenCodeType.Method
    public static long min(long l1, long l2)
    {
        return Math.min(l1, l2);
    }

    @ZenCodeType.Method
    public static int min(int i1, int i2)
    {
        return Math.min(i1, i2);
    }

    @ZenCodeType.Method
    public static long floor(double d1)
    {
        return new Double(Math.floor(d1)).longValue();
    }

    @ZenCodeType.Method
    public static long ceil(double d1)
    {
        return new Double(Math.ceil(d1)).longValue();
    }

    @ZenCodeType.Method
    public static double abs(double d1)
    {
        return Math.abs(d1);
    }

    @ZenCodeType.Method
    public static float abs(float f1)
    {
        return Math.abs(f1);
    }

    @ZenCodeType.Method
    public static long abs(long l1)
    {
        return Math.abs(l1);
    }

    @ZenCodeType.Method
    public static int abs(int i1)
    {
        return Math.abs(i1);
    }

    @ZenCodeType.Method
    public static double sin(double d1)
    {
        return Math.sin(d1);
    }

    @ZenCodeType.Method
    public static double cos(double d1)
    {
        return Math.cos(d1);
    }

    @ZenCodeType.Method
    public static double tan(double d1)
    {
        return Math.tan(d1);
    }

    @ZenCodeType.Method
    public static double asin(double d1)
    {
        return Math.asin(d1);
    }

    @ZenCodeType.Method
    public static double acos(double d1)
    {
        return Math.acos(d1);
    }

    @ZenCodeType.Method
    public static double atan(double d1)
    {
        return Math.atan(d1);
    }

    @ZenCodeType.Method
    public static double sinh(double d1)
    {
        return Math.sinh(d1);
    }

    @ZenCodeType.Method
    public static double cosh(double d1)
    {
        return Math.cosh(d1);
    }

    @ZenCodeType.Method
    public static double tanh(double d1)
    {
        return Math.tanh(d1);
    }

    @ZenCodeType.Method
    public static double sqrt(double d1)
    {
        return Math.sqrt(d1);
    }

    @ZenCodeType.Method
    public static int round(float f1)
    {
        return Math.round(f1);
    }

    @ZenCodeType.Method
    public static long round(double d1)
    {
        return Math.round(d1);
    }

    @ZenCodeType.Method
    public static double clamp(double value, double min, double max)
    {
        return (value < min) ? min : (value > max) ? max : value;
    }

    @ZenCodeType.Method
    public static float clamp(float value, float min, float max)
    {
        return (value < min) ? min : (value > max) ? max : value;
    }

    @ZenCodeType.Method
    public static int clamp(int value, int min, int max)
    {
        return (value < min) ? min : (value > max) ? max : value;
    }

    @ZenCodeType.Method
    public static long clamp(long value, long min, long max)
    {
        return (value < min) ? min : (value > max) ? max : value;
    }

    @ZenCodeType.Method
    public static double log(double input) { return Math.log(input); }

    @ZenCodeType.Method
    public static double log10(double input) { return Math.log10(input); }
}
