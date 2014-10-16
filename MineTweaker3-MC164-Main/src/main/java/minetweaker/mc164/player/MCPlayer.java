/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164.player;

import minetweaker.MineTweakerAPI;
import minetweaker.api.chat.IChatMessage;
import minetweaker.mc164.data.NBTConverter;
import minetweaker.api.data.IData;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.player.IPlayer;
import minetweaker.mc164.MineTweakerMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;

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
	public String getId() {
		return null;
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
		Object internal = message.getInternal();
		if (!(internal instanceof ChatMessageComponent)) {
			//throw new RuntimeException("Not a valid chat message: " + internal);
			MineTweakerAPI.logError("not a valid chat message");
			return;
		}
		player.sendChatToPlayer((ChatMessageComponent) internal);
	}

	@Override
	public void sendChat(String message) {
		player.sendChatToPlayer(ChatMessageComponent.createFromText(message));
	}

	@Override
	public int getHotbarSize() {
		return 9;
	}

	@Override
	public IItemStack getHotbarStack(int i) {
		return i < 0 || i >= 9 ? null : MineTweakerMC.getIItemStack(player.inventory.getStackInSlot(i));
	}

	@Override
	public int getInventorySize() {
		return player.inventory.getSizeInventory();
	}

	@Override
	public IItemStack getInventoryStack(int i) {
		return MineTweakerMC.getIItemStack(player.inventory.getStackInSlot(i));
	}

	@Override
	public IItemStack getCurrentItem() {
		return MineTweakerMC.getIItemStack(player.getCurrentEquippedItem());
	}

	@Override
	public boolean isCreative() {
		return player.capabilities.isCreativeMode;
	}

	@Override
	public boolean isAdventure() {
		return !player.capabilities.allowEdit;
	}

	@Override
	public void openBrowser(String url) {
		if (player instanceof EntityPlayerMP) {
			MineTweakerMod.INSTANCE.openBrowser(player.username, url);
		}
	}
	
	@Override
	public void copyToClipboard(String value) {
		if (player instanceof EntityPlayerMP) {
			MineTweakerMod.INSTANCE.copyToClipboard(player.username, value);
		}
	}
	
	@Override
	public void give(IItemStack stack) {
		player.inventory.addItemStackToInventory(MineTweakerMC.getItemStack(stack).copy());
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 59 * hash + (this.player != null ? this.player.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MCPlayer other = (MCPlayer) obj;
		if (this.player != other.player && (this.player == null || !this.player.equals(other.player))) {
			return false;
		}
		return true;
	}
}
