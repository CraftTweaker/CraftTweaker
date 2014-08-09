/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 *
 * @author Stan
 */
public class ForgeEventHandler {
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent ev) {
		minetweaker.api.event.PlayerInteractEvent event = new minetweaker.api.event.PlayerInteractEvent(
				MineTweakerMC.getIPlayer(ev.entityPlayer),
				MineTweakerMC.getDimension(ev.world),
				ev.x, ev.y, ev.z
		);
		
		MineTweakerImplementationAPI.events.publishPlayerInteract(event);
	}
}
