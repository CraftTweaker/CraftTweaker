/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.vanilla;

import java.util.List;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.WeightedItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("vanilla.ISeedRegistry")
public interface ISeedRegistry {
	@ZenMethod
	public void addSeed(WeightedItemStack item);

	@ZenMethod
	public void removeSeed(IIngredient item);

	@ZenGetter("seeds")
	public List<WeightedItemStack> getSeeds();
}
