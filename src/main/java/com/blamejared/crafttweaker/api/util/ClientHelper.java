package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IAction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

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
    
    
    /**
     * Safely checks if a key is down.
     *
     * Thanks to pupnewfster and Mekanism for the code!
     * https://github.com/mekanism/Mekanism/blob/v10.1/src/main/java/mekanism/client/key/MekKeyHandler.java#L26-L41
     *
     * @param keyBinding The key to check
     *
     * @return True if the key is down. False otherwise.
     */
    public static boolean isKeyDown(KeyBinding keyBinding) {
        
        InputMappings.Input key = keyBinding.getKey();
        int keyCode = key.getKeyCode();
        if(keyCode != InputMappings.INPUT_INVALID.getKeyCode()) {
            long windowHandle = Minecraft.getInstance().getMainWindow().getHandle();
            try {
                if(key.getType() == InputMappings.Type.KEYSYM) {
                    return InputMappings.isKeyDown(windowHandle, keyCode);
                } else if(key.getType() == InputMappings.Type.MOUSE) {
                    return GLFW.glfwGetMouseButton(windowHandle, keyCode) == GLFW.GLFW_PRESS;
                }
            } catch(Exception ignored) {
            }
        }
        return false;
    }
    
    /**
     * Safely checks if a key is down including any modifiers.
     *
     * Thanks to pupnewfster and Mekanism for the code!
     * https://github.com/mekanism/Mekanism/blob/v10.1/src/main/java/mekanism/client/key/MekKeyHandler.java#L14-L24
     *
     * @param keyBinding The key to check
     *
     * @return True if the key is down. False otherwise.
     */
    public static boolean getIsKeyPressed(KeyBinding keyBinding) {
        
        if (keyBinding.isKeyDown()) {
            return true;
        }
        if (keyBinding.getKeyModifier().isActive(KeyConflictContext.GUI)) {
            //Manually check in case keyBinding#pressed just never got a chance to be updated
            return isKeyDown(keyBinding);
        }
        //If we failed, due to us being a key modifier as our key, check the old way
        return KeyModifier.isKeyCodeModifier(keyBinding.getKey()) && isKeyDown(keyBinding);
    }
    
}
