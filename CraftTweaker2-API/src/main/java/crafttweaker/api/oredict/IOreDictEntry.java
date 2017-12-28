package crafttweaker.api.oredict;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.oredict.IOreDictEntry")
@ZenRegister
public interface IOreDictEntry extends IIngredient {

    @ZenGetter("name")
    String getName();

    @ZenGetter("empty")
    boolean isEmpty();

    @ZenGetter("firstItem")
    IItemStack getFirstItem();

    @ZenMethod
    void add(IItemStack... items);

    @ZenMethod
    void addItems(IItemStack[] items);

    @ZenMethod
    void addAll(IOreDictEntry entry);

    @ZenMethod
    void remove(IItemStack... items);

    @ZenMethod
    void removeItems(IItemStack[] items);


    @ZenOperator(OperatorType.CONTAINS)
    boolean contains(IItemStack item);

    @ZenMethod
    void mirror(IOreDictEntry other);
}
