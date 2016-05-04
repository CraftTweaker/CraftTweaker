/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.mc1710.client.MCClient;
import minetweaker.runtime.providers.ScriptProviderMemory;

/**
 *
 * @author Stan
 */
public class MineTweakerLoadScriptsHandler implements IMessageHandler<MineTweakerLoadScriptsPacket, IMessage> {
	@Override
	public IMessage onMessage(MineTweakerLoadScriptsPacket message, MessageContext ctx) {
		if (MineTweakerAPI.server == null) {
			MineTweakerAPI.client = new MCClient();

			MineTweakerImplementationAPI.setScriptProvider(new ScriptProviderMemory(message.getData()));
			MineTweakerImplementationAPI.reload();
		}

		return null;
	}
}
