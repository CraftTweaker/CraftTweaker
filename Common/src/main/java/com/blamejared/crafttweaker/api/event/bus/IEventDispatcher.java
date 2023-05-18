package com.blamejared.crafttweaker.api.event.bus;

@FunctionalInterface
interface IEventDispatcher<T> {
    void dispatch(final T event);
}
