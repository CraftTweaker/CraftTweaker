package minetweaker.api.item;

import minetweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * Contains an item definition. Item definitions provide general information
 * about an item, such as its name and ID.
 * <p>
 * Item definitions themselves have no localized name, as it could be different
 * for each subitem or even depend on NBT tags.
 *
 * @author Stan Hebben
 */
@ZenClass("minetweaker.item.IItemDefinition")
public interface IItemDefinition {
    
    /**
     * Gets the item ID.
     *
     * @return item ID
     */
    @ZenGetter("id")
    String getId();
    
    /**
     * Gets the unlocalized item name.
     *
     * @return item name
     */
    @ZenGetter("name")
    String getName();
    
    /**
     * Makes an item stack from this definition.
     *
     * @param meta meta value
     *
     * @return item stack
     */
    @ZenMethod
    IItemStack makeStack(@Optional int meta);
    
    /**
     * Returns all ore entries containing this item. Also contains ore entries
     * that refer to a specific sub-item.
     *
     * @return all the ore entries containing this item.
     */
    @ZenGetter("ores")
    List<IOreDictEntry> getOres();
}
