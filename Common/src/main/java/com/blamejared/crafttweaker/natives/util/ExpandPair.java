package com.blamejared.crafttweaker.natives.util;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeMethod;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.mojang.datafixers.util.Pair;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

@ZenRegister
@Document("vanilla/api/util/Pair")
@NativeTypeRegistration(value = Pair.class, zenCodeName = "crafttweaker.api.util.Pair")
@NativeMethod(name = "getFirst", getterName = "first", parameters = {})
@NativeMethod(name = "getSecond", getterName = "second", parameters = {})
@NativeMethod(name = "swap", parameters = {})
public class ExpandPair {
    
    @ZenCodeType.StaticExpansionMethod
    public static <F, S> Pair<F, S> of(Class<F> fClass, Class<S> sClass, F first, S second) {
        
        return Pair.of(first, second);
    }
    
}
