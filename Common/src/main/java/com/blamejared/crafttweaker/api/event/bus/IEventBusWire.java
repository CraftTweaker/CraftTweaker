package com.blamejared.crafttweaker.api.event.bus;

import com.google.common.reflect.TypeToken;

public interface IEventBusWire {
    
    <T> void registerBusForDispatch(final TypeToken<T> eventType, final IEventBus<T> bus);
    
}
