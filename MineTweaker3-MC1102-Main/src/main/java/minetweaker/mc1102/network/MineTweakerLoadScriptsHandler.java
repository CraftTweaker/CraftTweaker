/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1102.network;

import minetweaker.*;
import minetweaker.mc1102.client.MCClient;
import minetweaker.runtime.providers.ScriptProviderMemory;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.*;

/**
 * @author Stan
 */
public class MineTweakerLoadScriptsHandler implements IMessageHandler<MineTweakerLoadScriptsPacket, IMessage> {
	
	@Override
	public IMessage onMessage(MineTweakerLoadScriptsPacket message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> handle(message, ctx));
		return null;
	}
	
	private void handle(MineTweakerLoadScriptsPacket message, MessageContext ctx) {
		if(MineTweakerAPI.server == null) {
			MineTweakerAPI.client = new MCClient();
			
			MineTweakerImplementationAPI.setScriptProvider(new ScriptProviderMemory(message.getData()));
			MineTweakerImplementationAPI.reload();
		}
	}
}
