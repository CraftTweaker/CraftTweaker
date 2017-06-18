package com.blamejared.ctgui.proxy;

import com.blamejared.ctgui.client.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy {

    public void registerGuis() {
        new GuiHandler();
    }


    public EntityPlayer getPlayer() {
        return null;
    }

}
