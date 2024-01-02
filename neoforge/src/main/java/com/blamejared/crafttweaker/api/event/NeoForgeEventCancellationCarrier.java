package com.blamejared.crafttweaker.api.event;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.base.Suppliers;
import net.neoforged.bus.api.ICancellableEvent;

import java.util.function.Supplier;

/**
 * Represents the cancellation carrier for all NeoForge events.
 *
 * <p>This carrier relies on the {@link ICancellableEvent} interface to verify the cancellation status of a particular event.</p>
 *
 * @param <T> The type of the event.
 *
 * @since 15.0.0
 */
public final class NeoForgeEventCancellationCarrier<T extends ICancellableEvent> implements IEventCancellationCarrier<T> {
    private static final Supplier<NeoForgeEventCancellationCarrier<?>> INSTANCE = Suppliers.memoize(NeoForgeEventCancellationCarrier::new);
    
    private NeoForgeEventCancellationCarrier() {}
    
    public static <T> IEventCancellationCarrier<T> of() {
        return GenericUtil.uncheck(INSTANCE.get());
    }
    
    @Override
    public boolean isCanceled(final T event) {
        
        return event.isCanceled();
    }
    
    @Override
    public void cancel(final T event) {
        
        event.setCanceled(true);
    }
    
}
