package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.plugin.IEventRegistrationHandler;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.google.common.reflect.TypeToken;
import org.openzen.zencode.java.ZenCodeType;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

final class EventRegistrationManager {
    private static final MethodHandles.Lookup PUBLIC_LOOKUP = MethodHandles.publicLookup();
    
    void attemptRegistration(final Class<?> clazz, final IEventRegistrationHandler handler) {
        if (!clazz.isAnnotationPresent(ZenEvent.class) && !clazz.isAnnotationPresent(ZenEvent.BusCarrier.class)) {
            return;
        }
        
        this.attemptEventRegistration(clazz, handler);
    }
    
    private void attemptEventRegistration(final Class<?> clazz, final IEventRegistrationHandler handler) {
        final List<Field> busCandidates = this.findBuses(clazz);
        if (busCandidates.isEmpty()) {
            throw new NoSuchElementException("Class " + clazz.getName() + " is annotated with @ZenEvent or @ZenEvent.BusCarrier, but carries no buses");
        }
        
        this.attemptEventRegistration(clazz, busCandidates, handler);
    }
    
    private List<Field> findBuses(final Class<?> clazz) {
        return Arrays.stream(clazz.getFields())
                .filter(it -> Modifier.isStatic(it.getModifiers()) && Modifier.isFinal(it.getModifiers()))
                .filter(it -> it.getType() == IEventBus.class)
                .filter(it -> it.isAnnotationPresent(ZenEvent.Bus.class))
                .toList();
    }
    
    private void attemptEventRegistration(final Class<?> clazz, final List<Field> candidates, final IEventRegistrationHandler handler) {
        candidates.forEach(candidate -> this.attemptEventRegistration(clazz, candidate, handler));
    }
    
    private void attemptEventRegistration(final Class<?> clazz, final Field candidate, final IEventRegistrationHandler handler) {
        final ZenEvent.Bus busAnnotation = candidate.getAnnotation(ZenEvent.Bus.class);
        final Class<?> potentialTarget = busAnnotation.value();
        final Class<?> target = this.determineCandidate(clazz, potentialTarget);
        final VarHandle handle = this.varHandle(candidate);
        this.register(target, handle, handler);
    }
    
    private Class<?> determineCandidate(final Class<?> owner, final Class<?> potentialTarget) {
        if (potentialTarget != ZenEvent.Bus.Auto.class) {
            return potentialTarget;
        }
        
        if (owner.isAnnotationPresent(ZenEvent.class)) {
            if (owner.isAnnotationPresent(ZenCodeType.Name.class)) {
                return owner;
            }
            
            if (owner.isAnnotationPresent(NativeTypeRegistration.class)) {
                final NativeTypeRegistration registration = owner.getAnnotation(NativeTypeRegistration.class);
                return registration.value();
            }
        }
        
        throw new IllegalStateException("Automatic determination of target failed");
    }
    
    private <T> void register(final Class<T> target, final VarHandle candidate, final IEventRegistrationHandler handler) {
        final IEventBus<T> bus = GenericUtil.uncheck((IEventBus<?>) candidate.get());
        final TypeToken<T> token = TypeToken.of(target);
        handler.registerEventMapping(token, bus);
    }
    
    private VarHandle varHandle(final Field field) {
        try {
            return PUBLIC_LOOKUP.unreflectVarHandle(field);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
}
