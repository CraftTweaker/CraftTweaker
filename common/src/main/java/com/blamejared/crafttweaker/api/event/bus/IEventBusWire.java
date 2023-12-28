package com.blamejared.crafttweaker.api.event.bus;

import com.google.common.reflect.TypeToken;

/**
 * Manages the wiring of an {@link IEventBus} in order to construct a <em>wired</em> event bus.
 *
 * <p>The wire is essentially responsible for the association between an event bus and a platform-specific counterpart
 * that is responsible for firing events on specific platforms. It acts as a bridge between the platform-specific event
 * bus, if present, and the internal bus used by this implementation.</p>
 *
 * <p>Custom implementations of this interface are allowed, although discouraged: refer to the already available
 * implementations if possible, as a matter of cleanliness of code and implementations.</p>
 *
 * <p>It is allowed to reuse the same wire for multiple events, provided that they can be wired the same way.</p>
 *
 * <p>A <em>wireless</em> event bus can be used by using the special {@link CommonWirelessEventBusWire} wire.</p>
 *
 * @since 11.0.0
 */
public interface IEventBusWire {
    
    /**
     * Performs the necessary work to wire the given {@link IEventBus} to its platform-specific counterpart.
     *
     * @param eventType The type of the events fired in the bus; this corresponds to invoking
     *                  {@link IEventBus#eventType()} on {@code bus}.
     * @param bus The {@link IEventBus} that should be wired.
     * @param <T> The type of the events fired in the bus.
     * @throws IllegalStateException If the given event type does not match what the wire expects. Note, however, that
     * implementations should strive against throwing this kind of exception, rather attempting to work with as many
     * implementations as possible.
     *
     * @since 11.0.0
     */
    <T> void registerBusForDispatch(final TypeToken<T> eventType, final IEventBus<T> bus);
    
}
