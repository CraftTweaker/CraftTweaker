package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.IEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.Phase;
import com.google.common.reflect.TypeToken;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Represents a bus on which events of a specific type can be posted.
 *
 * <p>An event bus provides an easy way for a series of {@link Consumer}s, usually called <em>listener</em>s or
 * <em>handler</em>s to subscribe to certain events occurring within the program and be notified of their
 * occurrence.</p>
 *
 * <p>There are no restriction on the type of the event object, nor on the amount of buses that might exist for a single
 * class. It is in fact possible, although discouraged, for a single event class to be mapped to more than one bus.
 * Particular users of this class, such as the ZenCode scripting integration, might assume that every event class has a
 * single bus associated to it.</p>
 *
 * <p>An event bus might be of two types: either a <em>direct</em> or a <em>cancelable</em> event bus. A direct event
 * bus acts as a simple notifier, invoking the various handlers. A cancelable event bus allows the consumer to also
 * <em>cancel</em> a specific event, blocking processing of the event and letting the <em>dispatcher</em>, i.e. the user
 * that posted the event, know that one handler wants to prevent the action from happening, if possible. Some handlers
 * might still want to listen to canceled events, though, so such an option is also provided.</p>
 *
 * <p>Every event bus has a {@linkplain IEventBusWire wire} associated to it, that allows for the wiring of this event
 * bus to another event bus that might be present, such as platform-specific buses. It is also possible to create
 * <em>wireless</em> event buses, meaning buses that are standalone, by using the {@link CommonWirelessEventBusWire}
 * class as the wire.</p>
 *
 * <p>Every handler on an event bus might also be associated to a {@link Phase}. Refer to the class documentation for
 * additional specifications.</p>
 *
 * <p>While possible, it is discouraged to create custom sub-classes of this interface. Rather, clients of this API
 * should prefer using one of the factory methods provided in this class to construct the desired event bus.</p>
 *
 * <p>It is customary, although not required, for an event bus to be located within the event class itself, in a
 * {@code public static final} field:</p>
 *
 * <pre>
 *     {@code
 *     class MyEvent {
 *         public static final IEventBus<MyEvent> BUS = IEventBus.direct(MyEvent.class, CommonWirelessEventBusWire.of());
 *     }
 *     }
 * </pre>
 *
 * @param <T> The type of event to be posted on the event bus.
 *
 * @since 11.0.0
 */
public interface IEventBus<T> {
    
    /**
     * Constructs a new <em>direct</em> {@link IEventBus} for the given event type.
     *
     * <p>A direct event bus does not support cancellation.</p>
     *
     * @param clazz The type of the event.
     * @param wire The {@link IEventBusWire} to use to wire this event bus.
     * @return A newly created {@link IEventBus}.
     * @param <T> The type of the event to post in the bus.
     *
     * @since 11.0.0
     */
    static <T> IEventBus<T> direct(final Class<T> clazz, final IEventBusWire wire) {
        
        Objects.requireNonNull(clazz, "class");
        Objects.requireNonNull(wire, "wire");
        return direct(TypeToken.of(clazz), wire);
    }
    
    /**
     * Constructs a new <em>direct</em> {@link IEventBus} for the given event type.
     *
     * <p>A direct event bus does not support cancellation.</p>
     *
     * @param token The type of the event.
     * @param wire The {@link IEventBusWire} to use to wire this event bus.
     * @return A newly created {@link IEventBus}.
     * @param <T> The type of the event to post in the bus.
     *
     * @since 11.0.0
     */
    static <T> IEventBus<T> direct(final TypeToken<T> token, final IEventBusWire wire) {
        
        Objects.requireNonNull(token, "token");
        Objects.requireNonNull(wire, "wire");
        return DirectEventBus.of(token, wire);
    }
    
    /**
     * Constructs a new <em>cancelable</em> {@link IEventBus} for the given event type.
     *
     * <p>A cancelable event bus supports cancellation of events, the state of which is tracked through the given
     * {@link IEventCancellationCarrier}.</p>
     *
     * @param clazz The type of the event.
     * @param wire The {@link IEventBusWire} to use to wire this event bus.
     * @param carrier The {@link IEventCancellationCarrier} responsible to track the cancellation status of a particular
     *                event.
     * @return A newly created {@link IEventBus}
     * @param <T> The type of the event to post in the bus.
     *
     * @since 11.0.0
     */
    static <T> IEventBus<T> cancelable(final Class<T> clazz, final IEventBusWire wire, final IEventCancellationCarrier<T> carrier) {
        
        Objects.requireNonNull(clazz, "class");
        Objects.requireNonNull(wire, "wire");
        Objects.requireNonNull(carrier, "carrier");
        return cancelable(TypeToken.of(clazz), wire, carrier);
    }
    
    /**
     * Constructs a new <em>cancelable</em> {@link IEventBus} for the given event type.
     *
     * <p>A cancelable event bus supports cancellation of events, the state of which is tracked through the given
     * {@link IEventCancellationCarrier}.</p>
     *
     * @param token The type of the event.
     * @param wire The {@link IEventBusWire} to use to wire this event bus.
     * @param carrier The {@link IEventCancellationCarrier} responsible to track the cancellation status of a particular
     *                event.
     * @return A newly created {@link IEventBus}
     * @param <T> The type of the event to post in the bus.
     *
     * @since 11.0.0
     */
    static <T> IEventBus<T> cancelable(final TypeToken<T> token, final IEventBusWire wire, final IEventCancellationCarrier<T> carrier) {
        
        Objects.requireNonNull(token, "token");
        Objects.requireNonNull(wire, "wire");
        Objects.requireNonNull(carrier, "carrier");
        return CancelingEventBus.of(token, wire, carrier);
    }
    
    /**
     * Registers the given {@link Consumer} as a <em>handler</em> for the event this bus is for.
     *
     * <p>The consumer will be invoked in the event's {@linkplain Phase default phase} and, if this event bus is
     * cancelable, solely if the event is not canceled.</p>
     *
     * @param handler The handler to be invoked.
     * @return A {@link IHandlerToken} that allows for the removal of the given handler.
     *
     * @since 11.0.0
     */
    IHandlerToken<T> registerHandler(final Consumer<T> handler);
    
    /**
     * Registers the given {@link Consumer} as a <em>handler</em> for the event this bus is for on the given
     * {@link Phase}.
     *
     * <p>If the event bus is cancelable, then the handler will be invoked solely if the event is not canceled.</p>
     *
     * @param phase The {@link Phase} on which the handler should be invoked on.
     * @param handler The handler to be invoked.
     * @return A {@link IHandlerToken} that allows for the removal of the given handler.
     *
     * @since 11.0.0
     */
    IHandlerToken<T> registerHandler(final Phase phase, final Consumer<T> handler);
    
    /**
     * Registers the given {@link Consumer} as a <em>handler</em> for the event this bus is for.
     *
     * <p>If this event bus is cancelable, then the {@code listenToCanceled} {@code boolean} will determine whether the
     * handler will be invoked solely when the event is not canceled ({@code listenToCanceled = false}) or in all cases,
     * effectively ignoring cancellation status ({@code listenToCanceled = true}). If the event bus is <em>direct</em>,
     * on the other hand, the value of {@code listenToCanceled} is ignored.</p>
     *
     * @param listenToCanceled Whether the handler should be invoked for all events or only non-canceled ones.
     * @param handler The handler to be invoked.
     * @return A {@link IHandlerToken} that allows for the removal of the given handler.
     *
     * @since 11.0.0
     */
    IHandlerToken<T> registerHandler(final boolean listenToCanceled, final Consumer<T> handler);
    
    /**
     * Registers the given {@link Consumer} as a <em>handler</em> for the event this bus is for on the given
     * {@link Phase}.
     *
     * <p>If this event bus is cancelable, then the {@code listenToCanceled} {@code boolean} will determine whether the
     * handler will be invoked solely when the event is not canceled ({@code listenToCanceled = false}) or in all cases,
     * effectively ignoring cancellation status ({@code listenToCanceled = true}). If the event bus is <em>direct</em>,
     * on the other hand, the value of {@code listenToCanceled} is ignored.</p>
     *
     * @param phase The {@link Phase} on which the handler should be invoked on.
     * @param listenToCanceled Whether the handler should be invoked for all events or only non-canceled ones.
     * @param handler The handler to be invoked.
     * @return A {@link IHandlerToken} that allows for removal of the given handler.
     *
     * @since 11.0.0
     */
    IHandlerToken<T> registerHandler(final Phase phase, final boolean listenToCanceled, final Consumer<T> handler);
    
    /**
     * Removes the handler associated to the given {@link IHandlerToken} from the ones associated to this bus.
     *
     * <p>Removing a handler means that the event bus will no longer invoke it when an event is posted.</p>
     *
     * <p>It is not allowed to attempt removal of a given handler more than once.</p>
     *
     * @param token The token associated to the handler that should be removed.
     *
     * @throws IllegalArgumentException If the token is not recognized by the event bus, possibly because it is of the
     * wrong class or because it has been created by a different event bus.
     * @throws IllegalStateException If an attempt to reuse the token after it has already been used once is detected.
     *
     * @since 11.0.0
     */
    void unregisterHandler(final IHandlerToken<T> token);
    
    /**
     * Posts the given event to all handlers listening on the specified {@link Phase}.
     *
     * <p>Only the handlers listening to the given phase will be notified, no other handlers will. Moreover, any
     * {@linkplain BusHandlingException dispatching exceptions} that might occur during the posting of the event will be
     * automatically handled according to an internal policy specified by the event bus itself. An event bus
     * implementation need not raise the exception. If a custom policy is desired, then use
     * {@link #postCatching(Phase, Object, Consumer)} instead.</p>
     *
     * @param phase The {@link Phase} on which the event should be posted.
     * @param event The event object that should be posted to the various listeners.
     * @return {@code event}, for easier chaining.
     *
     * @since 11.0.0
     */
    T post(final Phase phase, final T event);
    
    /**
     * Posts the given event to all handlers listening on the specified {@link Phase}.
     *
     * <p>Only the handlers listening to the given phase will be notified, no other handlers will.</p>
     *
     * <p>Any {@link BusHandlingException} that might be raised during dispatching will be caught and provided to the
     * specified {@code exceptionHandler} {@link Consumer} for additional processing.</p>
     *
     * @param phase The {@link Phase} on which the event should be posted.
     * @param event The event object that should be posted to the various listeners.
     * @param exceptionHandler The {@link Consumer} responsible for the handling of dispatch errors, if any.
     * @return {@code event}, for easier chaining.
     *
     * @since 11.0.0
     */
    T postCatching(final Phase phase, final T event, final Consumer<BusHandlingException> exceptionHandler);
    
    /**
     * Gets the {@link TypeToken} representing the type of events that this bus dispatches.
     *
     * @return The type of events dispatched by this bus.
     *
     * @since 11.0.0
     */
    TypeToken<T> eventType();
    
}
