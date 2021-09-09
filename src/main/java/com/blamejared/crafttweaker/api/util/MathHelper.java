package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Everything in this class will be removed at some point.
 * When replacements for this code is made, these methods will shout at you telling you to stop using them.
 */
@Deprecated
@ZenRegister
@ZenCodeType.Name("crafttweaker.unsafe.MathHelper")
public class MathHelper {
    
    @Deprecated
    @ZenCodeType.Field
    public static double E = Math.E;
    
    @Deprecated
    @ZenCodeType.Field
    public static double PI = Math.PI;
    
    @Deprecated
    @ZenCodeType.Method
    public static int round(float value) {
        
        CraftTweakerAPI.logWarning("`crafttweaker.unsafe.MathHelper.round(float value)` has been replaced by `math.Functions.round(value as float)`! This method will be removed in the future!");
        return Math.round(value);
    }
    
    @Deprecated
    @ZenCodeType.Method
    public static long round(double value) {
        
        CraftTweakerAPI.logWarning("`crafttweaker.unsafe.MathHelper.round(double value)` has been replaced by `math.Functions.round(value as double)`! This method will be removed in the future!");
        return Math.round(value);
    }
    
    @Deprecated
    @ZenCodeType.Method
    public static double floor(double value) {
        
        CraftTweakerAPI.logWarning("`crafttweaker.unsafe.MathHelper.floor(double value)` has been replaced by `math.Functions.floor(value as double)`! This method will be removed in the future!");
        return Math.floor(value);
    }
    
    @Deprecated
    @ZenCodeType.Method
    public static double ceil(double value) {
        
        CraftTweakerAPI.logWarning("`crafttweaker.unsafe.MathHelper.ceil(double value)` has been replaced by `math.Functions.ceil(value as double)`! This method will be removed in the future!");
        return Math.ceil(value);
    }
    
    @Deprecated
    @ZenCodeType.Method
    public static double abs(double value) {
        
        CraftTweakerAPI.logWarning("`crafttweaker.unsafe.MathHelper.abs(double value)` has been replaced by `math.Functions.abs(value as double)`! This method will be removed in the future!");
        return Math.abs(value);
    }
    
    @Deprecated
    @ZenCodeType.Method
    public static int abs(int value) {
        
        CraftTweakerAPI.logWarning("`crafttweaker.unsafe.MathHelper.abs(int value)` has been replaced by `math.Functions.abs(value as int)`! This method will be removed in the future!");
        return Math.abs(value);
    }
    
    @Deprecated
    @ZenCodeType.Method
    public static double pow(double value, double exp) {
        
        CraftTweakerAPI.logWarning("`crafttweaker.unsafe.MathHelper.pow(double value, double exp)` has been replaced by `math.Functions.pow(value as double, exp as double)`! This method will be removed in the future!");
        return Math.pow(value, exp);
    }
    
    @Deprecated
    @ZenCodeType.Method
    public static double sqrt(double value) {
        
        CraftTweakerAPI.logWarning("`crafttweaker.unsafe.MathHelper.sqrt(double value)` has been replaced by `math.Functions.sqrt(value as double)`! This method will be removed in the future!");
        return Math.sqrt(value);
    }
    
    @Deprecated
    @ZenCodeType.Method
    public static double log(double value) {
        
        CraftTweakerAPI.logWarning("`crafttweaker.unsafe.MathHelper.log(double value)` has been replaced by `math.Functions.log(value as double)`! This method will be removed in the future!");
        return Math.log(value);
    }
    
    
}

