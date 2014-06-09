/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.minecraft.recipes;

import java.util.Map;
import minetweaker.minecraft.item.IItemStack;

/**
 *
 * @author Stan
 */
public interface IRecipeFunction {
	public IItemStack process(IItemStack output, Map<String, IItemStack> inputs);
}
