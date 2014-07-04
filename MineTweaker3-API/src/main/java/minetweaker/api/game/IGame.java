/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.game;

import java.util.List;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.liquid.ILiquidDefinition;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.game.IGame")
public interface IGame {
	@ZenGetter("items")
	public List<IItemDefinition> getItems();
	
	@ZenGetter("liquids")
	public List<ILiquidDefinition> getLiquids();
}
