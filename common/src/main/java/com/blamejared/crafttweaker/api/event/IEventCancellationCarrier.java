package com.blamejared.crafttweaker.api.event;

/**
 * Determines whether an event of the given type has been canceled or not.
 *
 * <p>The logic for determining whether an event is canceled or not, along with the cancellation itself is left to the
 * implementor of this interface.</p>
 *
 * @param <T> The type of the event.
 *
 * @since 11.0.0
 */
public interface IEventCancellationCarrier<T> {
    
    /**
     * Returns whether the given event has been canceled or not.
     *
     * @param event The event whose cancellation status should be determined.
     * @return Whether the event has been canceled.
     *
     * @since 11.0.0
     */
    boolean isCanceled(final T event);
    
    /**
     * Cancels the given event.
     *
     * <p>The cancellation operation must be idempotent, meaning that attempting to cancel an event that has already
     * been canceled before should result in no operations being carried out.</p>
     *
     * @param event The event to cancel.
     *
     * @since 11.0.0
     */
    void cancel(final T event);
}
