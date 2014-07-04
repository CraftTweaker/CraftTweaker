package minetweaker.mc1710;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.event.PlayerCraftedEvent;
import minetweaker.api.event.PlayerLoggedInEvent;
import minetweaker.api.event.PlayerLoggedOutEvent;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.mc1710.network.MineTweakerLoadScriptsPacket;
import minetweaker.mc1710.recipes.MCCraftingInventory;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 *
 * @author Stan
 */
public class FMLEventHandler {
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent ev) {
		if (ev.player instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) ev.player;
			MineTweakerMod.NETWORK.sendTo(
					new MineTweakerLoadScriptsPacket(MineTweakerAPI.tweaker.getScriptData()),
					player);
		}
		
		MineTweakerImplementationAPI.events.publishPlayerLoggedIn(new PlayerLoggedInEvent(MineTweakerMC.getIPlayer(ev.player)));
	}
	
	@SubscribeEvent
	public void onPlayerItemCrafted(PlayerEvent.ItemCraftedEvent ev) {
		if (MineTweakerMod.INSTANCE.recipes.hasTransformerRecipes()) {
			MineTweakerMod.INSTANCE.recipes.applyTransformations(MCCraftingInventory.get(ev.craftMatrix, ev.player));
		}
		
		if (MineTweakerImplementationAPI.events.hasPlayerCrafted()) {
			MineTweakerImplementationAPI.events.publishPlayerCrafted(new PlayerCraftedEvent(
					MineTweakerMC.getIPlayer(ev.player),
					MineTweakerMC.getIItemStack(ev.crafting),
					MCCraftingInventory.get(ev.craftMatrix, ev.player)));
		}
	}
	
	@SubscribeEvent
	public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent ev) {
		MineTweakerImplementationAPI.events.publishPlayerLoggedOut(new PlayerLoggedOutEvent(MineTweakerMC.getIPlayer(ev.player)));
	}
}
