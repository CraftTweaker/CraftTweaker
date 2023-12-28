package com.blamejared.crafttweaker.platform.client;

import com.blamejared.crafttweaker.platform.services.IClientHelper;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class ForgeClientHelper implements IClientHelper {
    
    @Override
    public boolean isKeyDown(KeyMapping keyBinding) {
        
        InputConstants.Key key = keyBinding.getKey();
        int keyCode = key.getValue();
        if(keyCode != InputConstants.UNKNOWN.getValue()) {
            long windowHandle = Minecraft.getInstance().getWindow().getWindow();
            try {
                if(key.getType() == InputConstants.Type.KEYSYM) {
                    return InputConstants.isKeyDown(windowHandle, keyCode);
                } else if(key.getType() == InputConstants.Type.MOUSE) {
                    return GLFW.glfwGetMouseButton(windowHandle, keyCode) == GLFW.GLFW_PRESS;
                }
            } catch(Exception ignored) {
            }
        }
        return false;
    }
    
    @Override
    public boolean isKeyDownExtra(KeyMapping keyBinding) {
        
        if(keyBinding.isDown()) {
            return true;
        }
        if(keyBinding.getKeyModifier().isActive(KeyConflictContext.GUI)) {
            //Manually check in case keyBinding#pressed just never got a chance to be updated
            return isKeyDown(keyBinding);
        }
        //If we failed, due to us being a key modifier as our key, check the old way
        return KeyModifier.isKeyCodeModifier(keyBinding.getKey()) && isKeyDown(keyBinding);
    }
    
}
