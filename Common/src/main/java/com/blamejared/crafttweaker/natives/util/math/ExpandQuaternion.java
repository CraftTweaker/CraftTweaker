package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/Quaternion")
@NativeTypeRegistration(value = Quaternion.class, zenCodeName = "crafttweaker.api.util.math.Quaternion")
public class ExpandQuaternion {
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("xyz")
    public static Vector3f toXYZ(Quaternion internal) {
        
        return internal.toXYZ();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("xyzDegrees")
    public static Vector3f toXYZDegrees(Quaternion internal) {
        
        return internal.toXYZDegrees();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("yxz")
    public static Vector3f toYXZ(Quaternion internal) {
        
        return internal.toYXZ();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("yxzDegrees")
    public static Vector3f toYXZDegrees(Quaternion internal) {
        
        return internal.toYXZDegrees();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("i")
    public static float i(Quaternion internal) {
        
        return internal.i();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("j")
    public static float j(Quaternion internal) {
        
        return internal.j();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("k")
    public static float k(Quaternion internal) {
        
        return internal.k();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("r")
    public static float r(Quaternion internal) {
        
        return internal.r();
    }
    
    @ZenCodeType.Method
    public static void mul(Quaternion internal, Quaternion other) {
        
        internal.mul(other);
    }
    
    @ZenCodeType.Method
    public static void mul(Quaternion internal, float value) {
        
        internal.mul(value);
    }
    
    @ZenCodeType.Method
    public static void conj(Quaternion internal) {
        
        internal.conj();
    }
    
    @ZenCodeType.Method
    public static void setValue(Quaternion internal, float i, float j, float k, float r) {
        
        internal.set(i, j, k, r);
    }
    
    @ZenCodeType.Method
    public static void normalize(Quaternion internal) {
        
        internal.normalize();
    }
    
    @ZenCodeType.Method
    public static void slerp(Quaternion internal, Quaternion other, float value) {
        
        internal.slerp(other, value);
    }
    
    @ZenCodeType.Method
    public static Quaternion copy(Quaternion internal) {
        
        return internal.copy();
    }
    
}
