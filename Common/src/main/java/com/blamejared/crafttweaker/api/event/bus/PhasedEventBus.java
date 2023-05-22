package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.Phase;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.reflect.TypeToken;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Supplier;

abstract class PhasedEventBus<T> implements IEventBus<T> {
    
    private record PhasedHandlerToken<T>(IHandlerToken<T> delegate, Phase phase) implements IHandlerToken<T> {}
    
    private static final Phase[] PHASES = Phase.values();
    private static final Consumer<BusHandlingException> DEFAULT_HANDLER = PhasedEventBus::onException;
    
    private final TypeToken<T> eventType;
    private final IEventBusWire wire;
    private final ArrayBackedDispatcher<T>[] phasedDispatchers;
    private final ReadWriteLock lock;
    
    protected PhasedEventBus(final TypeToken<T> eventType, final IEventBusWire wire) {
        this.eventType = Objects.requireNonNull(eventType, "eventType");
        this.wire = Objects.requireNonNull(wire, "wire");
        this.phasedDispatchers = GenericUtil.uncheck(new ArrayBackedDispatcher<?>[PHASES.length]);
        this.lock = new ReentrantReadWriteLock();
    }
    
    private static void onException(final BusHandlingException e) {
        // TODO("")
        throw e;
    }
    
    @Override
    public final IHandlerToken<T> registerHandler(final Consumer<T> handler) {
        
        return this.registerHandler(Phase.NORMAL, handler);
    }
    
    @Override
    public final IHandlerToken<T> registerHandler(final Phase phase, final Consumer<T> handler) {
        
        return this.registerHandler(phase, false, handler);
    }
    
    @Override
    public final IHandlerToken<T> registerHandler(final boolean listenToCanceled, final Consumer<T> handler) {
        
        return this.registerHandler(Phase.NORMAL, listenToCanceled, handler);
    }
    
    @Override
    public final IHandlerToken<T> registerHandler(final Phase phase, final boolean listenToCanceled, final Consumer<T> handler) {
        
        Objects.requireNonNull(phase, "phase");
        Objects.requireNonNull(handler, "handler");
        return this.modifyHandlers(() -> {
            final ArrayBackedDispatcher<T> dispatcher = this.dispatcherAt(phase);
            final IHandlerToken<T> token = this.registerPhased(dispatcher, listenToCanceled, handler);
            return new PhasedHandlerToken<>(token, phase);
        });
    }
    
    @Override
    public final void unregisterHandler(final IHandlerToken<T> token) {
        
        if (!(token instanceof PhasedEventBus.PhasedHandlerToken<T> phasedToken)) {
            throw new IllegalArgumentException("Unknown token");
        }
        
        this.modifyHandlers(() -> {
            this.dispatcherAt(phasedToken.phase()).unregister(phasedToken.delegate());
            return null;
        });
    }
    
    @Override
    public final T post(final Phase phase, final T event) {
        
        return this.postCatching(phase, event, DEFAULT_HANDLER);
    }
    
    @Override
    public final T postCatching(final Phase phase, final T event, final Consumer<BusHandlingException> exceptionHandler) {
        
        try {
            // TODO("Evaluate performance")
            final Lock readLock = this.lock.readLock();
            readLock.lock();
            try {
                for (final ArrayBackedDispatcher<T> dispatcher : this.phasedDispatchers) {
                    if (dispatcher != null) {
                        dispatcher.dispatch(event);
                    }
                }
                return event;
            } finally {
                readLock.unlock();
            }
        } catch(final BusHandlingException e) {
            exceptionHandler.accept(e);
            return event;
        }
    }
    
    @Override
    public final TypeToken<T> eventType() {
        
        return this.eventType;
    }
    
    protected abstract IHandlerToken<T> registerPhased(
            final ArrayBackedDispatcher<T> phasedDispatcher,
            final boolean listenToCanceled,
            final Consumer<T> handler
    );
    
    protected final void wire() {
        this.wire.registerBusForDispatch(this.eventType(), this);
    }
    
    private <R> R modifyHandlers(final Supplier<R> block) {
        // TODO("Evaluate performance")
        final Lock writeLock = this.lock.writeLock();
        writeLock.lock();
        try {
            return block.get();
        } finally {
            writeLock.unlock();
        }
    }
    
    private ArrayBackedDispatcher<T> dispatcherAt(final Phase phase) {
        final int phaseIndex = phase.ordinal();
        final ArrayBackedDispatcher<T> dispatcher = this.phasedDispatchers[phaseIndex];
        if (dispatcher != null) {
            return dispatcher;
        }
        final ArrayBackedDispatcher<T> newDispatcher = new ArrayBackedDispatcher<>();
        this.phasedDispatchers[phaseIndex] = newDispatcher;
        return newDispatcher;
    }
    
}
