package com.blamejared.crafttweaker.platform.sides;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public enum DistributionType {
    CLIENT(Set.of("client")), SERVER(Set.of("server", "dedicated_Server"));
    
    private static final Map<Enum<?>, DistributionType> CACHE = new HashMap<>();
    
    private final Set<String> names;
    
    DistributionType(Set<String> names) {
        
        this.names = names;
    }
    
    public boolean isServer() {
        
        return this == SERVER;
    }
    
    public boolean isClient() {
        
        return this == CLIENT;
    }
    
    public Set<String> getNames() {
        
        return names;
    }
    
    public boolean matches(String name) {
        
        return this.getNames().contains(name.toLowerCase(Locale.ROOT));
    }
    
    public static DistributionType from(Enum<?> other) {
        
        return CACHE.computeIfAbsent(other, key -> {
            final String name = key.name().toLowerCase(Locale.ROOT);
            return Arrays.stream(values())
                    .filter(it -> it.getNames().contains(name))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Cannot create DistributionType from provided enum " + key + " of class " + key.getClass()));
        });
    }
}
