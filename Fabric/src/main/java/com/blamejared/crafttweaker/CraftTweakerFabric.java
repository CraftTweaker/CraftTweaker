package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.event.CraftTweakerEvents;
import com.blamejared.crafttweaker.api.recipe.replacement.rule.DefaultExclusionReplacements;
import com.blamejared.crafttweaker.impl.util.WrappingBracketParser;
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
        
        CraftTweakerEvents.REGISTER_CUSTOM_BEP_EVENT.register((loader, regFun) -> {
            if(CraftTweakerConstants.MOD_ID.equals(loader)) {
                // Fire the old event for compatibility: this is only required for the CrT loader
                Services.EVENT.fireRegisterBEPEvent(new WrappingBracketParser(regFun));
            }
        });
        // Ensure that our BEPs are registered on every loader that explicitly fires the old event (which means it is
        // not updated to the new API)
        CraftTweakerEvents.REGISTER_BEP_EVENT.register(bep -> CraftTweakerCommon.registerCraftTweakerBracketHandlers(bep::register));
        
        CraftTweakerCommon.loadInitScripts();
    }
    
}
