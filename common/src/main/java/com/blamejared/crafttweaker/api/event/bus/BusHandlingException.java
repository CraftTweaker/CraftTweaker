package com.blamejared.crafttweaker.api.event.bus;

/**
 * Wraps an exception that has been thrown by an {@link IEventBus} while trying to dispatch an event to various
 * listeners.
 *
 * <p>All {@link Throwable}s thrown during dispatching are wrapped by this class, with the original exception able to
 * be accessed via {@link #original()}. Note that the original exception need not match the
 * {@linkplain #getCause() cause}, as the latter correctly records additional handling exceptions that nested event
 * handlers might throw.</p>
 *
 * @since 11.0.0
 */
public final class BusHandlingException extends RuntimeException {
    
    private final Throwable original;
    
    BusHandlingException(final Throwable t) {
        
        super("An error occurred while trying to dispatch an event onto the bus", t);
        this.original = t instanceof BusHandlingException e? e.original() : t;
    }
    
    /**
     * Obtains the original exception that caused the handling exception to be thrown initially.
     *
     * <p>This exception need not match the {@linkplain #getCause() cause} of the exception.</p>
     *
     * @return The original exception.
     *
     * @since 11.0.0
     */
    public Throwable original() {
        
        return this.original;
    }
    
}
