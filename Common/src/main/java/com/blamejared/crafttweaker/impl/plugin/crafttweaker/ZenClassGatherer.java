package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Either;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

final class ZenClassGatherer {
    
    record ZenClassData(Class<?> clazz, String loader) {}
    
    private record ZenCandidates(List<ZenClassData> classCandidates, Runnable modsProvidingLister) {}
    
    private final Supplier<ZenCandidates> zenCandidates;
    
    ZenClassGatherer() {
        
        this.zenCandidates = Suppliers.memoize(() -> {
            final CraftTweakerModList modList = new CraftTweakerModList();
            final List<ZenClassData> classes = Services.PLATFORM.findClassesWithAnnotation(ZenRegister.class, modList::add, this::checkModDependencies)
                    .filter(Objects::nonNull)
                    .map(it -> new ZenClassData(it, it.getDeclaredAnnotation(ZenRegister.class).loader()))
                    .toList();
            return new ZenCandidates(Collections.unmodifiableList(classes), modList::printToLog);
        });
    }
    
    void onCandidates(final Consumer<ZenClassData> consumer) {
        
        this.zenCandidates.get().classCandidates().forEach(consumer);
    }
    
    void listProviders() {
        
        this.zenCandidates.get().modsProvidingLister().run();
    }
    
    @SuppressWarnings("unchecked")
    private boolean checkModDependencies(final Either<ZenRegister, Map<String, Object>> annotationData) {
        
        return annotationData.map(zenRegister -> List.of(zenRegister.modDeps()), map -> (List<String>) map.getOrDefault("modDeps", List.of()))
                .stream()
                .allMatch(Services.PLATFORM::isModLoaded);
    }
    
}
