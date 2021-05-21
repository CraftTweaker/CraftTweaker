package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IAction;
import net.minecraft.client.Minecraft;

public class ClientHelper {
    
    /**
     * Helper method for DistExecutor.
     *
     * See {@link IAction#shouldApplySingletons()}
     *
     * @return true if in single player and on the server thread, or if connected to a dedicated server.
     */
    
    public static boolean shouldApplyServerActionOnClient() {
        if(Minecraft.getInstance().isSingleplayer()) {
            return CraftTweakerAPI.isServer();
        }
        return true; // Action needs to apply on the client when connected to a server
    }
    
}
