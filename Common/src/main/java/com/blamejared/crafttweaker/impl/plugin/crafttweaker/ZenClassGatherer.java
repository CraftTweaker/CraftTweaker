package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.ClassUtil;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Either;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

final class ZenClassGatherer {
    
    record ZenClassData(Class<?> clazz, String loader) {}
    
    private record ZenCandidates(List<ZenClassData> classCandidates, Runnable modsProvidingLister) {}
    
    private final Supplier<ZenCandidates> zenCandidates;
    
    ZenClassGatherer() {
        
        this.zenCandidates = Suppliers.memoize(() -> {
            final CraftTweakerModList modList = new CraftTweakerModList();
            final List<ZenClassData> classes = ClassUtil.findClassesWithAnnotation(ZenRegister.class, modList::add, this::checkModDependencies)
                    .filter(Objects::nonNull)
                    .flatMap(this::makeForClass)
                    .toList();
            return new ZenCandidates(classes, modList::printToLog);
        });
    }
    
    void gatherCandidates() {
        
        this.zenCandidates.get();
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
    
    private Stream<ZenClassData> makeForClass(final Class<?> clazz) {
        
        return Arrays.stream(clazz.getDeclaredAnnotation(ZenRegister.class).loaders())
                .map(it -> new ZenClassData(clazz, it));
    }
    
}
