package com.blamejared.crafttweaker.api.event;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Represents a {@link IEventCancellationCarrier} for Fabric events.
 *
 * <p>This carrier relies on a pair of {@link Predicate} and {@link Consumer} to properly carry out the verification and
 * cancellation of the event. Given that Fabric events are represented by functional interfaces, the carrier works on
 * the class that acts as a wrapper for the interface for the purposes of exposing it in a compatible way to the ZenCode
 * scripting interface. The {@code Predicate} receives an instance of the class and is responsible for computation of
 * whether the event has been canceled or not; the {@code Consumer} receives an instance of the class and is responsible
 * for the cancellation of the event itself.</p>
 *
 * @param <E> The type of the event.
 *
 * @since 11.0.0
 */
public final class FabricEventCancellationCarrier<E> implements IEventCancellationCarrier<E> {
    private final Predicate<E> isCanceled;
    private final Consumer<E> cancel;
    
    private FabricEventCancellationCarrier(final Predicate<E> isCanceled, final Consumer<E> cancel) {
        this.isCanceled = isCanceled;
        this.cancel = cancel;
    }
    
    /**
     * Builds a new {@link IEventCancellationCarrier} for Fabric events.
     *
     * <p>Given that Fabric events are represented by functional interfaces, the cancellation carrier operates on the
     * wrapper object.</p>
     *
     * @param isCanceled A {@link Predicate} that is used to determine, given an instance of the event, whether it has
     *                   been canceled or not.
     * @param cancel A {@link Consumer} that cancels the given instance of the event.
     * @return An {@link IEventCancellationCarrier} for Fabric events.
     * @param <E> The type of the event.
     *
     * @since 11.0.0
     */
    public static <E> IEventCancellationCarrier<E> of(final Predicate<E> isCanceled, final Consumer<E> cancel) {
        
        Objects.requireNonNull(isCanceled, "isCanceled");
        Objects.requireNonNull(cancel, "cancel");
        return new FabricEventCancellationCarrier<>(isCanceled, cancel);
    }
    
    @Override
    public boolean isCanceled(final E event) {
        
        return this.isCanceled.test(event);
    }
    
    @Override
    public void cancel(final E event) {
        
        this.cancel.accept(event);
    }
    
}
