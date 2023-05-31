package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.Phase;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.reflect.TypeToken;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Wire that provides a way to adapt a platform-specific event to a common event, allowing it to fire on both
 * platform-specific {@linkplain IEventBus buses} and common buses as needed.
 *
 * <p>In other words, an instance of this class will wire its associated {@link IEventBus} by first cascading to another
 * specified wiring implementation (usually a platform-specific one), and then setting up a series of listeners that
 * allow redirection of events from the targeted bus to another one, specified when constructing the wire.</p>
 *
 * <p>To allow for the double-posting to happen, a converting {@link Function} and a {@link BiConsumer} have to be
 * provided, as further explained in the {@link #of(IEventBusWire, IEventBus, Function)} and
 * {@link #of(IEventBusWire, IEventBus, Function, BiConsumer)} factory methods, refer to those for more information.</p>
 *
 * <p>The general usage pattern of this class sees it being wired accordingly to a scheme similar to the following
 * snippet of code:</p>
 *
 * <pre>
 *     {@code
 *     class MyCommonEvent {
 *         final IEventBus<MyCommonEvent> BUS = makeCommonBus();
 *     }
 *
 *     class MyPlatformEvent {
 *         final IEventBus<MyPlatformEvent> BUS = makeBusWithWire(
 *             CommonAdaptingEventBusWire.of(platformSpecificWire(), MyCommonEvent.BUS, MyCommonEvent::new)
 *         );
 *     }
 *     }
 * </pre>
 *
 * @param <P> The type of the platform-specific event.
 * @param <C> The type of the common event.
 *
 * @since 11.0.0
 */
public final class CommonAdaptingEventBusWire<P, C> implements IEventBusWire {
    private record ListenerRedirector<Q, D>(
            Phase phase,
            IEventBus<D> commonBus,
            PlatformToCommonConverter<Q, D> platformToCommon,
            CommonToPlatformConverter<D, Q> commonToPlatform
    ) implements Consumer<Q> {
        
        @Override
        public void accept(final Q platform) {
            final D common = this.platformToCommon().apply(platform);
            this.commonBus().post(this.phase(), common);
            this.commonToPlatform().accept(common, platform);
        }
        
    }
    
    @FunctionalInterface private interface PlatformToCommonConverter<Q, D> extends Function<Q, D> {}
    @FunctionalInterface private interface CommonToPlatformConverter<D, Q> extends BiConsumer<D, Q> {}
    
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
    
    /**
     * Constructs a new {@link CommonAdaptingEventBusWire} with the given parameters.
     *
     * <p>The newly built wire will first delegate to the given {@code delegate} for wiring, and then set up a listener
     * that will redirect an event of type {@code C} to one of type {@code P} on the given {@code commonBus}. The
     * conversion will happen accordingly to the specified {@code platformToCommon} {@link Function}.</p>
     *
     * <p>Usage of this method means that no further processing is required once the event dispatch is done to move back
     * information from the {@code C} event to the {@code P} event, either because objects are shared between the two or
     * because the events are both immutable. If additional behavior is required, please refer to
     * {@link #of(IEventBusWire, IEventBus, Function, BiConsumer)} instead.</p>
     *
     * @param delegate The {@link IEventBusWire} that will manage the initial wiring of the target event bus.
     * @param commonBus The {@link IEventBus} that will be used to dispatch the event, once converted from its
     *                  platform-specific form to the common one.
     * @param platformToCommon A {@link Function} that, given as argument an instance of the platform-specific event,
     *                         will handle the transformation to a common event instance.
     * @return An {@link IEventBusWire} that will perform the requested operations.
     * @param <C> The type of the common event.
     * @param <P> The type of the platform event.
     *
     * @since 11.0.0
     */
    public static <C, P> IEventBusWire of(
            final IEventBusWire delegate,
            final IEventBus<C> commonBus,
            final Function<P, C> platformToCommon
    ) {
        return of(delegate, commonBus, platformToCommon, GenericUtil.uncheck(NOTHING));
    }
    
    /**
     * Constructs a new {@link CommonAdaptingEventBusWire} with the given parameters.
     *
     * <p>The newly built wire will first delegate to the given {@code delegate} for wiring, and then set up a listener
     * that will redirect an event of type {@code C} to one of type {@code P} on the given {@code commonBus}. The
     * conversion will happen accordingly to the specified {@code platformToCommon} {@link Function}. Once the dispatch
     * has been completed, the {@code commonToPlatform} {@link BiConsumer} will be invoked to allow copying of data from
     * the instance of {@code C} to the one of {@code P}.</p>
     *
     * <p>If no further processing is required, refer to {@link #of(IEventBusWire, IEventBus, Function)} instead.</p>
     *
     * @param delegate The {@link IEventBusWire} that will manage the initial wiring of the target event bus.
     * @param commonBus The {@link IEventBus} that will be used to dispatch the event, once converted from its
     *                  platform-specific form to the common one.
     * @param platformToCommon A {@link Function} that, given as argument an instance of the platform-specific event,
     *                         will handle the transformation to a common event instance.
     * @param commonToPlatform A {@link BiConsumer} that, given as arguments an instance of the common event and one of
     *                         the platform-specific event, will handle copying of data between the first and the second
     *                         as necessary.
     * @return An {@link IEventBusWire} that will perform the requested operations.
     * @param <C> The type of the common event.
     * @param <P> The type of the platform event.
     *
     * @since 11.0.0
     */
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
            final Consumer<?> consumer = new ListenerRedirector<>(phase, this.commonBus, this.platformToCommon, this.commonToPlatform);
            bus.registerHandler(phase, true, GenericUtil.uncheck(consumer));
        }
    }
    
}
