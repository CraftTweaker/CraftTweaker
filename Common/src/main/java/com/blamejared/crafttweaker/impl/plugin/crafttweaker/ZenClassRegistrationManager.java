package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.plugin.IJavaNativeIntegrationRegistrationHandler;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;
import com.blamejared.crafttweaker_annotations.annotations.NativeMethod;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;
import org.openzen.zencode.java.ZenCodeType;

final class ZenClassRegistrationManager {
    
    private final AnnotationsToApiConverters converters;
    
    ZenClassRegistrationManager() {
        
        this.converters = new AnnotationsToApiConverters();
    }
    
    void attemptRegistration(final String loader, final Class<?> clazz, final IJavaNativeIntegrationRegistrationHandler handler) {
        
        this.attemptNativeRegistration(clazz, loader, handler);
        this.attemptZenRegistration(clazz, loader, handler);
        this.attemptPreprocessorRegistration(clazz, loader, handler);
    }
    
    private void attemptNativeRegistration(final Class<?> clazz, final String loader, final IJavaNativeIntegrationRegistrationHandler handler) {
        
        final NativeTypeRegistration ntr = clazz.getDeclaredAnnotation(NativeTypeRegistration.class);
        if(ntr == null) {
            return;
        }
        final NativeMethod[] methods = clazz.getDeclaredAnnotationsByType(NativeMethod.class);
        
        handler.registerNativeType(loader, clazz, this.converters.toNativeTypeInfo(ntr, methods));
    }
    
    private void attemptZenRegistration(final Class<?> clazz, final String loader, final IJavaNativeIntegrationRegistrationHandler handler) {
        
        this.attemptZenClassRegistration(clazz, loader, handler);
        this.attemptZenExpandRegistration(clazz, loader, handler);
        this.attemptTypedExpandRegistration(clazz, loader, handler);
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
        final String targetName = this.figureOutTypedExpansionName(target);
        if(targetName == null) {
            throw new IllegalStateException("Unable to register typed expansion for unknown type " + clazz.getName());
        }
        
        handler.registerZenClass(loader, clazz, new ZenTypeInfo(targetName, ZenTypeInfo.TypeKind.EXPANSION));
    }
    
    private void attemptPreprocessorRegistration(final Class<?> clazz, final String loader, final IJavaNativeIntegrationRegistrationHandler handler) {
        
        final Preprocessor preprocessor = clazz.getDeclaredAnnotation(Preprocessor.class);
        if(preprocessor == null) {
            return;
        }
        
        handler.registerPreprocessor((IPreprocessor) InstantiationUtil.getOrCreateInstance(clazz));
    }
    
    private String figureOutTypedExpansionName(final Class<?> target) {
        
        final NativeTypeRegistration registration = target.getDeclaredAnnotation(NativeTypeRegistration.class);
        if(registration != null) {
            return registration.zenCodeName();
        }
        
        final ZenCodeType.Name name = target.getDeclaredAnnotation(ZenCodeType.Name.class);
        if(name != null) {
            return name.value();
        }
        
        return null;
    }
    
}
