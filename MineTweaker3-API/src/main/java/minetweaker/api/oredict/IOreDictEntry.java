/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.oredict;

import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.oredict.IOreDictEntry")
public interface IOreDictEntry extends IIngredient {
	@ZenGetter("name")
    String getName();

	@ZenGetter("empty")
    boolean isEmpty();

	@ZenGetter("firstItem")
    IItemStack getFirstItem();

	@ZenMethod
    void add(IItemStack item);

	@ZenMethod
    void addItems(IItemStack[] items);

	@ZenMethod
    void addAll(IOreDictEntry entry);

	@ZenMethod
    void remove(IItemStack item);

    @ZenMethod
    void removeItems(IItemStack[] items);


    @ZenOperator(OperatorType.CONTAINS)
    boolean contains(IItemStack item);

	@ZenMethod
    void mirror(IOreDictEntry other);
}
