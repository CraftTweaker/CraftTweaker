/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.entity;

import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 *
 * @author Stan
 */
public interface IEntityItem extends IEntity {
	@ZenGetter("item")
	public IItemStack getItem();
}
