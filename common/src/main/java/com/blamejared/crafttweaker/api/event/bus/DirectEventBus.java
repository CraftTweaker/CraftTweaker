package com.blamejared.crafttweaker.api.event.bus;

import com.google.common.reflect.TypeToken;

import java.util.function.Consumer;

final class DirectEventBus<T> extends PhasedEventBus<T> {
    
    private record DirectEventDispatcher<T>(Consumer<T> delegate) implements IEventDispatcher<T> {
        
        @Override
        public void dispatch(final T event) {
            this.delegate().accept(event);
        }
        
    }
    
    private DirectEventBus(final TypeToken<T> eventType, final IEventBusWire wire) {
        
        super(eventType, wire);
    }
    
    static <T> IEventBus<T> of(final TypeToken<T> eventType, final IEventBusWire wire) {
        
        final DirectEventBus<T> bus = new DirectEventBus<>(eventType, wire);
        bus.wire();
        return bus;
    }
    
    @Override
    protected IHandlerToken<T> registerPhased(final ArrayBackedDispatcher<T> phasedDispatcher, final boolean listenToCanceled, final Consumer<T> handler) {
        
        final IEventDispatcher<T> dispatcher = new DirectEventDispatcher<>(handler);
        return phasedDispatcher.register(dispatcher);
    }
    
}
