package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.event.type.CTRegisterBEPEvent;
import com.blamejared.crafttweaker.api.event.type.CTRegisterCustomBepEvent;
import com.blamejared.crafttweaker.impl.network.PacketHandler;
import com.blamejared.crafttweaker.impl.util.WrappingBracketParser;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CraftTweakerConstants.MOD_ID)
public class CraftTweakerForge {
    
    public CraftTweakerForge() {
        
        CraftTweakerCommon.init();
        
        PacketHandler.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::onCustomBracketParserRegistration);
        MinecraftForge.EVENT_BUS.addListener(this::onLegacyBracketParserRegistration);
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        
        CraftTweakerCommon.loadInitScripts();
    }
    
    private void onCustomBracketParserRegistration(final CTRegisterCustomBepEvent event) {
        
        if(CraftTweakerConstants.MOD_ID.equals(event.getCurrentLoader())) {
            // Fire the old event for compatibility: this is only required for the CrT loader
            Services.EVENT.fireRegisterBEPEvent(new WrappingBracketParser(event::registerBep));
        }
    }
    
    private void onLegacyBracketParserRegistration(final CTRegisterBEPEvent event) {
        // Ensure that our BEPs are registered on every loader that explicitly fires the old event (which means it is
        // not updated to the new API)
        CraftTweakerCommon.registerCraftTweakerBracketHandlers(event::registerBEP);
    }
    
}
