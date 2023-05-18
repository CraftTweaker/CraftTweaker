package com.blamejared.crafttweaker.api.event;

public interface IEventCancellationCarrier<T> {
    boolean isCanceled(final T event);
    void cancel(final T event);
}
