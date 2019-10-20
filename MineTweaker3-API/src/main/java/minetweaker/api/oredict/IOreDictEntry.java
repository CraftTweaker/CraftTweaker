package minetweaker.api.oredict;

import minetweaker.api.item.*;
import stanhebben.zenscript.annotations.*;

/**
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
