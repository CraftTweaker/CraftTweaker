package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.Phase;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.reflect.TypeToken;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class CommonAdaptingEventBusWire<P, C> implements IEventBusWire {
    private interface PlatformToCommonConverter<Q, D> extends Function<Q, D> {}
    private interface CommonToPlatformConverter<D, Q> extends BiConsumer<D, Q> {}
    
    private static final CommonToPlatformConverter<?, ?> NOTHING = (a, b) -> {};
    
    private final IEventBusWire delegate;
    private final IEventBus<C> commonBus;
    private final PlatformToCommonConverter<P, C> platformToCommon;
    private final CommonToPlatformConverter<C, P> commonToPlatform;
    
    private CommonAdaptingEventBusWire(
            final IEventBusWire delegate,
            final IEventBus<C> commonBus,
            final PlatformToCommonConverter<P, C> platformToCommon,
            final CommonToPlatformConverter<C, P> commonToPlatform
    ) {
        this.delegate = delegate;
        this.commonBus = commonBus;
        this.platformToCommon = platformToCommon;
        this.commonToPlatform = commonToPlatform;
    }
    
    public static <C, P> IEventBusWire of(
            final IEventBusWire delegate,
            final IEventBus<C> commonBus,
            final Function<P, C> platformToCommon
    ) {
        return of(delegate, commonBus, platformToCommon, GenericUtil.uncheck(NOTHING));
    }
    
    public static <C, P> IEventBusWire of(
            final IEventBusWire delegate,
            final IEventBus<C> commonBus,
            final Function<P, C> platformToCommon,
            final BiConsumer<C, P> commonToPlatform
    ) {
        
        Objects.requireNonNull(delegate, "delegate");
        Objects.requireNonNull(commonBus, "commonBus");
        Objects.requireNonNull(platformToCommon, "platformToCommon");
        Objects.requireNonNull(commonToPlatform, "commonToPlatform");
        
        return new CommonAdaptingEventBusWire<>(delegate, commonBus, platformToCommon::apply, commonToPlatform::accept);
    }
    
    @Override
    public <T> void registerBusForDispatch(final TypeToken<T> eventType, final IEventBus<T> bus) {
        this.delegate.registerBusForDispatch(eventType, bus);
        this.setUpListenerRedirector(bus);
    }
    
    private <T> void setUpListenerRedirector(final IEventBus<T> bus) {
        for (final Phase phase : Phase.values()) {
            bus.registerHandler(phase, true, event -> this.postCommon(GenericUtil.uncheck(event), phase));
        }
    }
    
    private void postCommon(final P platform, final Phase phase) {
        final C common = this.platformToCommon.apply(platform);
        this.commonBus.post(phase, common);
        this.commonToPlatform.accept(common, platform);
    }
    
}
