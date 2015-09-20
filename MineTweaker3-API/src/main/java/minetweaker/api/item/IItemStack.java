package minetweaker.api.item;

import java.util.List;
import minetweaker.api.block.IBlock;
import minetweaker.api.data.IData;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;
import stanhebben.zenscript.annotations.ZenSetter;

/**
 * Contains an item stack. An item stack consists of an item definition, a meta
 * or damage value and NBT data.
 * 
 * Item stacks can be retrieved using its name (or ID, in 1.6.4) with the
 * bracket syntax. When using the bracket syntax, by default, the item stack
 * will match any size when used as ingredient. Item stacks can be multiplied
 * with an integer to create a stack of different size.
 * 
 * Item stacks are immutable. You can, however, easily create modified stacks
 * using the helper methods. Adding conditions and transformations will turn the
 * item stack into an ingredients.
 * 
 * @author Stan Hebben
 */
@ZenClass("minetweaker.item.IItemStack")
public interface IItemStack extends IIngredient {
	/**
	 * Gets the item definition.
	 * 
	 * @return item definition
	 */
	@ZenGetter("definition")
	public IItemDefinition getDefinition();

	/**
	 * Gets the unlocalized item name.
	 * 
	 * @return unlocalized item name
	 */
	@ZenGetter("name")
	public String getName();

	/**
	 * Gets the display name. By default, it is the localized item name, but
	 * could also be the display name from NBT data, or another item-specific
	 * name.
	 * 
	 * @return item display name
	 */
	@ZenGetter("displayName")
	public String getDisplayName();

	/**
	 * Sets the display name. Only works if the name is translatable. Does not
	 * override names set by NBT data or using item-specific logic.
	 * 
	 * @param name item display name
	 */
	@ZenSetter("displayName")
	public void setDisplayName(String name);

	/**
	 * Gets the max stack size for an item
	 * 
	 * @return int max stack size
	 */
	@ZenGetter("maxStackSize")
	public int getMaxStackSize();

	/**
	 * Sets the ItemStack max stack size.
	 * 
	 * @param size the new max size
	 */
	@ZenSetter("maxStackSize")
	public void setMaxStackSize(int size);

	/**
	 * Gets the block hardness
	 */
	@ZenGetter("hardness")
	public float getBlockHardness();

	/**
	 * Sets the block hardness
	 * 
	 * @param hardness the new hardness
	 */
	@ZenSetter("hardness")
	public void setBlockHardness(float hardness);

	/**
	 * Gets the item damage. Returns 0 if the item cannot be damaged.
	 * 
	 * @return item damage
	 */
	@ZenGetter("damage")
	public int getDamage();

	/**
	 * Gets the maximum item damage. Returns 0 if the item cannot be damaged.
	 * 
	 * @return maximum item damage
	 */
	@ZenGetter("maxDamage")
	public int getMaxDamage();

	/**
	 * Sets the max Itemstack damage
	 * 
	 * @param damage the new max damage
	 */
	@ZenSetter("maxDamage")
	public void setMaxDamage(int damage);

	/**
	 * Gets the item tag. (NBT data) The resulting data is immutable and can
	 * only be changed with updateTag. Returns empty data if there is no data.
	 * Never returns null.
	 * 
	 * @return item tag
	 */
	@ZenGetter("tag")
	public IData getTag();

	/**
	 * Gets the liquid contained in this item, if any. If this item stack is not
	 * an item container, it returns null. Only returns the amount of liquid in
	 * a single item and doesn't into account stack size.
	 * 
	 * @return liquid data
	 */
	@ZenGetter("liquid")
	public ILiquidStack getLiquid();

	/**
	 * Creates a new item stack with a different stack size.
	 * 
	 * @param amount new item stack size
	 * @return new item stack
	 */
	@ZenOperator(OperatorType.MUL)
	@ZenMethod
	public IItemStack amount(int amount);

	/**
	 * Creates a weighted item stack with the given percentage chance. Does the
	 * same as item.weight(p * 0.01).
	 * 
	 * @param p probability, with percent
	 * @return weighted item stack
	 */
	@ZenOperator(OperatorType.MOD)
	public WeightedItemStack percent(float p);

	/**
	 * Creates a weighted item stack with the given weight.
	 * 
	 * @param p item weight
	 * @return weighted item stack
	 */
	@ZenMethod
	public WeightedItemStack weight(float p);

	/**
	 * Creates an item stack with the same item and stack size, but accepting
	 * any damage value. Only useful for items used as ingredients.
	 * 
	 * @return item stack with wildcard damage
	 */
	@ZenMethod
	public IIngredient anyDamage();

	/**
	 * Creates an item stack with the specified damage.
	 * 
	 * @param damage damage value
	 * @return new, modified item stack
	 */
	@ZenMethod
	public IItemStack withDamage(int damage);

	/**
	 * Creates an item stack with the specified stack size.
	 * 
	 * @param amount stack size
	 * @return new, modified item stack
	 */
	@ZenMethod
	public IItemStack withAmount(int amount);

	/**
	 * Creates an item stack with wildcard stack size.
	 * 
	 * @return new, modified item stack
	 */
	@ZenMethod
	public IItemStack anyAmount();

	/**
	 * Creates an item stack with the given nbt tag. Ignores existing tags.
	 * 
	 * @param tag item tag to be assigned
	 * @return resulting item stack
	 */
	@ZenMethod
	public IItemStack withTag(IData tag);

	/**
	 * Creates an item stack with updated nbt tag. Updates existing tags.
	 * 
	 * @param tagUpdate item tag updates
	 * @return updated item stack
	 */
	@ZenMethod
	public IItemStack updateTag(IData tagUpdate);

	/**
	 * Converts this item stack into a block.
	 * 
	 * @return block, or null if this item is not a block
	 */
	@ZenCaster
	public IBlock asBlock();

	/**
	 * Retrieves all the ores referring to this item. Includes wildcard ore
	 * entries.
	 * 
	 * @return ore entries containing this item stack
	 */
	@ZenGetter("ores")
	public List<IOreDictEntry> getOres();
}
