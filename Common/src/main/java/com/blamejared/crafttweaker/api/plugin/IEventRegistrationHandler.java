package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.google.common.reflect.TypeToken;

/**
 * Manages the registration of additional information for events and {@linkplain IEventBus event buses}.
 *
 * <p>Refer to the {@link IEventBus} documentation for more information.</p>
 *
 * @since 11.0.0
 */
public interface IEventRegistrationHandler {
    
    /**
     * Registers a mapping between an event type and its corresponding bus.
     *
     * <p>This mapping is used by the ZenCode script interface to look-up associations between events and the
     * corresponding buses to perform registration and removal tasks. Not all event buses have to be registered with
     * this method: as a matter of fact CraftTweaker does not require registration of buses. Nevertheless, this step
     * is <em>mandatory</em> if the event bus is supposed to be accessible through the ZenCode scripting interface.</p>
     *
     * @param event The {@link TypeToken} representing the event for which this mapping is for.
     * @param bus The {@link IEventBus} associated to this event.
     * @param <T> The type of the event.
     * @throws IllegalArgumentException If the same event type has had a bus already associated to it.
     *
     * @since 11.0.0
     */
    <T> void registerEventMapping(final TypeToken<T> event, final IEventBus<T> bus);
    
}
