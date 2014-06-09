/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.minecraft.item;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * An item transformer modifies an item stack. Used to apply damage, grab multiple
 * items, change NBT tags, replace the item with something else - or whatever
 * else you'd want to happen to the input stack.
 * 
 * @author Stan Hebben
 */
@ZenClass("minetweaker.IItemTransformer")
public interface IItemTransformer {
	/**
	 * Transforms the specified item. May modify this item (and return it)
	 * or return an entirely new one.
	 * 
	 * @param item input item
	 * @return output item
	 */
	@ZenMethod
	public IItemStack transform(IItemStack item);
}
