package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.impl.network.PacketHandler;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Supplier;

@Mod(CraftTweakerConstants.MOD_ID)
public class CraftTweakerForge {
    
    public CraftTweakerForge() {
        
        CraftTweakerCommon.init();
        
        PacketHandler.init();
        
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(EventPriority.LOW, this::onConstruct);
        bus.addListener(this::setup);
    }
    
    private void onConstruct(final FMLConstructModEvent event) {
        // haha fuck you
        event.enqueueWork(() -> CraftTweakerCommon.handlePlugins(
                InterModComms.getMessages(CraftTweakerConstants.MOD_ID)
                        .filter(it -> ICraftTweakerPlugin.REGISTRATION_CHANNEL_ID.equals(it.method()))
                        .map(InterModComms.IMCMessage::messageSupplier)
                        .map(Supplier::get)
                        .map(CraftTweakerForge::checkAndCast)
        ));
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        
        CraftTweakerCommon.loadInitScripts();
        event.enqueueWork(CraftTweakerCommon::registerCommandArguments);
    }
    
    @SuppressWarnings("unchecked")
    private static Class<? extends ICraftTweakerPlugin> checkAndCast(final Object o) {
        
        if(!(o instanceof final Class<?> clazz)) {
            throw new IllegalArgumentException("Invalid IMC message for method " + ICraftTweakerPlugin.REGISTRATION_CHANNEL_ID + ": supplied object must be a class");
        }
        if(!ICraftTweakerPlugin.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Invalid IMC message for method " + ICraftTweakerPlugin.REGISTRATION_CHANNEL_ID + ": invalid class instance");
        }
        return (Class<? extends ICraftTweakerPlugin>) clazz;
    }
    
}
