package com.blamejared.crafttweaker.api.event;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.base.Suppliers;
import net.minecraftforge.eventbus.api.Event;

import java.util.function.Supplier;

/**
 * Represents the cancellation carrier for all Forge events.
 *
 * <p>This carrier relies on the {@link Event} interface to verify the cancellation status of a particular event.</p>
 *
 * @param <T> The type of the event.
 *
 * @since 11.0.0
 */
public final class ForgeEventCancellationCarrier<T extends Event> implements IEventCancellationCarrier<T> {
    private static final Supplier<ForgeEventCancellationCarrier<?>> INSTANCE = Suppliers.memoize(ForgeEventCancellationCarrier::new);
    
    private ForgeEventCancellationCarrier() {}
    
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
