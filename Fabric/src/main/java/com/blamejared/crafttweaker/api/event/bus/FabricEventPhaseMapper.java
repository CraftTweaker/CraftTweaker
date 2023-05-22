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

public final class FabricEventPhaseMapper implements Function<ResourceLocation, Phase> {
    private final Map<ResourceLocation, Phase> function;
    
    private FabricEventPhaseMapper(final Map<ResourceLocation, Phase> function) {
        this.function = function;
    }
    
    public static FabricEventPhaseMapper of() {
        return of(Event.DEFAULT_PHASE, Phase.NORMAL);
    }
    
    public static FabricEventPhaseMapper of(final ResourceLocation location, final Phase phase) {
        return of(Map.entry(location, phase));
    }
    
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
