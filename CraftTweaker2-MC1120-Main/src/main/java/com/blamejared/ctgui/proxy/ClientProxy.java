package com.blamejared.ctgui.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

    @Override
    public EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().player;
    }

}
