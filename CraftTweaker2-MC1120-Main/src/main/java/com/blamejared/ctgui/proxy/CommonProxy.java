package com.blamejared.ctgui.proxy;

import com.blamejared.ctgui.client.GuiHandler;
import com.blamejared.ctgui.events.CommonEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
    
    public void registerGuis() {
        new GuiHandler();
    }
    
    public void registerEvents() {
        MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
    }
    
    public EntityPlayer getPlayer() {
        return null;
    }
    
}
