/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.expand;

import minetweaker.api.item.IItemStack;
import minetweaker.api.item.WeightedItemStack;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

/**
 *
 * @author Stan
 */
@ZenExpansion("minetweaker.item.IItemStack")
public class ExpandItemStack {
	@ZenCaster
	public static WeightedItemStack asWeightedItemStack(IItemStack value) {
		return new WeightedItemStack(value, 1.0f);
	}
}
