package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.mojang.math.Matrix3f;
import com.mojang.math.Quaternion;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/Matrix3f")
@NativeTypeRegistration(value = Matrix3f.class, zenCodeName = "crafttweaker.api.util.math.Matrix3f")
public class ExpandMatrix3f {
    
    @ZenCodeType.Method
    public static void transpose(Matrix3f internal) {
        
        internal.transpose();
    }
    
    @ZenCodeType.Method
    public static void load(Matrix3f internal, Matrix3f other) {
        
        internal.load(other);
    }
    
    @ZenCodeType.Method
    public static void setIdentity(Matrix3f internal) {
        
        internal.setIdentity();
    }
    
    @ZenCodeType.Method
    public static float adjugateAndDet(Matrix3f internal) {
        
        return internal.adjugateAndDet();
    }
    
    @ZenCodeType.Method
    public static float determinant(Matrix3f internal) {
        
        return internal.determinant();
    }
    
    @ZenCodeType.Method
    public static boolean invert(Matrix3f internal) {
        
        return internal.invert();
    }
    
    @ZenCodeType.Method
    public static void setValue(Matrix3f internal, int row, int col, float value) {
        
        internal.set(row, col, value);
    }
    
    @ZenCodeType.Method
    public static void mul(Matrix3f internal, Matrix3f other) {
        
        internal.mul(other);
    }
    
    @ZenCodeType.Method
    public static void mul(Matrix3f internal, Quaternion quaternion) {
        
        internal.mul(quaternion);
    }
    
    @ZenCodeType.Method
    public static void mul(Matrix3f internal, float scalar) {
        
        internal.mul(scalar);
    }
    
    @ZenCodeType.Method
    public static void add(Matrix3f internal, Matrix3f other) {
        
        internal.add(other);
    }
    
    @ZenCodeType.Method
    public static void sub(Matrix3f internal, Matrix3f other) {
        
        internal.sub(other);
    }
    
    @ZenCodeType.Method
    public static float trace(Matrix3f internal) {
        
        return internal.trace();
    }
    
    @ZenCodeType.Method
    public static Matrix3f copy(Matrix3f internal) {
        
        return internal.copy();
    }
    
}
