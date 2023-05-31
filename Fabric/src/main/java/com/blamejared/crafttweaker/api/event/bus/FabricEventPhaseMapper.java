package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.Phase;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.searchtree.SearchTree;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Handles mapping Fabric event phases to {@link Phase} instances.
 *
 * <p>Fabric events represent phases as pure {@link ResourceLocation} instances, meaning that the association between
 * them is more free-form than other platforms. It is thus necessary to manually perform mapping between the various
 * phases that a Fabric event might have to the limited set provided by {@link Phase}.</p>
 *
 * <p>Every Fabric event has at least one phase, the {@link Event#DEFAULT_PHASE}. It is customary, although not strictly
 * required, to associate this phase with {@link Phase#NORMAL}. It is nevertheless mandatory to associate the default
 * Fabric phase with at least one {@code Phase}.</p>
 *
 * <p>Every Fabric phase must be associated with a corresponding {@code Phase}, although the mapping need not be
 * bijective: multiple Fabric phases can be associated to the same {@code Phase}.</p>
 *
 * <p>This class acts as a {@link Function} that maps a {@link ResourceLocation} to a {@link Phase}.</p>
 *
 * @since 11.0.0
 */
public final class FabricEventPhaseMapper implements Function<ResourceLocation, Phase> {
    private final Map<ResourceLocation, Phase> function;
    
    private FabricEventPhaseMapper(final Map<ResourceLocation, Phase> function) {
        this.function = function;
    }
    
    /**
     * Obtains a {@link FabricEventPhaseMapper} that performs default behavior.
     *
     * <p>By default, it is assumed that the given {@link Event} has only a single phase, namely the
     * {@linkplain Event#DEFAULT_PHASE default phase}, which is associated to {@link Phase#NORMAL}. No additional
     * associations are performed.</p>
     *
     * @return A {@link FabricEventPhaseMapper} with default behavior.
     *
     * @since 11.0.0
     */
    public static FabricEventPhaseMapper of() {
        return of(Event.DEFAULT_PHASE, Phase.NORMAL);
    }
    
    /**
     * Obtains a {@link FabricEventPhaseMapper} that maps the given {@link ResourceLocation} to the given {@link Phase}.
     *
     * <p>While no restrictions are given on the value of {@code location}, it is almost always required for it to be
     * {@link Event#DEFAULT_PHASE}, as that phase exists on all Fabric events. Nevertheless, the method is provided for
     * convenience, in case a mapping different from the default one is desired.</p>
     *
     * @param location The {@link ResourceLocation} identifying the Fabric event phase.
     * @param phase The {@link Phase} that corresponds to the given Fabric phase.
     * @return A {@link FabricEventPhaseMapper} that maps the given {@code location} to the given {@code phase}.
     *
     * @since 11.0.0
     */
    public static FabricEventPhaseMapper of(final ResourceLocation location, final Phase phase) {
        return of(Map.entry(location, phase));
    }
    
    /**
     * Obtains a {@link FabricEventPhaseMapper} that uses the given {@link Map.Entry}-set to map Fabric phases to
     * {@link Phase}s.
     *
     * <p>At least one of the entries <strong>must</strong> have {@link Event#DEFAULT_PHASE} as the key, as that phase
     * must be present in all Fabric events.</p>
     *
     * @param maps The various entries, each of them mapping a {@link ResourceLocation} representing a Fabric phase to
     *             the corresponding {@link Phase}. The given set of entries must be a function, meaning that it is not
     *             allowed for the same {@code ResourceLocation} to be present in two distinct entries, unless the
     *             corresponding {@code Phase}s also coincide. No requirements are given on the bijective property: it
     *             is possible for two different {@code ResourceLocation}s to map to the same {@code Phase}.
     * @return A {@link FabricEventPhaseMapper} that uses the given entries to perform mapping.
     * @throws NoSuchElementException If no entry has {@link Event#DEFAULT_PHASE} as a key.
     * @since 11.0.0
     */
    @SafeVarargs
    public static FabricEventPhaseMapper of(final Map.Entry<ResourceLocation, Phase>... maps) {
        
        Objects.requireNonNull(maps, "maps");
        final Map<ResourceLocation, Phase> function = IntStream.range(0, maps.length)
                .mapToObj(it -> Objects.requireNonNull(maps[it], "maps[" + it + ']'))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        
        if (!function.containsKey(Event.DEFAULT_PHASE)) {
            throw new NoSuchElementException("Unmapped default phase");
        }
        
        return new FabricEventPhaseMapper(function);
    }
    
    @Override
    public Phase apply(final ResourceLocation resourceLocation) {
        
        return this.function.computeIfAbsent(resourceLocation, it -> {
            throw new NoSuchElementException("Unmapped location " + it);
        });
    }
    
    Set<ResourceLocation> phases() {
        return this.function.keySet();
    }
    
}
