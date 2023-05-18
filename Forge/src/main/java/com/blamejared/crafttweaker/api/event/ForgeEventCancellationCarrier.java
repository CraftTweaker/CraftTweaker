package com.blamejared.crafttweaker.api.event;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.base.Suppliers;
import net.minecraftforge.eventbus.api.Event;

import java.util.function.Supplier;

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
