package minetweaker.api.item;

import minetweaker.api.block.IBlock;
import minetweaker.api.data.IData;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * Contains an item stack. An item stack consists of an item definition, a meta
 * or damage value and NBT data.
 * <p>
 * Item stacks can be retrieved using its name (or ID, in 1.6.4) with the
 * bracket syntax. When using the bracket syntax, by default, the item stack
 * will match any size when used as ingredient. Item stacks can be multiplied
 * with an integer to create a stack of different size.
 * <p>
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
    IItemDefinition getDefinition();
    
    /**
     * Gets the unlocalized item name.
     *
     * @return unlocalized item name
     */
    @ZenGetter("name")
    String getName();
    
    /**
     * Gets the display name. By default, it is the localized item name, but
     * could also be the display name from NBT data, or another item-specific
     * name.
     *
     * @return item display name
     */
    @ZenGetter("displayName")
    String getDisplayName();
    
    /**
     * Sets the display name. Only works if the name is translatable. Does not
     * override names set by NBT data or using item-specific logic.
     *
     * @param name item display name
     */
    @ZenSetter("displayName")
    void setDisplayName(String name);
    
    /**
     * Gets the max stack size for an item
     *
     * @return int max stack size
     */
    @ZenGetter("maxStackSize")
    int getMaxStackSize();
    
    /**
     * Sets the ItemStack max stack size.
     *
     * @param size the new max size
     */
    @ZenSetter("maxStackSize")
    void setMaxStackSize(int size);
    
    /**
     * Gets the block hardness
     */
    @ZenGetter("hardness")
    float getBlockHardness();
    
    /**
     * Sets the block hardness
     *
     * @param hardness the new hardness
     */
    @ZenSetter("hardness")
    void setBlockHardness(float hardness);
    
    /**
     * Gets the item damage. Returns 0 if the item cannot be damaged.
     *
     * @return item damage
     */
    @ZenGetter("damage")
    int getDamage();
    
    /**
     * Gets the maximum item damage. Returns 0 if the item cannot be damaged.
     *
     * @return maximum item damage
     */
    @ZenGetter("maxDamage")
    int getMaxDamage();
    
    /**
     * Sets the max Itemstack damage
     *
     * @param damage the new max damage
     */
    @ZenSetter("maxDamage")
    void setMaxDamage(int damage);
    
    /**
     * Gets the item tag. (NBT data) The resulting data is immutable and can
     * only be changed with updateTag. Returns empty data if there is no data.
     * Never returns null.
     *
     * @return item tag
     */
    @ZenGetter("tag")
    IData getTag();
    
    /**
     * Gets the liquid contained in this item, if any. If this item stack is not
     * an item container, it returns null. Only returns the amount of liquid in
     * a single item and doesn't into account stack size.
     *
     * @return liquid data
     */
    @ZenGetter("liquid")
    ILiquidStack getLiquid();
    
    /**
     * Creates a new item stack with a different stack size.
     *
     * @param amount new item stack size
     *
     * @return new item stack
     */
    @ZenMethod
    IItemStack amount(int amount);
    
    /**
     * Creates a weighted item stack with the given percentage chance. Does the
     * same as item.weight(p * 0.01).
     *
     * @param p probability, with percent
     *
     * @return weighted item stack
     */
    @ZenOperator(OperatorType.MOD)
    WeightedItemStack percent(float p);
    
    /**
     * Creates a weighted item stack with the given weight.
     *
     * @param p item weight
     *
     * @return weighted item stack
     */
    @ZenMethod
    WeightedItemStack weight(float p);
    
    /**
     * Creates an item stack with the same item and stack size, but accepting
     * any damage value. Only useful for items used as ingredients.
     *
     * @return item stack with wildcard damage
     */
    @ZenMethod
    IIngredient anyDamage();
    
    /**
     * Creates an item stack with the specified damage.
     *
     * @param damage damage value
     *
     * @return new, modified item stack
     */
    @ZenMethod
    IItemStack withDamage(int damage);
    
    /**
     * Creates an item stack with the specified stack size.
     *
     * @param amount stack size
     *
     * @return new, modified item stack
     */
    @ZenOperator(OperatorType.MUL)
    @ZenMethod
    IItemStack withAmount(int amount);
    
    /**
     * Creates an item stack with wildcard stack size.
     *
     * @return new, modified item stack
     */
    
    @ZenMethod
    IItemStack anyAmount();
    
    /**
     * Creates an item stack with the given nbt tag. Ignores existing tags.
     *
     * @param tag item tag to be assigned
     *
     * @return resulting item stack
     */
    @ZenMethod
    IItemStack withTag(IData tag);
    
    /**
     * Create an item stack with an empty nbt tag. Removes previous tag if any.
     *
     * @return resulting item stack
     */
    @ZenMethod
    IItemStack withEmptyTag();
    
    /**
     * Creates an item stack without the given nbt tag.
     *
     * @param tag item tag to be removed
     *
     * @return resulting item stack
     */
    @ZenMethod
    IItemStack removeTag(String tag);
    
    /**
     * Creates an item stack with updated nbt tag. Updates existing tags.
     *
     * @param tagUpdate item tag updates
     *
     * @return updated item stack
     */
    @ZenMethod
    IItemStack updateTag(IData tagUpdate);
    
    /**
     * Converts this item stack into a block.
     *
     * @return block, or null if this item is not a block
     */
    @ZenCaster
    IBlock asBlock();
    
    /**
     * Retrieves all the ores referring to this item. Includes wildcard ore
     * entries.
     *
     * @return ore entries containing this item stack
     */
    @ZenGetter("ores")
    List<IOreDictEntry> getOres();
}
