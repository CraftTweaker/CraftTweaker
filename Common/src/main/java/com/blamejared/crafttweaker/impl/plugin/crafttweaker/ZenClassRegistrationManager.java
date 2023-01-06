package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.plugin.IJavaNativeIntegrationRegistrationHandler;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;
import com.blamejared.crafttweaker_annotations.annotations.NativeMethod;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayDeque;
import java.util.Queue;

final class ZenClassRegistrationManager {
    
    @FunctionalInterface
    private interface LateCallback {
        
        void callback(final Class<?> clazz, final String loader, final IJavaNativeIntegrationRegistrationHandler handler);
        
    }
    
    private record LateRegistrationCandidate(Class<?> clazz, String loader, LateCallback callback) {}
    
    private final AnnotationsToApiConverters converters;
    private final Table<Class<?>, String, NativeTypeInfo> foundNatives;
    private final Queue<LateRegistrationCandidate> lateRegistrations;
    
    ZenClassRegistrationManager() {
        
        this.converters = new AnnotationsToApiConverters();
        this.foundNatives = HashBasedTable.create();
        this.lateRegistrations = new ArrayDeque<>();
    }
    
    void attemptRegistration(final String loader, final Class<?> clazz, final IJavaNativeIntegrationRegistrationHandler handler) {
        
        this.attemptNativeRegistration(clazz, loader, handler);
        this.attemptZenRegistration(clazz, loader, handler);
        this.attemptPreprocessorRegistration(clazz, handler);
    }
    
    void attemptDeferredRegistration(final IJavaNativeIntegrationRegistrationHandler handler) {
        
        this.lateRegistrations.forEach(it -> it.callback().callback(it.clazz(), it.loader(), handler));
        this.lateRegistrations.clear();
    }
    
    private void attemptNativeRegistration(final Class<?> clazz, final String loader, final IJavaNativeIntegrationRegistrationHandler handler) {
        
        final NativeTypeRegistration ntr = clazz.getDeclaredAnnotation(NativeTypeRegistration.class);
        if(ntr == null) {
            return;
        }
        final NativeMethod[] methods = clazz.getDeclaredAnnotationsByType(NativeMethod.class);
        final NativeTypeInfo nativeTypeInfo = this.converters.toNativeTypeInfo(ntr, methods);
        
        final NativeTypeInfo previous = this.foundNatives.get(nativeTypeInfo.targetedType(), loader);
        if(previous != null) {
            CraftTweakerCommon.logger().warn(
                    "Found two native expansions for the same class {} in loader {}, current {}, new {}: this will lead to issues",
                    nativeTypeInfo.targetedType().getName(),
                    loader,
                    previous,
                    nativeTypeInfo
            );
        }
        
        this.foundNatives.put(nativeTypeInfo.targetedType(), loader, nativeTypeInfo);
        handler.registerNativeType(loader, clazz, nativeTypeInfo);
    }
    
    private void attemptZenRegistration(final Class<?> clazz, final String loader, final IJavaNativeIntegrationRegistrationHandler handler) {
        
        this.attemptZenClassRegistration(clazz, loader, handler);
        this.attemptZenExpandRegistration(clazz, loader, handler);
        this.orFuture(clazz, loader, handler, this::attemptTypedExpandRegistration);
    }
    
    private void attemptZenClassRegistration(final Class<?> clazz, final String loader, final IJavaNativeIntegrationRegistrationHandler handler) {
        
        final ZenCodeType.Name name = clazz.getDeclaredAnnotation(ZenCodeType.Name.class);
        if(name == null) {
            return;
        }
        
        final ZenTypeInfo typeInfo = this.converters.toZenTypeInfo(name);
        handler.registerZenClass(loader, clazz, typeInfo);
        handler.registerGlobalsIn(loader, clazz, typeInfo);
    }
    
    private void attemptZenExpandRegistration(final Class<?> clazz, final String loader, final IJavaNativeIntegrationRegistrationHandler handler) {
        
        final ZenCodeType.Expansion expansion = clazz.getDeclaredAnnotation(ZenCodeType.Expansion.class);
        if(expansion == null) {
            return;
        }
        
        handler.registerZenClass(loader, clazz, this.converters.toZenTypeInfo(expansion));
    }
    
    private void attemptTypedExpandRegistration(final Class<?> clazz, final String loader, final IJavaNativeIntegrationRegistrationHandler handler) {
        
        final TypedExpansion expansion = clazz.getDeclaredAnnotation(TypedExpansion.class);
        if(expansion == null) {
            return;
        }
        
        final Class<?> target = expansion.value();
        final String targetName = this.figureOutTypedExpansionName(loader, target);
        if(targetName == null) {
            throw new IllegalStateException("Unable to register typed expansion for unknown type " + clazz.getName());
        }
        
        handler.registerZenClass(loader, clazz, new ZenTypeInfo(targetName, ZenTypeInfo.TypeKind.EXPANSION));
    }
    
    private void attemptPreprocessorRegistration(final Class<?> clazz, final IJavaNativeIntegrationRegistrationHandler handler) {
        
        final Preprocessor preprocessor = clazz.getDeclaredAnnotation(Preprocessor.class);
        if(preprocessor == null) {
            return;
        }
        
        handler.registerPreprocessor((IPreprocessor) InstantiationUtil.getOrCreateInstance(clazz));
    }
    
    private void orFuture(final Class<?> clazz, final String loader, final IJavaNativeIntegrationRegistrationHandler handler, final LateCallback callback) {
        
        try {
            callback.callback(clazz, loader, handler);
        } catch(final Exception e) {
            // Let's try again later: maybe some information is not yet available
            this.lateRegistrations.add(new LateRegistrationCandidate(clazz, loader, callback));
        }
    }
    
    private String figureOutTypedExpansionName(final String loader, final Class<?> target) {
        
        final NativeTypeRegistration registration = target.getDeclaredAnnotation(NativeTypeRegistration.class);
        if(registration != null) {
            return registration.zenCodeName();
        }
        
        final ZenCodeType.Name name = target.getDeclaredAnnotation(ZenCodeType.Name.class);
        if(name != null) {
            return name.value();
        }
        
        final NativeTypeInfo knownNative = this.foundNatives.get(target, loader);
        if(knownNative != null) {
            return knownNative.name();
        }
        
        return null;
    }
    
}
