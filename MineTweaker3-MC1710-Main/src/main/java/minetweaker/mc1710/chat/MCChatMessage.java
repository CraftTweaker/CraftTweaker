/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.chat;

import minetweaker.api.chat.IChatMessage;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

/**
 *
 * @author Stan
 */
public class MCChatMessage implements IChatMessage {
	private final IChatComponent data;

	public MCChatMessage(String message) {
		data = new ChatComponentText(message);
	}

	public MCChatMessage(IChatComponent data) {
		this.data = data;
	}

	@Override
	public IChatMessage add(IChatMessage other) {
		return new MCChatMessage(data.appendSibling((IChatComponent) other.getInternal()));
	}

	@Override
	public Object getInternal() {
		return data;
	}
}
