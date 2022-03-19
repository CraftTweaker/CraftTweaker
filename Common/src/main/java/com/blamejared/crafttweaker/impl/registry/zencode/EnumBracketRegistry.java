package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class EnumBracketRegistry {
    
    private record EnumData(Map<ResourceLocation, Class<? extends Enum<?>>> enums) {
        
        EnumData() {
            
            this(new HashMap<>());
        }
        
    }
    
    private final Map<IScriptLoader, EnumData> data = new HashMap<>();
    
    public <T extends Enum<T>> void register(final IScriptLoader loader, final ResourceLocation id, final Class<T> clazz) {
        
        final EnumData data = this.data.computeIfAbsent(loader, it -> {
            throw new IllegalStateException("No data for loader " + loader + ": this cannot happen");
        });
        final Map<ResourceLocation, Class<? extends Enum<?>>> enums = data.enums();
        final Class<? extends Enum<?>> old = enums.get(id);
        if(old != null) {
            throw new IllegalArgumentException("Attempted enum overriding on id " + id + ": old " + old.getName() + ", new " + clazz.getName());
        }
        enums.put(id, clazz);
    }
    
    public void fillLoaderData(final Collection<IScriptLoader> loader) {
        
        loader.forEach(it -> this.data.put(it, new EnumData()));
    }
    
    public void applyInheritanceRules() {
        
        final Map<IScriptLoader, EnumData> snapshot = this.createSnapshot();
        final Map<IScriptLoader, EnumData> inherited = this.applyInheritanceRules(snapshot);
        this.data.clear();
        this.data.putAll(inherited);
    }
    
    public <T extends Enum<T>> Optional<Class<T>> getEnum(final IScriptLoader loader, final ResourceLocation type) {
        
        return Optional.ofNullable(this.data.get(loader))
                .flatMap(it -> Optional.ofNullable(it.enums().get(type)))
                .map(it -> (Class<T>) it);
    }
    
    public Map<ResourceLocation, Class<? extends Enum<?>>> getEnums(final IScriptLoader loader) {
        
        return ImmutableMap.copyOf(this.data.getOrDefault(loader, new EnumData()).enums());
    }
    
    private Map<IScriptLoader, EnumData> createSnapshot() {
        
        return this.data.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                it -> new EnumData(new HashMap<>(it.getValue().enums()))
                        )
                );
    }
    
    private Map<IScriptLoader, EnumData> applyInheritanceRules(final Map<IScriptLoader, EnumData> snapshot) {
        
        return snapshot.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, it -> this.applyInheritanceRules(it.getKey(), snapshot)));
    }
    
    private EnumData applyInheritanceRules(final IScriptLoader loader, final Map<IScriptLoader, EnumData> snapshot) {
        
        final Map<ResourceLocation, Class<? extends Enum<?>>> inheritedData = new HashMap<>();
        final Collection<Map<ResourceLocation, Class<? extends Enum<?>>>> inheritanceData = this.computeInheritanceData(loader, snapshot);
        inheritanceData.forEach(it -> {
            try {
                this.tryMerge(inheritedData, it);
            } catch(final Exception e) {
                throw new IllegalStateException("Unable to apply inheritance rules for " + loader.name(), e);
            }
        });
        return new EnumData(inheritedData);
    }
    
    private Collection<Map<ResourceLocation, Class<? extends Enum<?>>>> computeInheritanceData(final IScriptLoader loader, final Map<IScriptLoader, EnumData> snapshot) {
        
        return Stream.concat(loader.allInheritedLoaders().stream(), Stream.of(loader))
                .map(snapshot::get)
                .filter(Objects::nonNull)
                .map(EnumData::enums)
                .toList();
    }
    
    private void tryMerge(final Map<ResourceLocation, Class<? extends Enum<?>>> loaderEnums, final Map<ResourceLocation, Class<? extends Enum<?>>> inheritedEnums) {
        
        inheritedEnums.forEach((id, clazz) -> {
            if(loaderEnums.containsKey(id)) {
                throw new IllegalStateException(id + " is already assigned to an enum");
            }
            loaderEnums.put(id, clazz);
        });
    }
    
}
