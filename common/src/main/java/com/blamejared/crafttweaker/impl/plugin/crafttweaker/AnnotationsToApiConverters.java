package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeMethod;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

final class AnnotationsToApiConverters {
    
    NativeTypeInfo toNativeTypeInfo(final NativeTypeRegistration registration, final NativeMethod... methods) {
        
        return new NativeTypeInfo(
                registration.zenCodeName(),
                registration.value(),
                this.toNativeConstructors(registration.constructors()),
                this.toNativeMethods(methods)
        );
    }
    
    ZenTypeInfo toZenTypeInfo(final ZenCodeType.Name name) {
        
        return new ZenTypeInfo(name.value(), ZenTypeInfo.TypeKind.CLASS);
    }
    
    ZenTypeInfo toZenTypeInfo(final ZenCodeType.Expansion expansion) {
        
        return new ZenTypeInfo(expansion.value(), ZenTypeInfo.TypeKind.EXPANSION);
    }
    
    private NativeTypeInfo.Constructor[] toNativeConstructors(final NativeConstructor... constructors) {
        
        return Arrays.stream(constructors).map(this::toNativeConstructor).toArray(NativeTypeInfo.Constructor[]::new);
    }
    
    private NativeTypeInfo.Constructor toNativeConstructor(final NativeConstructor constructor) {
        
        return new NativeTypeInfo.Constructor(
                this.toNativeParameters(constructor.value())
        );
    }
    
    private NativeTypeInfo.Parameter[] toNativeParameters(final NativeConstructor.ConstructorParameter... parameters) {
        
        return Arrays.stream(parameters).map(this::toNativeParameter).toArray(NativeTypeInfo.Parameter[]::new);
    }
    
    private NativeTypeInfo.Parameter toNativeParameter(final NativeConstructor.ConstructorParameter parameter) {
        
        return new NativeTypeInfo.Parameter(
                parameter.type(),
                parameter.name()
        );
    }
    
    private NativeTypeInfo.Method[] toNativeMethods(final NativeMethod... methods) {
        
        return Arrays.stream(methods).map(this::toNativeMethod).toArray(NativeTypeInfo.Method[]::new);
    }
    
    private NativeTypeInfo.Method toNativeMethod(final NativeMethod method) {
        
        return new NativeTypeInfo.Method(
                method.name(),
                method.getterName(),
                method.setterName(),
                this.toNativeParameters(method.parameters())
        );
    }
    
    private NativeTypeInfo.Parameter[] toNativeParameters(final NativeMethod.MethodParameter... parameterTypes) {
        
        return Arrays.stream(parameterTypes)
                .map(it -> new NativeTypeInfo.Parameter(it.type(), it.name()))
                .toArray(NativeTypeInfo.Parameter[]::new);
    }
    
}
