package com.blamejared.crafttweaker.impl.event;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.event.ActionRegisterEvent;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.IHandlerToken;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.reflect.TypeToken;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Manages the registration and handling of custom handlers for the various events of the game.
 *
 * <p>You can register event handlers for pretty much everything, but make sure that the class you are referencing is
 * documented as an event, otherwise you're in for a nasty surprise.</p>
 *
 * @since 11.0.0
 *
 * @docParam this events
 */
@ZenRegister
@Document("vanilla/api/event/EventManager")
@ZenCodeType.Name("crafttweaker.api.events.EventManager")
public final class ZenEventManager {
    
    /**
     * Represents the global entry point for all event handling.
     *
     * @since 11.0.0
     */
    @ZenCodeGlobals.Global("events")
    public static final ZenEventManager EVENTS = new ZenEventManager();
    
    private ZenEventManager() {}
    
    /**
     * Registers a new event listener.
     *
     * @param typeOfT The reified type of the event.
     * @param consumer The event handler.
     * @param <T> The type of the event.
     *
     * @since 11.0.0
     */
    @ZenCodeType.Method
    public <T> void register(final Class<T> typeOfT, final Consumer<T> consumer) {
        this.registerOnBus(typeOfT, consumer, IEventBus::registerHandler);
    }
    
    /**
     * Registers a new event listener for the specified {@link ZenEventPhase}.
     *
     * @param typeOfT The reified type of the event.
     * @param phase The phase on which the event should be registered.
     * @param consumer The event handler.
     * @param <T> The type of the event.
     *
     * @since 11.0.0
     */
    @ZenCodeType.Method
    public <T> void register(final Class<T> typeOfT, final ZenEventPhase phase, final Consumer<T> consumer) {
        this.registerOnBus(typeOfT, consumer, (bus, event) -> bus.registerHandler(phase.phase(), event));
    }
    
    /**
     * Registers a new event listener that can listen to cancelled events.
     *
     * @param typeOfT The reified type of the event.
     * @param listenToCanceled Whether cancelled events should also be listened to or not.
     * @param consumer The event handler.
     * @param <T> The type of the event.
     *
     * @since 11.0.0
     */
    @ZenCodeType.Method
    public <T> void register(final Class<T> typeOfT, final boolean listenToCanceled, final Consumer<T> consumer) {
        this.registerOnBus(typeOfT, consumer, (bus, event) -> bus.registerHandler(listenToCanceled, event));
    }
    
    /**
     * Registers a new event listener for the specified {@link ZenEventPhase} that can listen to cancelled events.
     *
     * @param typeOfT The reified type of the event.
     * @param phase The phase on which the event should be registered.
     * @param listenToCanceled Whether cancelled events should also be listened to or not.
     * @param consumer The event handler.
     * @param <T> The type of the event.
     *
     * @since 11.0.0
     */
    @ZenCodeType.Method
    public <T> void register(final Class<T> typeOfT, final ZenEventPhase phase, final boolean listenToCanceled, final Consumer<T> consumer) {
        this.registerOnBus(typeOfT, consumer, (bus, event) -> bus.registerHandler(phase.phase(), listenToCanceled, event));
    }
    
    private <T> void registerOnBus(
            final Class<T> typeOfT,
            final Consumer<T> consumer,
            final BiFunction<IEventBus<T>, Consumer<T>, IHandlerToken<T>> regFun
    ) {
        
        CraftTweakerAPI.apply(ActionRegisterEvent.of(TypeToken.of(typeOfT), consumer, regFun));
    }
    
}
