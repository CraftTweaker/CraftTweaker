/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.event;

import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerLoggedOutEvent")
public class PlayerLoggedOutEvent {
	private final IPlayer player;

	public PlayerLoggedOutEvent(IPlayer player) {
		this.player = player;
	}

	@ZenGetter("player")
	public IPlayer getPlayer() {
		return player;
	}
}
