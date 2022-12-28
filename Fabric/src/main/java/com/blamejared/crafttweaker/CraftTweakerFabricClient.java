package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.util.sequence.SequenceManager;
import com.blamejared.crafttweaker.api.util.sequence.SequenceType;
import com.blamejared.crafttweaker.impl.logging.CraftTweakerLog4jEditor;
import com.blamejared.crafttweaker.impl.network.message.ClientMessages;
import com.blamejared.crafttweaker.platform.Services;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class CraftTweakerFabricClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        
        ItemTooltipCallback.EVENT.register(Services.CLIENT::applyTooltips);
        
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> CraftTweakerLog4jEditor.addPlayer(client.player));
        
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> CraftTweakerLog4jEditor.removePlayer(client.player));
        
        for(ClientMessages msg : ClientMessages.values()) {
            ClientPlayNetworking.registerGlobalReceiver(msg.getId(), (client, handler, buf, responseSender) -> msg.getMessageFactory()
                    .apply(buf)
                    .handle());
        }
        
        ClientTickEvents.START_WORLD_TICK.register(world -> SequenceManager.tick(SequenceType.CLIENT_THREAD_LEVEL));
    }
    
}
