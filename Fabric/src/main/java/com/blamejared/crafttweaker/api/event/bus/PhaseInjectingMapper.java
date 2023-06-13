package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.event.Phase;
import com.google.common.base.Suppliers;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.resources.ResourceLocation;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

// TODO("@JARED: What do you think? Do you want this? Note that this is currently **not** wired in")
final class PhaseInjectingMapper implements IFabricPhaseMapper {
    @SuppressWarnings("ClassCanBeRecord")
    private static final class HackyPrepare {
        private final Class<?> arrayBackedEvent;
        private final VarHandle sortedPhases;
        private final VarHandle id;
        
        private HackyPrepare(final Class<?> arrayBackedEvent, final VarHandle sortedPhases, final VarHandle id) {
            this.arrayBackedEvent = arrayBackedEvent;
            this.sortedPhases = sortedPhases;
            this.id = id;
        }
        
        static HackyPrepare of() {
            try {
                final MethodHandles.Lookup lookup = MethodHandles.lookup();
                final Class<?> arrayBackedEvent = Class.forName("net.fabricmc.fabric.impl.base.event.ArrayBackedEvent");
                final Class<?> eventPhaseData = Class.forName("net.fabricmc.fabric.impl.base.event.EventPhaseData");
                
                final VarHandle sortedPhases = MethodHandles.privateLookupIn(arrayBackedEvent, lookup).findVarHandle(arrayBackedEvent, "sortedPhases", List.class);
                final VarHandle id = MethodHandles.privateLookupIn(eventPhaseData, lookup).findVarHandle(eventPhaseData, "id", ResourceLocation.class);
                
                return new HackyPrepare(arrayBackedEvent, sortedPhases, id);
            } catch (final Throwable ignored) {
                return null;
            }
        }
        
        <E> boolean prepare(final Event<E> event) {
            try {
                if (!this.arrayBackedEvent.isInstance(event)) {
                    return false;
                }
                
                final List<?> phases = (List<?>) this.sortedPhases.get(this.arrayBackedEvent.cast(event));
                assert phases.size() > 0;
                final Object first = phases.get(0);
                final Object last = phases.get(phases.size() - 1);
                
                final ResourceLocation before = (ResourceLocation) this.id.get(first);
                final ResourceLocation after = (ResourceLocation) this.id.get(last);
                
                event.addPhaseOrdering(PhaseInjectingMapper.EARLIEST, before);
                event.addPhaseOrdering(after, PhaseInjectingMapper.LATEST);
                return true;
            } catch (final ClassCastException e) {
                return false;
            }
        }
    }
    
    private static final PhaseInjectingMapper INSTANCE = new PhaseInjectingMapper();
    
    private static final ResourceLocation EARLIEST = CraftTweakerConstants.rl("earliest");
    private static final ResourceLocation LATEST = CraftTweakerConstants.rl("latest");
    
    private static final boolean DO_HACKY_PREPARE = true;
    
    private final Map<ResourceLocation, Phase> phases;
    private final Supplier<HackyPrepare> hackyPrepare;
    
    private PhaseInjectingMapper() {
        this.phases = Map.of(EARLIEST, Phase.EARLIEST, Event.DEFAULT_PHASE, Phase.NORMAL, LATEST, Phase.LATEST);
        this.hackyPrepare = DO_HACKY_PREPARE? Suppliers.memoize(HackyPrepare::of) : null;
    }
    
    @Override
    public <E> void prepareEvent(final Event<E> event) {
        if (DO_HACKY_PREPARE) {
            if (this.hackyPrepare(event)) {
                return;
            }
        }
        event.addPhaseOrdering(EARLIEST, Event.DEFAULT_PHASE);
        event.addPhaseOrdering(Event.DEFAULT_PHASE, LATEST);
    }
    
    @Override
    public <W> void dispatch(final IEventBus<W> bus, final W event, final ResourceLocation fabricPhase) {
        final Phase phase = this.phases.get(fabricPhase);
        assert phase != null;
        bus.post(phase, event);
    }
    
    @Override
    public Set<ResourceLocation> targetPhases() {
        return this.phases.keySet();
    }
    
    private <E> boolean hackyPrepare(final Event<E> event) {
        final HackyPrepare p = this.hackyPrepare.get();
        if (p == null) {
            return false;
        }
        return p.prepare(event);
    }
    
}
