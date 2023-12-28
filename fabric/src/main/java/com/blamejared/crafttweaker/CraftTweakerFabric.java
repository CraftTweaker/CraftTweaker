package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.sequence.SequenceManager;
import com.blamejared.crafttweaker.api.util.sequence.SequenceType;
import com.blamejared.crafttweaker.impl.loot.ILootTableIdHolder;
import com.blamejared.crafttweaker.mixin.common.access.loot.AccessLootDataManager;
import com.blamejared.crafttweaker.platform.Services;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Map;

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
        
        LootTableEvents.ALL_LOADED.register((res, loot) -> {
            // ****************** IMPORTANT *********************
            // Code copied from common's MixinLootDataManager because Fabric API decided to just fuck up this part of
            // the code
            // Any changes made here should be made there too
            ((AccessLootDataManager) loot).crafttweaker$elements()
                    .entrySet()
                    .stream()
                    .filter(it -> LootDataType.TABLE.equals(it.getKey().type()))
                    .map(it -> Map.entry(it.getKey().location(), GenericUtil.<LootTable>uncheck(it.getValue())))
                    .forEach(entry -> GenericUtil.<ILootTableIdHolder.Mutable>uncheck(entry.getValue()).crafttweaker$tableId(entry.getKey()));
        });
        
        CraftTweakerCommon.getPluginManager().broadcastSetupEnd(); // TODO("Another place?")
        
        CraftTweakerCommon.loadInitScripts();
        
    }
    
}
