package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IScriptLoadSourceRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

final class LoadSourceRegistrationHandler implements IScriptLoadSourceRegistrationHandler {
    
    private final Map<ResourceLocation, IScriptLoadSource> sources;
    
    private LoadSourceRegistrationHandler() {
        
        this.sources = new HashMap<>();
    }
    
    static Collection<IScriptLoadSource> gather(final Consumer<IScriptLoadSourceRegistrationHandler> populatingConsumer) {
        
        final LoadSourceRegistrationHandler handler = new LoadSourceRegistrationHandler();
        populatingConsumer.accept(handler);
        return ImmutableList.copyOf(handler.sources.values());
    }
    
    @Override
    public void registerLoadSource(final ResourceLocation id) {
        
        if(this.sources.containsKey(id)) {
            
            throw new IllegalArgumentException("Load source '" + id + "' was already registered");
        }
        
        this.sources.put(id, new LoadSource(id));
    }
    
}
