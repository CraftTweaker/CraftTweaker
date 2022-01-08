package com.blamejared.crafttweaker.platform.client;

import com.blamejared.crafttweaker.mixin.client.access.AccessKeyMapping;
import com.blamejared.crafttweaker.platform.services.IClientHelper;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class FabricClientHelper implements IClientHelper {
    
    @Override
    public boolean isKeyDown(KeyMapping keyBinding) {
        
        InputConstants.Key key = ((AccessKeyMapping) keyBinding).getKey();
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
        
        return isKeyDown(keyBinding);
    }
    
}
