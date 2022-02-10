package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@SuppressWarnings("ClassCanBeRecord")
public final class PluginManager {
    
    private final List<ICraftTweakerPlugin> plugins;
    
    private PluginManager(final List<ICraftTweakerPlugin> plugins) {
        
        this.plugins = List.copyOf(plugins);
    }
    
    public static PluginManager of(final Stream<Class<? extends ICraftTweakerPlugin>> stream) {
        
        return new PluginManager(discoverPlugins(stream));
    }
    
    private static List<ICraftTweakerPlugin> discoverPlugins(final Stream<Class<? extends ICraftTweakerPlugin>> stream) {
        
        return stream.map(PluginManager::initPlugin)
                .filter(Objects::nonNull)
                .toList();
    }
    
    private static ICraftTweakerPlugin initPlugin(final Class<? extends ICraftTweakerPlugin> clazz) {
        
        try {
            final ICraftTweakerPlugin plugin = clazz.getConstructor().newInstance();
            CraftTweakerAPI.LOGGER.info("Successfully identified and loaded plugin {}", plugin.id());
            return plugin;
        } catch(final InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            CraftTweakerAPI.LOGGER.error(() -> "Unable to load plugin class '" + clazz.getName() + "' due to an error", e);
            return null;
        }
    }
    
    public void loadPlugins() {
        
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public void broadcastEnd() {
        
        // TODO("")
    }
    
}
