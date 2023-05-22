package com.blamejared.crafttweaker.api.event;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class FabricEventCancellationCarrier<E> implements IEventCancellationCarrier<E> {
    private final Predicate<E> isCanceled;
    private final Consumer<E> cancel;
    
    private FabricEventCancellationCarrier(final Predicate<E> isCanceled, final Consumer<E> cancel) {
        this.isCanceled = isCanceled;
        this.cancel = cancel;
    }
    
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
