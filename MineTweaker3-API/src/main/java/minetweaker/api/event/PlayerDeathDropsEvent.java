/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.event;

import java.util.List;
import minetweaker.api.damage.IDamageSource;
import minetweaker.api.entity.IEntityItem;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerDeathDropsEvent")
public class PlayerDeathDropsEvent {
	private final IPlayer player;
	private final List<IEntityItem> items;
	private final IDamageSource damageSource;

	public PlayerDeathDropsEvent(IPlayer player, List<IEntityItem> items, IDamageSource damageSource) {
		this.player = player;
		this.items = items;
		this.damageSource = damageSource;
	}

	@ZenGetter("player")
	public IPlayer getPlayer() {
		return player;
	}

	@ZenGetter("items")
	public List<IEntityItem> getItems() {
		return items;
	}

	@ZenGetter("damageSource")
	public IDamageSource getDamageSource() {
		return damageSource;
	}
}
