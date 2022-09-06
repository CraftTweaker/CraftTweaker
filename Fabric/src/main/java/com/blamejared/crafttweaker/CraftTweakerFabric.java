package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.util.sequence.SequenceManager;
import com.blamejared.crafttweaker.api.util.sequence.SequenceType;
import com.blamejared.crafttweaker.platform.Services;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionResult;

public class CraftTweakerFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        CraftTweakerCommon.init();
        CraftTweakerCommon.getPluginManager().loadPlugins();
        
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            CraftTweakerCommon.registerCommands(dispatcher, environment);
        });
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if(Services.EVENT.onBlockInteract(player, hand, hitResult)) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });
        
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if(Services.EVENT.onEntityInteract(player, hand, entity)) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });
        
        ServerTickEvents.START_WORLD_TICK.register(world -> SequenceManager.tick(SequenceType.SERVER_THREAD_LEVEL));
        
        CraftTweakerCommon.getPluginManager().broadcastSetupEnd(); // TODO("Another place?")
        
        CraftTweakerCommon.loadInitScripts();
        
    }
    
}
