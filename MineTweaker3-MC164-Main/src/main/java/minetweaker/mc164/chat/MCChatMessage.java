/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164.chat;

import minetweaker.api.chat.IChatMessage;
import net.minecraft.util.ChatMessageComponent;

/**
 *
 * @author Stan
 */
public class MCChatMessage implements IChatMessage {
	private final ChatMessageComponent data;
	
	public MCChatMessage(String message) {
		data = ChatMessageComponent.createFromText(message);
	}
	
	public MCChatMessage(ChatMessageComponent data) {
		this.data = data;
	}

	@Override
	public IChatMessage add(IChatMessage other) {
		return new MCChatMessage(data.appendComponent((ChatMessageComponent) other.getInternal()));
	}

	@Override
	public Object getInternal() {
		return data;
	}
}
