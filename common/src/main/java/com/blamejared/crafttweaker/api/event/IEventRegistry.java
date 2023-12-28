package com.blamejared.crafttweaker.api.event;

import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.google.common.reflect.TypeToken;

/**
 * Holds all information pertaining to events and {@linkplain IEventBus event buses}.
 *
 * <p>An instance of this class can be obtained through
 * {@link com.blamejared.crafttweaker.api.ICraftTweakerRegistry}.</p>
 *
 * @since 11.0.0
 */
public interface IEventRegistry {
    
    /**
     * Obtains the {@link IEventBus} associated to the given event type, represented as a {@link TypeToken}.
     *
     * <p>Usage of this API is <strong>discouraged</strong> as the lookup is slower than accessing the bus for the event
     * directly through Java code. This entry point is provided solely for automatic discovery for the ZenCode script
     * interface.</p>
     *
     * @param eventType The type of the event whose bus should be looked up.
     * @return The {@link IEventBus} associated to it.
     * @param <T> The type of the event.
     * @throws IllegalArgumentException If the given event type does not exist.
     *
     * @since 11.0.0
     */
    <T> IEventBus<T> busOf(final TypeToken<T> eventType);
    
    /**
     * Obtains the {@link IEventBus} associated to the given event type, represented as a {@link Class}.
     *
     * <p>Usage of this API is <strong>discouraged</strong> as the lookup is slower than accessing the bus for the event
     * directly through Java code. This entry point is provided solely for automatic discovery for the ZenCode script
     * interface.</p>
     *
     * @param clazz The type of the event whose bus should be looked up.
     * @return The {@link IEventBus} associated to it.
     * @param <T> The type of the event.
     * @throws IllegalArgumentException If the given event type does not exist.
     *
     * @since 11.0.0
     */
    default <T> IEventBus<T> busOf(final Class<T> clazz) {
        return this.busOf(TypeToken.of(clazz));
    }
    
}
