/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.player;

import minetweaker.MineTweakerAPI;
import minetweaker.api.chat.IChatMessage;
import minetweaker.mc1710.data.NBTConverter;
import minetweaker.api.data.IData;
import minetweaker.api.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;

/**
 *
 * @author Stan
 */
public class MCPlayer implements IPlayer {
	private final EntityPlayer player;
	
	public MCPlayer(EntityPlayer player) {
		this.player = player;
	}
	
	public EntityPlayer getInternal() {
		return player;
	}

	@Override
	public String getName() {
		return player.getCommandSenderName();
	}

	@Override
	public IData getData() {
		return NBTConverter.from(player.getEntityData(), true);
	}
	
	@Override
	public void update(IData data) {
		NBTConverter.updateMap(player.getEntityData(), data);
	}

	@Override
	public void sendChat(IChatMessage message) {
		Object internal = message;
		if (!(internal instanceof IChatComponent)) {
			MineTweakerAPI.logger.logError("not a valid chat message");
			return;
		}
		player.addChatMessage((IChatComponent) internal);
	}
}
