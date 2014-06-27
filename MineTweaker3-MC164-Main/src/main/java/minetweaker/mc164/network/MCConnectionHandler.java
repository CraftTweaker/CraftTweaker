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

public class MCConnectionHandler implements IConnectionHandler {
	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler,
			INetworkManager manager) {
		/*String[] admins = MineTweaker.instance.getAdmins();
		boolean canSendErrors = false;
		if (admins.length == 0) {
			canSendErrors = true;
		} else {
			for (String s : admins) {
				if (s.equals(netHandler.getPlayer().getEntityName())) canSendErrors = true;
			}
		}
		if (canSendErrors) {
			for (String s : MineTweaker.instance.getErrorMessages()) {
				//#ifdef MC152
				//+netHandler.getPlayer().sendChatToPlayer(s);
				//#else
				netHandler.getPlayer().sendChatToPlayer(ChatMessageComponent.createFromText(s));
				//#endif
			}
			MineTweaker.instance.onAdminLogin(manager, netHandler);
		}*/
		manager.addToSendQueue(new Packet250CustomPayload(
				MCPacketHandler.CHANNEL_SERVERSCRIPT,
				MineTweakerAPI.tweaker.getScriptData()));
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
		//MineTweaker.instance.onLogout(manager);
	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler,
			INetworkManager manager, Packet1Login login) {
	}
}
