package com.blamejared.crafttweaker.impl.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;

public class ClientUtil {
    
    public static boolean giveFeedback(MutableComponent msg) {
        
        Object gameInstance = FabricLoader.getInstance().getGameInstance();
        if(gameInstance instanceof Minecraft mc && mc.player != null) {
            mc.player.sendSystemMessage(msg);
            return true;
        }
        return false;
    }
    
}
