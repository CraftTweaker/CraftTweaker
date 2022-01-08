package com.blamejared.crafttweaker.impl.event;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CraftTweakerConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTModEventHandler {
    
    /**
     * By this time, forge tags have been registered, so we can use this.
     * Subscribed to at highest priority to allow other mods to call CrT methods that use tags from within that event.
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void handleTags(RegistryEvent.Register<Block> ignored) {
        
        CraftTweakerAPI.LOGGER.debug("Setting up Tag Managers");
        Services.PLATFORM.registerCustomTags();
        CraftTweakerAPI.LOGGER.debug("Finished setting up Tag Managers");
    }
    
}
