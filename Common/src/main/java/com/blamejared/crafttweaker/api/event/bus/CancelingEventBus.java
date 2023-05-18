package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.IEventCancellationCarrier;
import com.google.common.reflect.TypeToken;

import java.util.Objects;
import java.util.function.Consumer;

final class CancelingEventBus<T> extends PhasedEventBus<T> {
    
    private record CancelingEventDispatcher<T>(Consumer<T> delegate, boolean allowCanceled, IEventCancellationCarrier<T> carrier) implements IEventDispatcher<T> {
        
        @Override
        public void dispatch(final T event) {
            if (!this.carrier().isCanceled(event) || this.allowCanceled()) {
                this.delegate().accept(event);
            }
        }
        
    }
    
    private final IEventCancellationCarrier<T> carrier;
    
    private CancelingEventBus(final TypeToken<T> token, final IEventBusWire wire, final IEventCancellationCarrier<T> carrier) {
        super(token, wire);
        this.carrier = Objects.requireNonNull(carrier, "carrier");
    }
    
    static <T> IEventBus<T> of(final TypeToken<T> token, final IEventBusWire wire, final IEventCancellationCarrier<T> carrier) {
        final CancelingEventBus<T> bus = new CancelingEventBus<>(token, wire, carrier);
        bus.wire();
        return bus;
    }
    
    @Override
    protected IHandlerToken<T> registerPhased(final ArrayBackedDispatcher<T> phasedDispatcher, final boolean listenToCanceled, final Consumer<T> handler) {
        
        final IEventDispatcher<T> dispatcher = new CancelingEventDispatcher<>(handler, listenToCanceled, this.carrier);
        return phasedDispatcher.register(dispatcher);
    }
    
}
