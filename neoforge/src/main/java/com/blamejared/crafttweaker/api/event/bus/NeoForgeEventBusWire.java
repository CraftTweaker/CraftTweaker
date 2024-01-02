package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.Phase;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.base.Suppliers;
import com.google.common.reflect.TypeToken;
import net.minecraft.Util;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.GenericEvent;
import net.neoforged.bus.api.IGenericEvent;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.commons.lang3.NotImplementedException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Wires the given {@link IEventBus} onto the default NeoForge's {@link net.neoforged.bus.api.IEventBus}.
 *
 * <p>The default event bus is {@link NeoForge#EVENT_BUS}.</p>
 *
 * <p>By default, all even {@link Phase}s are automatically wired according to the various {@link EventPriority} values
 * provided by NeoForge.</p>
 *
 * @since 15.0.0
 */
public final class NeoForgeEventBusWire implements IEventBusWire {
    
    @FunctionalInterface
    private interface ListenerRegistrationHandler<E extends Event> {
        void register(final EventPriority priority, final boolean receiveCanceled, final Consumer<E> handler);
    }
    
    private static final Supplier<IEventBusWire> INSTANCE = Suppliers.memoize(NeoForgeEventBusWire::new);
    private static final Map<Phase, EventPriority> PRIORITIES = Util.make(new EnumMap<>(Phase.class), it -> {
        it.put(Phase.EARLIEST, EventPriority.HIGHEST);
        it.put(Phase.NORMAL, EventPriority.NORMAL);
        it.put(Phase.LATEST, EventPriority.LOWEST);
    });
    
    private NeoForgeEventBusWire() {}
    
    /**
     * Obtains an instance of this class.
     *
     * @return An instance of this class.
     *
     * @since 15.0.0
     */
    public static IEventBusWire of() {
        return INSTANCE.get();
    }
    
    @Override
    public <T> void registerBusForDispatch(final TypeToken<T> eventType, final IEventBus<T> bus) {
        
        if (!eventType.isSubtypeOf(Event.class)) {
            throw new IllegalArgumentException("Unable to wire EventBus to NeoForge bus for type " + eventType);
        }
        
        this.registerBus(GenericUtil.uncheck(eventType), GenericUtil.uncheck(bus));
    }
    
    private <T extends Event> void registerBus(final TypeToken<T> eventType, final IEventBus<T> bus) {
        
        final ListenerRegistrationHandler<T> registrationHandler = this.discoverRegistrationHandler(eventType);
        PRIORITIES.forEach((phase, priority) -> registrationHandler.register(priority, true, e -> bus.post(phase, e)));
    }
    
    private <T extends Event> ListenerRegistrationHandler<T> discoverRegistrationHandler(final TypeToken<T> token) {
        
        final net.neoforged.bus.api.IEventBus bus = this.discoverBus(token);
        //noinspection removal
        final boolean isGeneric = token.isSubtypeOf(IGenericEvent.class);
        final Class<T> rawClass = GenericUtil.uncheck(token.getRawType());
        if (!isGeneric) {
            return (priority, receiveCanceled, handler) -> bus.addListener(priority, receiveCanceled, rawClass, handler);
        }
        
        return GenericUtil.uncheck(this.discoverGenericHandler(GenericUtil.uncheck(token), GenericUtil.uncheck(rawClass), bus));
    }
    
    private <T extends Event> net.neoforged.bus.api.IEventBus discoverBus(final TypeToken<T> token) {
        if (token.isSubtypeOf(IModBusEvent.class)) {
            // TODO("")
            throw new NotImplementedException("TODO");
        }
        
        return NeoForge.EVENT_BUS;
    }
    
    @SuppressWarnings("removal")
    private <E, T extends GenericEvent<? extends E>> ListenerRegistrationHandler<T> discoverGenericHandler(
            final TypeToken<T> token,
            final Class<T> rawType,
            final net.neoforged.bus.api.IEventBus bus
    ) {
        final Class<E> genericType = GenericUtil.uncheck(this.determineGeneric(token.getType()));
        // TODO("Remove when NeoForge fixes the bug")
        Objects.requireNonNull(genericType, "Expected a generic type for a generic event: buses listening to all instances are unsupported as of now");
        return (priority, receiveCanceled, handler) -> bus.addGenericListener(genericType, priority, receiveCanceled, rawType, handler);
    }
    
    private Class<?> determineGeneric(final Type type) {
        if (!(type instanceof ParameterizedType generic)) {
            throw new IllegalArgumentException("Generic event must be specified by a parameterized type, not " + type.getTypeName() + '/' + type.getClass().getName());
        }
        
        final Type[] generics = generic.getActualTypeArguments();
        if (generics.length != 1) {
            throw new IllegalArgumentException("Generic event on NeoForge requires only a single parameter type, but got " + Arrays.deepToString(generics));
        }
        
        final Type argument = generics[0];
        return this.determineInnerGeneric(argument);
    }
    
    private Class<?> determineInnerGeneric(final Type type) {
        if (type instanceof Class<?> clazz) {
            // The type is already a raw type
            return clazz;
        }
        
        if (type instanceof ParameterizedType generic) {
            // NeoForge discards any further specifications, so we can just convert it to a raw type and make do with it
            final Type raw = generic.getRawType();
            if (!(raw instanceof Class<?> clazz)) {
                throw new UnsupportedOperationException("Java reflection API has changed");
            }
            return clazz;
        }
        
        if (type instanceof WildcardType wildcard) {
            final Type[] upperBounds = wildcard.getUpperBounds();
            final Type[] lowerBounds = wildcard.getLowerBounds();
            if (upperBounds.length == 1 && lowerBounds.length == 0 && upperBounds[0] == Object.class) {
                return null;
            }
            
            throw new IllegalArgumentException("Generic event on NeoForge must not be parameterized with wildcards");
        }
        
        throw new IllegalArgumentException("Type " + type.getTypeName() + '/' + type.getClass().getName() + " is unsupported on NeoForge");
    }
    
}
