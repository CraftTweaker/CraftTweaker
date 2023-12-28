package com.blamejared.crafttweaker.impl.event;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTests;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.tags.TagManager;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mod.EventBusSubscriber(modid = CraftTweakerConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CTModEventHandler {
    
    @SubscribeEvent
    public static void onRegisterGameTests(RegisterGameTestsEvent event) {
        event.register(CraftTweakerGameTests.class);
    }
    
    /**
     * Creates a fake tag registry for mods using CraftTweaker in their own DataGen.
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void gatherData(GatherDataEvent event) {
        
        if(event.includeServer()) {
            List<TagManager.LoadResult<?>> loadResults = new ArrayList<>();
            
            for(RegistryDataLoader.RegistryData<?> data : RegistryDataLoader.WORLDGEN_REGISTRIES) {
                loadResults.add(new TagManager.LoadResult<>(data.key(), new HashMap<>()));
            }
            for(Registry<?> registry : BuiltInRegistries.REGISTRY) {
                loadResults.add(new TagManager.LoadResult<>(registry.key(), new HashMap<>()));
            }
            CraftTweakerTagRegistry.INSTANCE.bind(loadResults);
        }
    }
    
}
