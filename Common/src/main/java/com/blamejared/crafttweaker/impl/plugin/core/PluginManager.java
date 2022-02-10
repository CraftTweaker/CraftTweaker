package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.impl.registry.CraftTweakerRegistry;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public final class PluginManager {
    
    public static final class Req {
        
        private static final AtomicBoolean X = new AtomicBoolean(false);
        
        private Req() {
            
            if(X.get()) {
                throw new RuntimeException();
            }
            X.set(true);
        }
        
    }
    
    private final List<DecoratedCraftTweakerPlugin> plugins;
    private final Req req;
    
    private PluginManager(final List<DecoratedCraftTweakerPlugin> plugins) {
        
        this.plugins = List.copyOf(plugins);
        this.req = new Req();
    }
    
    public static PluginManager of() {
        
        return new PluginManager(discoverPlugins());
    }
    
    private static List<DecoratedCraftTweakerPlugin> discoverPlugins() {
        
        return Services.PLATFORM.findClassesWithAnnotation(CraftTweakerPlugin.class)
                .map(PluginManager::checkAndCast)
                .map(PluginManager::initPlugin)
                .filter(Objects::nonNull)
                .toList();
    }
    
    @SuppressWarnings("unchecked")
    private static Pair<ResourceLocation, Class<? extends ICraftTweakerPlugin>> checkAndCast(final Class<?> clazz) {
        
        if(!ICraftTweakerPlugin.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Invalid plugin class annotated with @CraftTweakerPlugin: it must implement ICraftTweakerPlugin");
        }
        
        final ResourceLocation id;
        try {
            id = new ResourceLocation(Objects.requireNonNull(clazz.getAnnotation(CraftTweakerPlugin.class)).value());
        } catch(final ResourceLocationException e) {
            throw new IllegalArgumentException("Invalid plugin class ID: not a valid resource location", e);
        }
        
        return Pair.of(id, (Class<? extends ICraftTweakerPlugin>) clazz);
    }
    
    private static DecoratedCraftTweakerPlugin initPlugin(final Pair<ResourceLocation, Class<? extends ICraftTweakerPlugin>> pluginData) {
        
        try {
            final ResourceLocation id = pluginData.getFirst();
            final ICraftTweakerPlugin plugin = pluginData.getSecond().getConstructor().newInstance();
            CraftTweakerAPI.LOGGER.info("Successfully identified and loaded plugin {}", id);
            return new DecoratedCraftTweakerPlugin(id, plugin);
        } catch(final InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            CraftTweakerAPI.LOGGER.error("Unable to load plugin class '" + pluginData.getSecond()
                    .getName() + "' due to an error", e);
            return null;
        }
    }
    
    public void loadPlugins() {
        
        final IPluginRegistryAccess pluginRegistryAccess = CraftTweakerRegistry.pluginAccess(this.req);
        final Collection<IScriptLoader> loaders = LoaderRegistrationHandler.gather(h -> this.plugins.forEach(p -> p.registerLoaders(h)));
        
        pluginRegistryAccess.registerLoaders(loaders);
        pluginRegistryAccess.registerLoadSources(LoadSourceRegistrationHandler.gather(h -> this.plugins.forEach(p -> p.registerLoadSource(h))));
    }
    
    public void broadcastEnd() {
        
        // TODO("")
    }
    
}
