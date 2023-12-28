package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.Phase;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Set;

/**
 * Handles mapping Fabric event phases to {@link Phase} instances.
 *
 * <p>Fabric events represent phases as pure {@link ResourceLocation} instances, meaning that the association between
 * them is more free-form than other platforms. It is thus necessary to manually perform mapping between the various
 * phases that a Fabric event might have to the limited set provided by {@link Phase}.</p>
 *
 * <p>To provide more flexibility, the mapper is responsible for the dispatch of the actual event onto the target
 * {@link IEventBus} for the specified Fabric phase. No restrictions are imposed upon the behavior of the mapper, nor on
 * the amount of events or {@code Phase}s that need to be notified.</p>
 *
 * @since 11.0.0
 */
public interface IFabricPhaseMapper {
    
    /**
     * Obtains a mapper that maps the {@linkplain Event#DEFAULT_PHASE default phase} of an event onto all known
     * {@link Phase}s.
     *
     * <p>In other words, the only Fabric phase to be considered is the default phase. Whenever the event is received on
     * the default phase, all {@link Phase}s are notified according to the order specified by
     * {@link IEventBus#post(Object)}.</p>
     *
     * @return A mapper that maps the default phase to all phases.
     *
     * @since 11.0.0
     */
    static IFabricPhaseMapper defaultToAll() {
        
        return DefaultToAllPhaseMapper.of();
    }
    
    /**
     * Obtains a mapper that maps the {@linkplain Event#DEFAULT_PHASE default phase} of an event onto the given
     * {@link Phase}.
     *
     * <p>In other words, the only Fabric phase that will be considered is the default phase. Whenever the event is
     * received on that Fabric phase, the event will be dispatched on the {@link IEventBus} solely for the specified
     * {@code Phase}. All event handlers on other phases will not be notified, allowing the restriction of the set of
     * handlers that is allowed to be notified.</p>
     *
     * @param phase The {@link Phase} of handlers that should be notified.
     * @return A mapper that maps the default phase to the given {@code phase}.
     *
     * @since 11.0.0
     */
    static IFabricPhaseMapper solely(final Phase phase) {
        
        return oneToOne(Map.entry(Event.DEFAULT_PHASE, phase));
    }
    
    /**
     * Obtains a mapper that maps the various Fabric phases represented as {@link ResourceLocation} to the corresponding
     * {@link Phase}s.
     *
     * <p>In other words, the event will be listened to on all the specified phases and, when it is received, it will be
     * dispatched on the {@link IEventBus} according to the given phase mapping.</p>
     *
     * <p>The mappings are represented as key-value pairs of the type {@link Map.Entry}. It is forbidden for two
     * key-value pairs to share the same key, but this does not carry to the value: the function may in fact not be
     * bijective. Two Fabric phases may map to the same {@code Phase}. Regardless, a mapping that associates the event's
     * {@linkplain Event#DEFAULT_PHASE default phase} to a {@code Phase} <strong>must</strong> be present.</p>
     *
     * @param phases A series of key-value pairs representing the mappings.
     * @return A mapper that maps phases according to the given specifications.
     *
     * @since 11.0.0
     */
    @SafeVarargs
    static IFabricPhaseMapper oneToOne(final Map.Entry<ResourceLocation, Phase>... phases) {
        
        return OneToOnePhaseMapper.of(phases);
    }
    
    /**
     * Prepares the Fabric {@link Event} object for the phase mapping process.
     *
     * @param event The event to prepare.
     * @param <E> The type of the functional interface representing the Fabric event.
     *
     * @since 11.0.0
     */
    <E> void prepareEvent(final Event<E> event);
    
    /**
     * Dispatches the given event onto the {@link IEventBus} according to the given Fabric phase.
     *
     * <p>No restrictions are imposed on the kind of mapping. Moreover, it is guaranteed that the value of
     * {@code fabricPhase} will always match one of the values returned by {@link #targetPhases()}.</p>
     *
     * @param bus The bus on which the event should be dispatched.
     * @param event The event to dispatch.
     * @param fabricPhase The {@link ResourceLocation} representing the Fabric phase that should be dispatched.
     * @param <W> The type of the event that should be dispatched on the bus.
     *
     * @since 11.0.0
     */
    <W> void dispatch(final IEventBus<W> bus, final W event, final ResourceLocation fabricPhase);
    
    /**
     * Gets the phases that should be mapped by this mapper.
     *
     * <p>These phases will be the only ones for which the mapper will be asked to dispatch an event through
     * {@link #dispatch(IEventBus, Object, ResourceLocation)}.</p>
     *
     * <p>The returned set <strong>must</strong> always contain at least {@link Event#DEFAULT_PHASE}.</p>
     *
     * @return The set of targeted phases.
     *
     * @since 11.0.0
     */
    Set<ResourceLocation> targetPhases();
    
}
