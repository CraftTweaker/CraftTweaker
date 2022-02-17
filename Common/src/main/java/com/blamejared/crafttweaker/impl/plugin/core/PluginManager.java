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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

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
            final String targetId = Objects.requireNonNull(clazz.getAnnotation(CraftTweakerPlugin.class)).value();
            if(!targetId.contains(":")) {
                throw new ResourceLocationException("Not within a namespace");
            }
            if(targetId.startsWith("minecraft:")) {
                throw new ResourceLocationException("Illegal namespace 'minecraft'");
            }
            id = new ResourceLocation(targetId);
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
        final Map<String, IScriptLoader> loaders = LoaderRegistrationHandler.gather(this.onEach(ICraftTweakerPlugin::registerLoaders));
        
        pluginRegistryAccess.registerLoaders(loaders.values());
        pluginRegistryAccess.registerLoadSources(LoadSourceRegistrationHandler.gather(this.onEach(ICraftTweakerPlugin::registerLoadSource)));
        
        final JavaNativeIntegrationRegistrationHandler handler = JavaNativeIntegrationRegistrationHandler.of(this.onEach(ICraftTweakerPlugin::manageJavaNativeIntegration));
        this.manageZenRegistration(pluginRegistryAccess, handler, name -> {
            if(loaders.containsKey(name)) {
                return loaders.get(name);
            }
            throw new IllegalArgumentException("Unknown loader '" + name + "' queried");
        });
    }
    
    public void broadcastEnd() {
        
        throw new UnsupportedOperationException("Requires implementation");
    }
    
    private <T> Consumer<T> onEach(final BiConsumer<ICraftTweakerPlugin, T> consumer) {
        
        return handler -> this.plugins.forEach(plugin -> {
            try {
                consumer.accept(plugin, handler);
            } catch(final Exception e) {
                throw new IllegalStateException("Plugin " + plugin.id() + " failed to initialize", e);
            }
        });
    }
    
    private void manageZenRegistration(final IPluginRegistryAccess access, final JavaNativeIntegrationRegistrationHandler handler, final Function<String, IScriptLoader> loaderGetter) {
        
        handler.preprocessors().forEach(access::registerPreprocessor);
        handler.nativeClassRequests()
                .forEach(r -> access.registerNativeType(loaderGetter.apply(r.loader()), r.info()));
        handler.zenClassRequests()
                .object2BooleanEntrySet()
                .stream()
                .sorted(Comparator.comparing(it -> it.getKey().info().kind()))
                .forEach(entry -> access.registerZenType(loaderGetter.apply(entry.getKey().loader()), entry.getKey()
                        .clazz(), entry.getKey().info(), entry.getBooleanValue()));
    }
    
}
