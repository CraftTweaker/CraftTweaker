/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.entity;

import minetweaker.api.item.IItemStack;
import minetweaker.util.IntegerRange;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.*;

import java.util.*;


/**
 * @author Stan Hebben
 */
@ZenClass("minetweaker.entity.IEntityDefinition")
public interface IEntityDefinition {
	
	@ZenGetter("id")
	String getId();
	
	@ZenGetter("name")
	String getName();
	
	@ZenMethod
	void addDrop(IItemStack stack, @Optional int min, @Optional int max);
	
	@ZenMethod
	void addPlayerOnlyDrop(IItemStack stack, @Optional int min, @Optional int max);
	
	@ZenMethod
	void removeDrop(IItemStack stack);
	
	Map<IItemStack, IntegerRange> getDropsToAdd();
	
	Map<IItemStack, IntegerRange> getDropsToAddPlayerOnly();
	
	List<IItemStack> getDropsToRemove();
	
}
