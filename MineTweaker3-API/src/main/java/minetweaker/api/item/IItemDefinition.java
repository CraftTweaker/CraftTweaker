package minetweaker.api.item;

import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Contains an item definition. Item definitions provide general information
 * about an item, such as its name and ID.
 * 
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
	public String getId();
	
	/**
	 * Gets the unlocalized item name.
	 * 
	 * @return item name
	 */
	@ZenGetter("name")
	public String getName();
	
	/**
	 * Makes an item stack from this definition.
	 * 
	 * @param meta meta value
	 * @return item stack
	 */
	@ZenMethod
	public IItemStack makeStack(@Optional int meta);
}
