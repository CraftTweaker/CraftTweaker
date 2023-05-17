package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.ICancelableEvent;
import com.blamejared.crafttweaker.api.event.Phase;
import com.google.common.reflect.TypeToken;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

public final class EventBus<T> {
    
    private final TypeToken<T> eventType;
    private final Set<EventBusCharacteristic> characteristics;
    private final BusDispatcher<T> dispatcher;
    private final ReadWriteLock lock;
    
    private EventBus(final TypeToken<T> eventType, final Set<EventBusCharacteristic> characteristics, final BusDispatcher<T> dispatcher) {
        
        this.eventType = eventType;
        this.characteristics = characteristics;
        this.dispatcher = dispatcher;
        this.lock = new ReentrantReadWriteLock();
    }
    
    public static <T> EventBus<T> of(final TypeToken<T> eventType, final EventBusCharacteristic... characteristics) {
        
        Objects.requireNonNull(eventType, "eventType");
        Objects.requireNonNull(characteristics, "characteristics");
        final Set<EventBusCharacteristic> characteristicSet = verify(eventType, characteristics);
        final BusDispatcher<T> dispatcher = findDispatcher(characteristicSet);
        return new EventBus<>(eventType, characteristicSet, dispatcher);
    }
    
    private static <T> Set<EventBusCharacteristic> verify(final TypeToken<T> type, final EventBusCharacteristic... characteristics) {
        
        final Set<EventBusCharacteristic> characteristicSet = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(characteristics)));
        
        if(characteristicSet.contains(EventBusCharacteristic.SUPPORTS_CANCELLATION) && !type.isSubtypeOf(ICancelableEvent.class)) {
            throw new IllegalArgumentException("Unable to support cancellation for an event that is not cancelable");
        }
        
        return characteristicSet;
    }
    
    private static <T> BusDispatcher<T> findDispatcher(final Set<EventBusCharacteristic> characteristics) {
        
        final boolean cancellation = characteristics.contains(EventBusCharacteristic.SUPPORTS_CANCELLATION);
        return new ArrayBackedDispatcher<>(cancellation);
    }
    
    public HandlerToken<T> registerHandler(final Consumer<T> handler) {
        
        return this.registerHandler(Phase.NORMAL, handler);
    }
    
    public HandlerToken<T> registerHandler(final Phase phase, final Consumer<T> handler) {
        
        return this.registerHandler(phase, false, handler);
    }
    
    public HandlerToken<T> registerHandler(final boolean listenToCanceled, final Consumer<T> handler) {
        
        return this.registerHandler(Phase.NORMAL, listenToCanceled, handler);
    }
    
    public HandlerToken<T> registerHandler(final Phase phase, final boolean listenToCanceled, final Consumer<T> handler) {
        
        Objects.requireNonNull(phase, "phase");
        Objects.requireNonNull(handler, "handler");
        
        if(listenToCanceled && !this.isCancelable()) {
            throw new IllegalArgumentException("Unable to listen to canceled events for an event bus that does not support cancellation");
        }
        
        final Phase flattened = this.flattensPhases() ? Phase.NORMAL : phase;
        
        final Lock writeLock = this.lock.writeLock();
        writeLock.lock();
        try {
            return this.dispatcher.register(listenToCanceled, flattened, handler);
        } finally {
            writeLock.unlock();
        }
    }
    
    public void unregisterHandler(final HandlerToken<T> token) {
        
        Objects.requireNonNull(token, "token");
        
        final Lock writeLock = this.lock.writeLock();
        writeLock.lock();
        try {
            this.dispatcher.unregister(token);
        } finally {
            writeLock.unlock();
        }
    }
    
    public T post(final T event) {
        return this.postCatching(event, e -> { throw e; });
    }
    
    public T postCatching(final T event, final Consumer<BusHandlingException> exceptionHandler) {
        
        try {
            // TODO("Evaluate performance")
            final Lock readLock = this.lock.readLock();
            readLock.lock();
            try {
                return this.dispatcher.dispatch(event);
            } finally {
                readLock.unlock();
            }
        } catch(final BusHandlingException e) {
            exceptionHandler.accept(e);
            return event;
        }
    }
    
    private boolean flattensPhases() {
        
        return this.characteristics.contains(EventBusCharacteristic.IGNORE_PHASES);
    }
    
    private boolean isCancelable() {
        
        return this.characteristics.contains(EventBusCharacteristic.SUPPORTS_CANCELLATION);
    }
    
    @Override
    public String toString() {
        
        return "EventBus[" + this.eventType.getType() + '/' + this.characteristics + ']';
    }
    
}
