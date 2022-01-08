package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.impl.network.PacketHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CraftTweakerConstants.MOD_ID)
public class CraftTweakerForge {
    
    public CraftTweakerForge() {
        
        CraftTweakerCommon.init();
        
        PacketHandler.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        
        CraftTweakerCommon.loadInitScripts();
    }
    
}
