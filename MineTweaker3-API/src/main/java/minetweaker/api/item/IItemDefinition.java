/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.item;

import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.item.IItemDefinition")
public interface IItemDefinition {
	@ZenGetter("id")
	public String getId();
	
	@ZenGetter("name")
	public String getName();
	
	@ZenMethod
	public IItemStack makeStack(@Optional int meta);
}
