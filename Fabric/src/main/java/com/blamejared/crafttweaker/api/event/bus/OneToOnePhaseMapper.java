package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.Phase;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class OneToOnePhaseMapper implements IFabricPhaseMapper {
    
    private final Map<ResourceLocation, Phase> phaseMappings;
    
    private OneToOnePhaseMapper(final Map<ResourceLocation, Phase> phaseMappings) {
        this.phaseMappings = Collections.unmodifiableMap(phaseMappings);
    }
    
    @SafeVarargs
    static OneToOnePhaseMapper of(final Map.Entry<ResourceLocation, Phase>... phases) {
        
        Objects.requireNonNull(phases, "phases");
        final Map<ResourceLocation, Phase> phaseMappings = IntStream.range(0, phases.length)
                .mapToObj(it -> Objects.requireNonNull(phases[it], () -> "phases[" + it + ']'))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        
        if (!phaseMappings.containsKey(Event.DEFAULT_PHASE)) {
            throw new NoSuchElementException("Missing mapping for '" + Event.DEFAULT_PHASE + "', but it is required");
        }
        
        return new OneToOnePhaseMapper(phaseMappings);
    }
    
    @Override
    public <E> void prepareEvent(final Event<E> event) {}
    
    @Override
    public <W> void dispatch(final IEventBus<W> bus, final W event, final ResourceLocation fabricPhase) {
        final Phase phase = this.phaseMappings.get(fabricPhase);
        assert phase != null;
        bus.post(phase, event);
    }
    
    @Override
    public Set<ResourceLocation> targetPhases() {
        return this.phaseMappings.keySet();
    }
    
}
