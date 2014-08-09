/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164;

import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 *
 * @author Stan
 */
public class ForgeEventHandler {
	@ForgeSubscribe
	public void onPlayerInteract(PlayerInteractEvent ev) {
		System.out.println("Player interact");
		
		minetweaker.api.event.PlayerInteractEvent event = new minetweaker.api.event.PlayerInteractEvent(
				MineTweakerMC.getIPlayer(ev.entityPlayer),
				MineTweakerMC.getDimension(ev.entityPlayer.getEntityWorld()),
				ev.x, ev.y, ev.z
		);
		
		MineTweakerImplementationAPI.events.publishPlayerInteract(event);
	}
}
