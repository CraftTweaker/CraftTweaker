package com.blamejared.crafttweaker.impl.script;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.impl.loot.modifier.LootModifierManagerReloadListener;
import com.blamejared.crafttweaker.impl.util.ClientUtil;
import com.blamejared.crafttweaker.platform.Services;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public final class ScriptReloadListenerAdapter implements IdentifiableResourceReloadListener {
    
    private final ScriptReloadListener listener;
    
    public ScriptReloadListenerAdapter(final ReloadableServerResources managerGetter) {
        
        this.listener = new ScriptReloadListener(managerGetter, this::giveFeedback);
    }
    
    @Override
    public ResourceLocation getFabricId() {
        
        return new ResourceLocation(CraftTweakerConstants.MOD_ID, "scripts");
    }
    
    @Override
    public CompletableFuture<Void> reload(final PreparationBarrier preparationBarrier, final ResourceManager resourceManager,
                                          final ProfilerFiller preparationsProfiler, final ProfilerFiller reloadProfiler,
                                          final Executor backgroundExecutor,
                                          final Executor gameExecutor) {
        
        return this.listener.reload(preparationBarrier, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor);
    }
    
    @Override
    public Collection<ResourceLocation> getFabricDependencies() {
        
        return Set.of(
                LootModifierManagerReloadListener.RELOAD_LISTENER_ID,
                ResourceReloadListenerKeys.ADVANCEMENTS,
                ResourceReloadListenerKeys.RECIPES,
                ResourceReloadListenerKeys.LOOT_TABLES,
                ResourceReloadListenerKeys.TAGS
        );
    }
    
    private void giveFeedback(final MutableComponent msg) {
        
        @SuppressWarnings("deprecation") // Fabric be like
        Object gameInstance = FabricLoader.getInstance().getGameInstance();
        Services.DISTRIBUTION.callOn(() -> () -> ClientUtil.giveFeedback(msg), () -> () -> {
            if(gameInstance instanceof MinecraftServer server) {
                server.getPlayerList().broadcastSystemMessage(msg, false);
                return true;
            }
            return false;
        }).ifPresent(sent -> {
            if(!sent) {
                CraftTweakerCommon.logger().info(msg.getString());
            }
        });
    }
    
}
