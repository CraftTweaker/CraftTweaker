//package com.blamejared.crafttweaker.natives.util.math;
//
//import com.blamejared.crafttweaker.api.annotation.ZenRegister;
//import com.blamejared.crafttweaker_annotations.annotations.Document;
//import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
//import org.joml.Matrix3f;
//import org.joml.Vector3f;
//import org.openzen.zencode.java.ZenCodeType;
//
//@ZenRegister
//@Document("vanilla/api/util/math/Vector3f")
//@NativeTypeRegistration(value = Vector3f.class, zenCodeName = "crafttweaker.api.util.math.Vector3f")
//public class ExpandVector3f {
//
//    @ZenCodeType.Method
//    @ZenCodeType.Getter("x")
//    public static float x(Vector3f internal) {
//
//        return internal.x();
//    }
//
//    @ZenCodeType.Method
//    @ZenCodeType.Getter("y")
//    public static float y(Vector3f internal) {
//
//        return internal.y();
//    }
//
//    @ZenCodeType.Method
//    @ZenCodeType.Getter("z")
//    public static float z(Vector3f internal) {
//
//        return internal.z();
//    }
//
//    @ZenCodeType.Method
//    public static void mul(Vector3f internal, float scalar) {
//
//        internal.mul(scalar);
//    }
//
//    @ZenCodeType.Method
//    public static void mul(Vector3f internal, float x, float y, float z) {
//
//        internal.mul(x, y, z);
//    }
//
//    @ZenCodeType.Method
//    public static void clamp(Vector3f internal, Vector3f min, Vector3f max) {
//
//        internal.clamp(min, max);
//    }
//
//    @ZenCodeType.Method
//    public static void clamp(Vector3f internal, float min, float max) {
//
//        internal.clamp(min, max);
//    }
//
//    @ZenCodeType.Method
//    public static void setValues(Vector3f internal, float x, float y, float z) {
//
//        internal.set(x, y, z);
//    }
//
//    @ZenCodeType.Method
//    public static void load(Vector3f internal, Vector3f other) {
//
//        internal.load(other);
//    }
//
//    @ZenCodeType.Method
//    public static void add(Vector3f internal, float x, float y, float z) {
//
//        internal.add(x, y, z);
//    }
//
//    @ZenCodeType.Method
//    public static void add(Vector3f internal, Vector3f other) {
//
//        internal.add(other);
//    }
//
//    @ZenCodeType.Method
//    public static void sub(Vector3f internal, Vector3f other) {
//
//        internal.sub(other);
//    }
//
//    @ZenCodeType.Method
//    public static float dot(Vector3f internal, Vector3f other) {
//
//        return internal.dot(other);
//    }
//
//    @ZenCodeType.Method
//    public static boolean normalize(Vector3f internal) {
//
//        return internal.normalize();
//    }
//
//    @ZenCodeType.Method
//    public static void cross(Vector3f internal, Vector3f other) {
//
//        internal.cross(other);
//    }
//
//    @ZenCodeType.Method
//    public static void transform(Vector3f internal, Matrix3f matrix) {
//
//        internal.transform(matrix);
//    }
//
//    @ZenCodeType.Method
//    public static void transform(Vector3f internal, Quaternion quaternion) {
//
//        internal.transform(quaternion);
//    }
//
//    @ZenCodeType.Method
//    public static void lerp(Vector3f internal, Vector3f max, float value) {
//
//        internal.lerp(max, value);
//    }
//
//    @ZenCodeType.Method
//    public static Quaternion rotation(Vector3f internal, float rads) {
//
//        return internal.rotation(rads);
//    }
//
//    @ZenCodeType.Method
//    public static Quaternion rotationDegrees(Vector3f internal, float degrees) {
//
//        return internal.rotationDegrees(degrees);
//    }
//
//    @ZenCodeType.Method
//    public static Vector3f copy(Vector3f internal) {
//
//        return internal.copy();
//    }
//
//
//}
