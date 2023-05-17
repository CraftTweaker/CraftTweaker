package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.EventBus;
import com.blamejared.crafttweaker.api.plugin.IEventRegistrationHandler;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.reflect.TypeToken;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.NoSuchElementException;

final class EventRegistrationManager {
    private static final MethodHandles.Lookup PUBLIC_LOOKUP = MethodHandles.publicLookup();
    
    void attemptRegistration(final Class<?> clazz, final IEventRegistrationHandler handler) {
        if (!clazz.isAnnotationPresent(ZenEvent.class)) {
            return;
        }
        
        this.attemptEventRegistration(clazz, handler);
    }
    
    private <T> void attemptEventRegistration(final Class<T> clazz, final IEventRegistrationHandler handler) {
        final TypeToken<T> token = TypeToken.of(clazz);
        final EventBus<T> bus = this.tryFindingBus(clazz);
        handler.registerEventMapping(token, bus);
    }
    
    private <T> EventBus<T> tryFindingBus(final Class<T> clazz) {
        try {
            return this.findBus(clazz);
        } catch (final Exception e) {
            throw new IllegalArgumentException("Registration of event class " + clazz.getName() + " failed");
        }
    }
    
    private <T> EventBus<T> findBus(final Class<T> clazz) throws IllegalAccessException {
        final Field target = Arrays.stream(clazz.getFields())
                .filter(it -> Modifier.isStatic(it.getModifiers()) && Modifier.isFinal(it.getModifiers()))
                .filter(it -> it.isAnnotationPresent(ZenEvent.Bus.class))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No public static final field annotated with @ZenEvent.Bus found"));
        final VarHandle fieldHandle = PUBLIC_LOOKUP.unreflectVarHandle(target);
        return GenericUtil.uncheck((EventBus<?>) fieldHandle.get());
    }
    
}
