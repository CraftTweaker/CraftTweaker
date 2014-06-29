/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.oredict;

import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.oredict.IOreDictEntry")
public interface IOreDictEntry extends IIngredient {
	@ZenGetter("empty")
	public boolean isEmpty();
	
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
