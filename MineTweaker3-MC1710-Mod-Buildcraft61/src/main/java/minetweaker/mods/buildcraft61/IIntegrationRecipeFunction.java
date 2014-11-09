/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.buildcraft61;

import minetweaker.api.item.IItemStack;

/**
 *
 * @author Stan
 */
public interface IIntegrationRecipeFunction {
	public IItemStack recipe(IItemStack output, IItemStack inputA, IItemStack inputB);
}
