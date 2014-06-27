/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.event;

import minetweaker.util.IEventHandler;
import stanhebben.zenscript.annotations.ZenClass;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.event.IPlayerUseHoeEventHandler")
public interface IPlayerUseHoeEventHandler extends IEventHandler<PlayerUseHoeEvent> {
	@Override
	public void handle(PlayerUseHoeEvent event);
}
