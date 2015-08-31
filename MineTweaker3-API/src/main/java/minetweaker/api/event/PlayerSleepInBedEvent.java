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
@ZenClass("minetweaker.event.PlayerSleepInBedEvent")
public class PlayerSleepInBedEvent {
	private final IPlayer player;
	private final int x;
	private final int y;
	private final int z;

	public PlayerSleepInBedEvent(IPlayer player, int x, int y, int z) {
		this.player = player;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@ZenGetter("player")
	public IPlayer getPlayer() {
		return player;
	}

	@ZenGetter("x")
	public int getX() {
		return x;
	}

	@ZenGetter("y")
	public int getY() {
		return y;
	}

	@ZenGetter("z")
	public int getZ() {
		return z;
	}
}
