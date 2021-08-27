package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Everything in this class will be removed at some point.
 * When replacements for this code is made, these methods will shout at you telling you to stop using them.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.unsafe.MathHelper")
public class MathHelper {
    
    @ZenCodeType.Field
    public static double E = Math.E;
    
    @ZenCodeType.Field
    public static double PI = Math.PI;
    
    @ZenCodeType.Method
    public static int round(float value) {
        
        return Math.round(value);
    }
    
    @ZenCodeType.Method
    public static long round(double value) {
        
        return Math.round(value);
    }
    
    @ZenCodeType.Method
    public static double floor(double value) {
        
        return Math.floor(value);
    }
    
    
    @ZenCodeType.Method
    public static double ceil(double value) {
        
        return Math.ceil(value);
    }
    
    @ZenCodeType.Method
    public static double abs(double value) {
        
        return Math.abs(value);
    }
    
    @ZenCodeType.Method
    public static int abs(int value) {
        
        return Math.abs(value);
    }
    
    
    @ZenCodeType.Method
    public static double pow(double value, double exp) {
        
        return Math.pow(value, exp);
    }
    
    @ZenCodeType.Method
    public static double sqrt(double value) {
        
        return Math.sqrt(value);
    }
    
    @ZenCodeType.Method
    public static double log(double value) {
        
        return Math.log(value);
    }
    
    
}

