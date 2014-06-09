/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.minecraft.oredict;

import minetweaker.minecraft.item.IIngredient;
import minetweaker.minecraft.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minecraft.oreDict.IOreDictEntry")
public interface IOreDictEntry extends IIngredient {
	@ZenMethod
	public void add(IItemStack item);
	
	@ZenMethod
	public void addAll(IOreDictEntry entry);
	
	@ZenMethod
	public void remove(IItemStack item);
	
	@ZenMethod
	public boolean contains(IItemStack item);
	
	@ZenMethod
	public void mirror(IOreDictEntry other);
}
