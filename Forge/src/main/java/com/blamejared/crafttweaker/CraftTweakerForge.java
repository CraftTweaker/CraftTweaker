package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.impl.network.PacketHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CraftTweakerConstants.MOD_ID)
public class CraftTweakerForge {
    
    public CraftTweakerForge() {
        
        CraftTweakerCommon.init();
        CraftTweakerCommon.getPluginManager().loadPlugins();
        
        PacketHandler.init();
        
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        
        CraftTweakerCommon.getPluginManager().broadcastSetupEnd(); // TODO("Another place?")
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        
        CraftTweakerCommon.loadInitScripts();
        event.enqueueWork(CraftTweakerCommon::registerCommandArguments);
    }
    
}
