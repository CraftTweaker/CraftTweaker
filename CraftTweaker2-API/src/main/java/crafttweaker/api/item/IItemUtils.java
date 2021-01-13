package crafttweaker.api.item;

import crafttweaker.api.data.IData;
import crafttweaker.api.enchantments.IEnchantment;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.potions.IPotionEffect;
import stanhebben.zenscript.annotations.*;

/**
 *
 * Class for various Item utils
 * can be called with itemUtils.function
 *
 * @author BloodWorkXGaming
 */
public interface IItemUtils {
    
    /**
     *
     * @param params has to be in this syntax:
     *               [<effect1>, strength, time], [<effect2>, strength, time], ...
     * @return returns the {@link IItemStack} of the potion
     */
    @ZenMethod
    IItemStack createPotion(Object[]... params);


    /**
     * @param params list of IPotionEffects
     * @return retursn the {@link IItemStack} of the potion
     */
    @ZenMethod
    IItemStack createPotion(IPotionEffect... params);

    /**
     * @param item An item to be enchanted. Outside of checking for <minecraft:enchanted_book>
     *             or <minecraft:book> (to determine which key to use), no checks are made to
     *             make sure that the enchantments can actually be applied to the item.
     *             If the item is already enchanted, this will attempt to merge the new
     *             enchantments into the existing list.
     * @param enchantments List of enchantments, the result of (eg):
     *                     <enchantment:minecraft:protection>.makeEnchantment(3);
     * @return returns the {@link IItemStack} of the item with new enchantments.
     */
    @ZenMethod
    IItemStack enchantItem (IItemStack item, IEnchantment... enchantments);

    /**
     * @param enchantments List of enchantment, the result of (eg):
     *                     <enchantment:minecraft:protection>.makeEnchantment(3);
     * @return returns the {@link IItemStack} of the enchanted book.
     */
    @ZenMethod
    IItemStack createEnchantedBook(IEnchantment... enchantments);

    /**
     * @param baseKey The base key, one of either "ench" or "StoredEnchantments". The former
     *                functions for enchanted items (swords, weapons, tools, etc) while the latter
     *                is specifically for Enchanted Books.
     * @param enchantments Contains the enchantment to combine, result of (eg):
     *                     <enchantment:minecraft:protection>.makeEnchantment(3)
     * @return A combined list of enchantments as though you had manually combined using
     * makeTag. This unoverloaded method allows for specifying the base key, unlike its 
     * overloaded counterpart.
     */
    @ZenMethod
    IData combineEnchantments (String baseKey, IEnchantment... enchantments);

    /**
     * @param enchantments Contains the enchantment to combine, result of (eg):
     *                     <enchantment:minecraft:protection>.makeEnchantment(3)
     * @return A combined list of enchantments as though you had manually combined using
     * makeTag. By default this method overloads with the key "ench", making it suitable
     * for application to the end-result product enchanted items. Use the unoverloaded method
     * with the key "StoredEnchantments" to create an enchanted book, or use createEnchantedBook.
     */
    @ZenMethod
    IData combineEnchantments (IEnchantment... enchantments);

    /**
     * Gets all Items where the Regex matches the Registry name
     * check in syntax "mod:item:meta"
     * meta is always present
     * @param regex to compare agains
     * @return Array of Items that match
     */
    @ZenMethod
    IItemStack[] getItemsByRegexRegistryName(String regex);
    
    /**
     * Invokes the Bracket Handler Item method
     * @param location What you would write in the BH, e.g. "minecraft:dirt"
     * @param meta The metadata, default to 0
     * @return The same thing the Bracket handler would return.
     */
    @ZenMethod
    IItemStack getItem(String location, @Optional int meta);
    
    /**
     * Gets all Items where the Regex matches the Unlocalized name
     * check in syntax "mod:item:meta"
     * meta is always present
     * @param regex to compare agains
     * @return Array of Items that match
     */
    @ZenMethod
    IItemStack[] getItemsByRegexUnlocalizedName(String regex);
    
    
    /**
     * Creates a Spawnegg of the given entity
     * @param entity
     * @param customNBT NBT that the Item should have, entity gets overwritten by function
     * example: itemUtils.createSpawnEgg(<entity:minecraft:sheep>, {EntityTag:{id:"minecraft:creeper",NoAI:1 as byte,PersistenceRequired:1 as byte}})
     */
    @ZenMethod
    IItemStack createSpawnEgg(IEntityDefinition entity, @Optional IData customNBT);
    
    
}
