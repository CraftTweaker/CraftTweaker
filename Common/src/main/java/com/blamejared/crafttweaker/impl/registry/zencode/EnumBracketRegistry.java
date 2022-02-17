package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class EnumBracketRegistry {
    
    private record EnumData(Map<ResourceLocation, Class<? extends Enum<?>>> enums) {
        
        EnumData() {
            
            this(new HashMap<>());
        }
        
    }
    
    private final Map<IScriptLoader, EnumData> data = new HashMap<>();
    
    public <T extends Enum<T>> void register(final IScriptLoader loader, final ResourceLocation id, final Class<T> clazz) {
        
        final EnumData data = this.data.computeIfAbsent(loader, it -> new EnumData());
        final Map<ResourceLocation, Class<? extends Enum<?>>> enums = data.enums();
        final Class<? extends Enum<?>> old = enums.get(id);
        if(old != null) {
            throw new IllegalArgumentException("Attempted enum overriding on id " + id + ": old " + old.getName() + ", new " + clazz.getName());
        }
        enums.put(id, clazz);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Enum<T>> Optional<Class<T>> getEnum(final IScriptLoader loader, final ResourceLocation type) {
        
        return Optional.ofNullable(this.data.get(loader))
                .flatMap(it -> Optional.ofNullable(it.enums().get(type)))
                .map(it -> (Class<T>) it);
    }
    
    
    public Map<ResourceLocation, Class<? extends Enum<?>>> getEnums(final IScriptLoader loader) {
        
        return ImmutableMap.copyOf(this.data.getOrDefault(loader, new EnumData()).enums());
    }
    
}
