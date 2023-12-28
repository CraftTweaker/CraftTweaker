package com.blamejared.crafttweaker.api.event.bus;

import com.google.common.reflect.TypeToken;

/**
 * Wire that allows the creation of a wireless {@link IEventBus}.
 *
 * <p>An event bus is defined as <em>wireless</em> if it does not require any additional wiring in order to be
 * operative, allowing it to behave fully standalone. This is usually the case for buses for common events, as they do
 * not have to latch onto a pre-existing event bus.</p>
 *
 * @since 11.0.0
 */
public final class CommonWirelessEventBusWire implements IEventBusWire {
    
    private static final CommonWirelessEventBusWire INSTANCE = new CommonWirelessEventBusWire();
    
    private CommonWirelessEventBusWire() {}
    
    /**
     * Obtains an instance of a {@link CommonWirelessEventBusWire}.
     *
     * @return An event wire instance.
     *
     * @since 11.0.0
     */
    public static IEventBusWire of() {
        return INSTANCE;
    }
    
    @Override
    public <T> void registerBusForDispatch(final TypeToken<T> eventType, final IEventBus<T> bus) {}
    
}
