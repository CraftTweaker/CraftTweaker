package com.blamejared.crafttweaker.api.event.bus;

/**
 * Represents a token associated to a particular {@link IEventBus} and <em>handler</em> that allows for the removal of
 * the handler from the event bus.
 *
 * <p>This interface acts simply as a marker interface, and objects of this type shall be treated by the client as mere
 * tokens. Their actual value should not be inspected nor it has to be meaningful, as solely the event bus that has
 * constructed the instance needs to be able to handle objects of this type.</p>
 *
 * @param <T> The type of the event associated to the token.
 *
 * @since 11.0.0
 */
public interface IHandlerToken<T> {}
