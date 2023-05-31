package com.blamejared.crafttweaker.api.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as being an event accessible to ZenCode scripts, allowing for automatic discovery.
 *
 * <p>Every class annotated with this annotation will be automatically discovered by CraftTweaker and managed
 * accordingly. Namely, CraftTweaker will automatically manage registration of event-bus pairs during the
 * {@link com.blamejared.crafttweaker.api.plugin.IEventRegistrationHandler} phase of plugin loading.</p>
 *
 * <p>A class annotated by this annotation <strong>must</strong> contain at least one {@code public static final} field
 * of type {@link com.blamejared.crafttweaker.api.event.bus.IEventBus} annotated with the {@link ZenEvent.Bus}
 * annotation. All fields that respect these characteristics will be discovered and automatically registered as known
 * event buses by CraftTweaker. As an example, the following snippet of code shows the conventional way of registering
 * an event bus:</p>
 *
 * <pre>
 *     {@code
 *      @ZenCodeType.Name("mods.mymod.event.MyEvent")
 *      @ZenEvent
 *      @ZenRegister
 *      public class MyEvent {
 *          @ZenEvent.Bus public static final IEventBus<MyEvent> BUS = makeEventBus();
 *      }
 *     }
 * </pre>
 *
 * <p>Note that usage of this annotation enables automatic discovery of the association between event and event bus. In
 * all other cases the type of event carried by the bus must be specified manually: refer to the {@link ZenEvent.Bus}
 * documentation for more information.</p>
 *
 * <p>It is generally customary to use this annotation solely for classes that represent actual events and that contain
 * a <em>single</em> bus. In all other cases, it is preferred to use the {@link ZenEvent.BusCarrier} annotation. Note
 * that the single requirement might be strengthened in the future, so prefer using the {@code ZenEvent.BusCarrier}
 * annotation for any class containing two or more event buses.</p>
 *
 * @since 11.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZenEvent {
    
    /**
     * Marks a class as carrying a series of {@link com.blamejared.crafttweaker.api.event.bus.IEventBus}es that should
     * be made available to ZenCode scripts, allowing for automatic discovery.
     *
     * <p>Every class annotated with this annotation will be automatically discovered by CraftTweaker and managed
     * accordingly. Namely, CraftTweaker will automatically manage registration of event buses and events during the
     * {@link com.blamejared.crafttweaker.api.plugin.IEventRegistrationHandler} phase of plugin loading.</p>
     *
     * <p>A class annotated by this annotation <strong>must</strong> contain at least one {@code public static final}
     * field of type {@link com.blamejared.crafttweaker.api.event.bus.IEventBus} annotated with the {@link ZenEvent.Bus}
     * annotation. All fields that respect these characteristics will be discovered and automatically registered as
     * known event buses by CraftTweaker. As an example, the following snippet of code shows the conventional way of
     * registering multiple event buses:</p>
     *
     * <pre>
     *     {@code
     *      @ZenEvent.BusCarrier
     *      public final class MyEvents {
     *          @ZenEvent.Bus(FooEvent.class) public static final IEventBus<FooEvent> FOO_BUS = makeBus(FooEvent.class);
     *          @ZenEvent.Bus(BarEvent.class) public static final IEventBus<BarEvent> BAR_BUS = makeBus(BarEvent.class);
     *          @ZenEvent.Bus(BazEvent.class) public static final IEventBus<BazEvent> BAZ_BUS = makeBus(BazEvent.class);
     *      }
     *     }
     * </pre>
     *
     * <p>Note that usage of this annotation does not allow for automatic discovery of the association between event and
     * event bus. The type of event carried by the bus must then be always specified manually. Refer to the
     * {@link ZenEvent.Bus} documentation for more information.</p>
     *
     * <p>It is generally customary to use this annotation solely on classes that are mere event bus carriers and do
     * <strong>not</strong> represent events themselves. In such a case, it is suggested to use the {@link ZenEvent}
     * annotation directly.</p>
     *
     * @since 11.0.0
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface BusCarrier {}
    
    /**
     * Marks a field within a class as being an event bus that should be made available to ZenCode scripts, allowing for
     * automatic discovery.
     *
     * <p>To allow for automatic discovery of this field, the containing class must be annotated with either
     * {@link ZenEvent} or {@link ZenEvent.BusCarrier} accordingly. Refer to the specific documentation for further
     * information and the distinguishing factors between the two.</p>
     *
     * <p>Moreover, the annotated field must be {@code public static final} and have a type of
     * {@link com.blamejared.crafttweaker.api.event.bus.IEventBus}. If that's not the case, then an error will be raised
     * at runtime. Note that fields that follow the above characteristics but are not annotated with this annotation
     * will not be discovered automatically by CraftTweaker.</p>
     *
     * <p>The association between the bus and the event carried by it can be either automatic or manual, according to
     * the limitations imposed by the parent class annotations. To enable automatic discovery, the {@link #value()} of
     * this annotation should be set to its default value {@link ZenEvent.Bus.Auto} (or left empty, as it will be set to
     * the default automatically by Java). Refer to the documentation of the {@code ZenEvent.Bus.Auto} annotation for
     * more details on the automatic procedure. For manual discovery, the class of the event being carried by the bus
     * should be specified directly.</p>
     *
     * <p>Note that, due to Java limitations, it is not possible to use this method to automatically register an event
     * bus that has a generic type (e.g. {@code IEventBus<MyGenericEvent<Foo>>}), as Java offers no way of determining
     * the generic types in this way. Similarly, it is not possible to discover both automatically or manually an event
     * bus for the {@link ZenEvent.Bus.Auto} annotation itself, if desired.</p>
     *
     * @since 11.0.0
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Bus {
        
        /**
         * The type of the event carried by the bus, or {@link ZenEvent.Bus.Auto} for automatic discovery.
         *
         * @return The type of event carried by the class.
         *
         * @since 11.0.0
         */
        Class<?> value() default Auto.class;
        
        /**
         * Enables automatic determination of the type of event carried by an event bus.
         *
         * <p>Automatic determination of the event type is allowed solely if the class owning the field annotated with
         * {@link ZenEvent.Bus} is annotated by the {@link ZenEvent} annotation. Automatic discovery is thus prohibited
         * in classes annotated by {@link ZenEvent.BusCarrier}.</p>
         *
         * <p>If the above requirement is met, then the class is examined and an attempt to locate either a
         * {@link org.openzen.zencode.java.ZenCodeType.Name} or a
         * {@link com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration} annotation is performed.
         * If at least one is present, then automatic discovery can proceed, otherwise automatic determination is not
         * possible.</p>
         *
         * <p>If the {@code ZenCodeType.Name} annotation is present, then the owning class itself is treated as the
         * event class itself, and thus that is the result of the automatic discovery process. If the
         * {@code NativeTypeRegistration} annotation is present, on the other hand, the class references by its
         * {@link com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration#value()} will be used as
         * the result of the automatic discovery process. If both annotations are present, then {@code ZenCodeType.Name}
         * will take precedence.</p>
         *
         * @since 11.0.0
         */
        @Target({})
        @Retention(RetentionPolicy.RUNTIME)
        @interface Auto {}
    }
    
}
