/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.minecraft.player;

import minetweaker.minecraft.data.IData;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minecraft.player.IPlayer")
public interface IPlayer {
	@ZenGetter("name")
	public String getName();
	
	@ZenGetter("data")
	public IData getData();
	
	@ZenMethod
	public void update(IData data);
}
