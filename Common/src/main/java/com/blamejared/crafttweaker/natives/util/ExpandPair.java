package com.blamejared.crafttweaker.natives.util;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.mojang.datafixers.util.Pair;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

@ZenRegister
@Document("vanilla/api/util/Pair")
@NativeTypeRegistration(value = Pair.class, zenCodeName = "crafttweaker.api.util.Pair")
public class ExpandPair {
    
    @ZenCodeType.StaticExpansionMethod
    public static <F, S> Pair<F, S> of(F first, S second) {
        
        return Pair.of(first, second);
    }
    
    @ZenCodeType.Method
    public static <F, S> F getFirst(Pair internal) {
        
        return (F) internal.getFirst();
    }
    
    @ZenCodeType.Method
    public static <F, S> S getSecond(Pair internal) {
        
        return (S) internal.getSecond();
    }
    
    @ZenCodeType.Method
    public static <F, S> Pair<S, F> swap(Pair internal) {
        
        return internal.swap();
    }
    
    @ZenCodeType.Method
    public static <F, S, F2> Pair<F2, S> mapFirst(Pair internal, Function<F, F2> function) {
        
        return internal.mapFirst(function);
    }
    
    @ZenCodeType.Method
    public static <F, S, S2> Pair<F, S2> mapSecond(Pair internal, Function<S, S2> function) {
        
        return internal.mapSecond(function);
    }
    
}
