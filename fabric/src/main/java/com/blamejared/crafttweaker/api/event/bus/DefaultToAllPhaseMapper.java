package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;

final class DefaultToAllPhaseMapper implements IFabricPhaseMapper {
    
    private static final DefaultToAllPhaseMapper INSTANCE = new DefaultToAllPhaseMapper();
    
    private final Set<ResourceLocation> target;
    
    private DefaultToAllPhaseMapper() {
        this.target = Set.of(Event.DEFAULT_PHASE);
    }
    
    static IFabricPhaseMapper of() {
        return GenericUtil.uncheck(INSTANCE);
    }
    
    
    @Override
    public <E> void prepareEvent(final Event<E> event) {}
    
    @Override
    public <W> void dispatch(final IEventBus<W> bus, final W event, final ResourceLocation fabricPhase) {
        assert fabricPhase == Event.DEFAULT_PHASE;
        bus.post(event);
    }
    
    @Override
    public Set<ResourceLocation> targetPhases() {
        return this.target;
    }
    
}
