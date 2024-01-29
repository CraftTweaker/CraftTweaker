package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.event.Phase;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.reflect.TypeToken;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

abstract class PhasedEventBus<T> implements IEventBus<T> {
    
    private record PhasedHandlerToken<T>(IHandlerToken<T> delegate, Phase phase, IEventBus<T> bus) implements IHandlerToken<T> {}
    
    private static final Logger LOGGER = CraftTweakerAPI.getLogger("Event");
    private static final Phase[] PHASES = Phase.values();
    
    private final TypeToken<T> eventType;
    private final IEventBusWire wire;
    private final ArrayBackedDispatcher<T>[] phasedDispatchers;
    
    protected PhasedEventBus(final TypeToken<T> eventType, final IEventBusWire wire) {
        this.eventType = Objects.requireNonNull(eventType, "eventType");
        this.wire = Objects.requireNonNull(wire, "wire");
        this.phasedDispatchers = GenericUtil.uncheck(new ArrayBackedDispatcher<?>[PHASES.length]);
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
            return new PhasedHandlerToken<>(token, phase, this);
        });
    }
    
    @Override
    public final void unregisterHandler(final IHandlerToken<T> token) {
        
        if (!(token instanceof PhasedEventBus.PhasedHandlerToken<T> phasedToken)) {
            throw new IllegalArgumentException("Unknown token");
        }
        
        if (phasedToken.bus() != this) {
            throw new IllegalArgumentException("Cross-bus token referencing is disallowed");
        }
        
        this.modifyHandlers(() -> {
            this.dispatcherAt(phasedToken.phase()).unregister(phasedToken.delegate());
            return null;
        });
    }
    
    @Override
    public T post(final T event) {
        
        return this.postCatching(event, this::onException);
    }
    
    @Override
    public final T post(final Phase phase, final T event) {
        
        return this.postCatching(phase, event, this::onException);
    }
    
    @Override
    public T postCatching(final T event, final Consumer<BusHandlingException> exceptionHandler) {
        
        return this.dispatch(event, exceptionHandler, this.phasedDispatchers);
    }
    
    @Override
    public final T postCatching(final Phase phase, final T event, final Consumer<BusHandlingException> exceptionHandler) {
        
        return this.dispatch(event, exceptionHandler, this.phasedDispatchers[phase.ordinal()]);
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
    
    private <R> R dispatch(final R event, final Consumer<BusHandlingException> exceptionHandler, final ArrayBackedDispatcher<R> dispatcher) {
        return this.queryHandlers(event, exceptionHandler, it -> {
            if (dispatcher != null) {
                dispatcher.dispatch(it);
            }
        });
    }
    
    @SafeVarargs
    private <R> R dispatch(final R event, final Consumer<BusHandlingException> exceptionHandler, final ArrayBackedDispatcher<R>... dispatchers) {
        return this.queryHandlers(event, exceptionHandler, it -> {
            for (final ArrayBackedDispatcher<R> dispatcher : dispatchers) {
                if (dispatcher != null) {
                    dispatcher.dispatch(it);
                }
            }
        });
    }
    
    private <R> R modifyHandlers(final Supplier<R> block) {
        return block.get();
    }
    
    private <R> R queryHandlers(final R event, final Consumer<BusHandlingException> exceptionHandler, final Consumer<R> block) {
        try {
            block.accept(event);
            return event;
        } catch (final BusHandlingException e) {
            exceptionHandler.accept(e);
            return event;
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
    
    private void onException(final BusHandlingException e) {
        LOGGER.error(
                () -> "An error occurred while attempting to dispatch an event of type " + this.eventType,
                e.original()
        );
    }
    
}
