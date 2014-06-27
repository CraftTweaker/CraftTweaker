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
import minetweaker.mc1710.MineTweakerMod;
import minetweaker.runtime.providers.ScriptProviderMemory;

/**
 *
 * @author Stan
 */
public class MineTweakerLoadScriptsHandler implements IMessageHandler<MineTweakerLoadScriptsPacket, IMessage> {
	@Override
	public IMessage onMessage(MineTweakerLoadScriptsPacket message, MessageContext ctx) {
		if (!MineTweakerMod.INSTANCE.iAmServer()) {
			/*IScriptProvider scriptProvider = new ScriptProviderMemory(message.getData());
			Iterator<IScriptIterator> scripts = scriptProvider.getScripts();
			while (scripts.hasNext()) {
				IScriptIterator myGroup = scripts.next();
				System.out.println("Scripts: " + myGroup.getGroupName());
				
				while (myGroup.next()) {
					System.out.println("Script name: " + myGroup.getName());
				}
			}*/
			MineTweakerAPI.tweaker.setScriptProvider(new ScriptProviderMemory(message.getData()));
			MineTweakerAPI.tweaker.rollback();
			MineTweakerAPI.tweaker.load();
		}
		
		return null;
	}
}
