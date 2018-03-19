package crafttweaker.api.mods;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan Hebben
 */

@ZenRegister
@ZenClass("crafttweaker.mods.IMod")
public interface IMod {

    @ZenGetter("id")
    String getId();

    @ZenGetter("name")
    String getName();

    @ZenGetter("version")
    String getVersion();
    
    @ZenGetter("description")
    String getDescription();

    @ZenGetter("items")
    IItemStack[] getItems();
}
