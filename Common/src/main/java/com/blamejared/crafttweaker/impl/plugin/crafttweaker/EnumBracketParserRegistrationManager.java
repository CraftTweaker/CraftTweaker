package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;

final class EnumBracketParserRegistrationManager {
    
    void attemptRegistration(final Class<?> clazz, final String loader, final IBracketParserRegistrationHandler handler) {
        
        if(clazz.isEnum()) {
            this.tryEnumRegistration(clazz, loader, handler);
        }
        if(clazz.isAnnotationPresent(NativeTypeRegistration.class)) {
            this.tryNativeRegistration(clazz, loader, handler);
        }
    }
    
    private void tryEnumRegistration(final Class<?> clazz, final String loader, final IBracketParserRegistrationHandler handler) {
        
        if(!clazz.isAnnotationPresent(BracketEnum.class)) {
            return;
        }
        
        this.tryRegistration(clazz, loader, clazz.getAnnotation(BracketEnum.class).value(), handler);
    }
    
    private void tryNativeRegistration(final Class<?> clazz, final String loader, final IBracketParserRegistrationHandler handler) {
        
        if(!clazz.isAnnotationPresent(BracketEnum.class)) {
            return;
        }
        
        final NativeTypeRegistration ntr = clazz.getAnnotation(NativeTypeRegistration.class);
        final Class<?> nativeType = ntr.value();
        if(!nativeType.isEnum()) {
            return;
        }
        
        this.tryRegistration(nativeType, loader, clazz.getAnnotation(BracketEnum.class).value(), handler);
    }
    
    private void tryRegistration(final Class<?> clazz, final String loader, final String value, final IBracketParserRegistrationHandler handler) {
        
        final ResourceLocation id;
        try {
            id = new ResourceLocation(value);
        } catch(final ResourceLocationException e) {
            throw new IllegalArgumentException("Provided resource location '" + value + "' for enum " + clazz.getName() + " is invalid", e);
        }
        
        handler.registerEnumForBracket(loader, id, this.uncheck(clazz));
    }
    
    @SuppressWarnings("unchecked")
    private <T, U> T uncheck(final U u) {
        
        return (T) u;
    }
    
}
