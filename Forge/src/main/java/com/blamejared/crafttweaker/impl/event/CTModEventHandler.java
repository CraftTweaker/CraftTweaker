package com.blamejared.crafttweaker.impl.event;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.tags.TagManager;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = CraftTweakerConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTModEventHandler {
    
    /**
     * Creates a fake tag registry for mods using CraftTweaker in their own DataGen.
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void gatherData(GatherDataEvent event) {
        
        if(event.includeServer()) {
            List<TagManager.LoadResult<?>> loadResults = new ArrayList<>();
            for(RegistryAccess.RegistryData<?> registry : RegistryAccess.knownRegistries()) {
                loadResults.add(new TagManager.LoadResult<>(registry.key(), Map.of()));
            }
            for(Registry<?> registry : Registry.REGISTRY) {
                loadResults.add(new TagManager.LoadResult<>(registry.key(), Map.of()));
            }
            CraftTweakerTagRegistry.INSTANCE.bind(loadResults);
        }
    }
    
}
