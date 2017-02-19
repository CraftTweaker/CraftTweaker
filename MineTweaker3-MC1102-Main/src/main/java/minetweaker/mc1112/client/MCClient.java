/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1112.client;

import minetweaker.api.client.IClient;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.player.IPlayer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.FMLClientHandler;

/**
 * @author Stan
 */
public class MCClient implements IClient{
    @Override
    public IPlayer getPlayer(){
        return MineTweakerMC.getIPlayer(Minecraft.getMinecraft().thePlayer);
    }

    @Override
    public String getLanguage(){
        return FMLClientHandler.instance().getCurrentLanguage();
    }
}
