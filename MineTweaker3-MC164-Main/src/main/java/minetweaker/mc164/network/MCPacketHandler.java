/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import minetweaker.MineTweakerAPI;
import minetweaker.runtime.providers.ScriptProviderMemory;

public class MCPacketHandler implements IPacketHandler {
	public static final String CHANNEL_SERVERSCRIPT = "MTServerScript";
	
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		// catches some special cases caused by unknown reasons
		if (packet == null || packet.channel == null || packet.data == null) return;
		
		if (packet.channel.equals(CHANNEL_SERVERSCRIPT)) {
			MineTweakerAPI.tweaker.setScriptProvider(new ScriptProviderMemory(packet.data));
			MineTweakerAPI.tweaker.rollback();
			MineTweakerAPI.tweaker.load();
		}
	}
}
