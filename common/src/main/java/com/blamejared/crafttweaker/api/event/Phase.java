package com.blamejared.crafttweaker.api.event;

/**
 * Represents the phase at which a specific event is being posted.
 *
 * <p>The exact meaning of {@link Phase} differs between implementations, but it is usually used to provide some sort
 * of time frame for the invocation of the various event listeners. Generally, event listeners will be invoked
 * from lowest phase to higher phase (i.e. {@link Phase#EARLIEST}, {@link Phase#NORMAL}, {@link Phase#LATEST}), but some
 * particular implementations might choose a different order if it makes sense to do so.</p>
 *
 * <p>The <em>default phase</em> of an event is always mapped to {@link Phase#NORMAL} by contract, though, allowing for
 * general handlers to always behave correctly.</p>
 *
 * @since 11.0.0
 */
public enum Phase {
    /**
     * Represents the earliest phase of event dispatching.
     *
     * @since 11.0.0
     */
    EARLIEST,
    /**
     * Represents the normal phase of event dispatching, also known as the <em>default phase</em>.
     *
     * @since 11.0.0
     */
    NORMAL,
    /**
     * Represents the latest phase of event dispatching.
     *
     * @since 11.0.0
     */
    LATEST
}
