/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.client;

import minetweaker.api.client.IClient;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.player.IPlayer;
import net.minecraft.client.Minecraft;

/**
 *
 * @author Stan
 */
public class MCClient implements IClient {
	private final IPlayer player;
	
	public MCClient() {
		player = MineTweakerMC.getIPlayer(Minecraft.getMinecraft().thePlayer);
	}

	@Override
	public IPlayer getPlayer() {
		return player;
	}
}
