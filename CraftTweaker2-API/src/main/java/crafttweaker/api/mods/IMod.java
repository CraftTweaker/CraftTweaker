package crafttweaker.api.mods;

import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan Hebben
 */
public interface IMod {
    
    @ZenGetter("id")
    String getId();
    
    @ZenGetter("name")
    String getName();
    
    @ZenGetter("version")
    String getVersion();
    
    @ZenGetter("decription")
    String getDescription();
    
    @ZenGetter("items")
    IItemStack[] getItems();
}
