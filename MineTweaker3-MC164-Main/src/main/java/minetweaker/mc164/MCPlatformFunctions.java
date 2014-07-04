/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164;

import cpw.mods.fml.common.network.PacketDispatcher;
import minetweaker.IPlatformFunctions;
import minetweaker.MineTweakerAPI;
import minetweaker.api.chat.IChatMessage;
import minetweaker.api.item.IItemDefinition;
import minetweaker.mc164.chat.MCChatMessage;
import minetweaker.mc164.item.MCItemDefinition;
import minetweaker.mc164.network.MCPacketHandler;
import net.minecraft.item.Item;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

/**
 *
 * @author Stan
 */
public class MCPlatformFunctions implements IPlatformFunctions {
	public static final MCPlatformFunctions INSTANCE = new MCPlatformFunctions();
	
	private MCPlatformFunctions() {}
	
	@Override
	public IChatMessage getMessage(String message) {
		return new MCChatMessage(message);
	}

	@Override
	public void distributeScripts(byte[] data) {
		Packet packet = new Packet250CustomPayload(
				MCPacketHandler.CHANNEL_SERVERSCRIPT,
				MineTweakerAPI.tweaker.getScriptData());
		PacketDispatcher.sendPacketToAllPlayers(packet);
	}

	@Override
	public IItemDefinition getItemDefinition(int id) {
		if (id < 0 || id >= Item.itemsList.length) return null;
		Item item = Item.itemsList[id];
		if (item == null) return null;
		return new MCItemDefinition(item);
	}
}
