package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.event.CraftTweakerEvents;
import com.blamejared.crafttweaker.api.recipe.replacement.rule.DefaultExclusionReplacements;
import com.blamejared.crafttweaker.platform.Services;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.commands.Commands;
import net.minecraft.world.InteractionResult;

public class CraftTweakerFabric implements ModInitializer {
    
    
    @Override
    public void onInitialize() {
        
        CraftTweakerCommon.init();
        CraftTweakerCommon.registerCommandArguments();
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            CraftTweakerCommon.registerCommands(dispatcher, dedicated ? Commands.CommandSelection.DEDICATED : Commands.CommandSelection.INTEGRATED);
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
        
        CraftTweakerEvents.GATHER_REPLACEMENT_EXCLUSION_EVENT.register(DefaultExclusionReplacements::handleDefaultExclusions);
        
        Services.PLATFORM.registerCustomTags();
        
        CraftTweakerCommon.loadInitScripts();
    }
    
}
