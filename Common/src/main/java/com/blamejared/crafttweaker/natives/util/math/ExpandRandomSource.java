package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.RandomSource;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/RandomSource")
@NativeTypeRegistration(value = RandomSource.class, zenCodeName = "crafttweaker.api.util.math.RandomSource")
public class ExpandRandomSource {
    
    @ZenCodeType.Method
    public static int nextInt(RandomSource internal) {
        
        return internal.nextInt();
    }
    
    @ZenCodeType.Method
    public static int nextInt(RandomSource internal, int bound) {
        
        return internal.nextInt(bound);
    }
    
    @ZenCodeType.Method
    public static int nextIntBetweenInclusive(RandomSource internal, int origin, int bound) {
        
        return internal.nextIntBetweenInclusive(origin, bound);
    }
    
    @ZenCodeType.Method
    public static long nextLong(RandomSource internal) {
        
        return internal.nextLong();
    }
    
    @ZenCodeType.Method
    public static boolean nextBoolean(RandomSource internal) {
        
        return internal.nextBoolean();
    }
    
    @ZenCodeType.Method
    public static float nextFloat(RandomSource internal) {
        
        return internal.nextFloat();
    }
    
    @ZenCodeType.Method
    public static double nextDouble(RandomSource internal) {
        
        return internal.nextDouble();
    }
    
    @ZenCodeType.Method
    public static double nextGaussian(RandomSource internal) {
        
        return internal.nextGaussian();
    }
    
    @ZenCodeType.Method
    public static int nextInt(RandomSource internal, int bound, int origin) {
        
        return internal.nextInt(bound, origin);
    }
    
}
