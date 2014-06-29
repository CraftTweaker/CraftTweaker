/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;
import minetweaker.MineTweakerAPI;
import minetweaker.mc164.MineTweakerMod;

public class MCConnectionHandler implements IConnectionHandler {
	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler,
			INetworkManager manager) {
		System.out.println("Player logged in: " + netHandler.getPlayer().username);
		
		manager.addToSendQueue(new Packet250CustomPayload(
				MCPacketHandler.CHANNEL_SERVERSCRIPT,
				MineTweakerAPI.tweaker.getScriptData()));
		MineTweakerMod.INSTANCE.onPlayerLoggedIn(manager, netHandler.getPlayer());
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler,
			INetworkManager manager) {
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server,
			int port, INetworkManager manager) {
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler,
			MinecraftServer server, INetworkManager manager) {
	}

	@Override
	public void connectionClosed(INetworkManager manager) {
		MineTweakerMod.INSTANCE.onPlayerLoggedOut(manager);
	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler,
			INetworkManager manager, Packet1Login login) {
	}
}
