/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.player;

import minetweaker.api.chat.IChatMessage;
import minetweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.player.IPlayer")
public interface IPlayer {
	@ZenGetter("name")
	public String getName();
	
	@ZenGetter("data")
	public IData getData();
	
	@ZenMethod
	public void update(IData data);
	
	@ZenMethod
	public void sendChat(IChatMessage message);
}
