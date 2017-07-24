package crafttweaker.api.item;

import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * Class for various Item utils
 * can be called with items.function
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
     * Gets all Items where the Regex matches the Registry name
     * check in syntax "mod:item:meta"
     * meta is always present
     * @param regex to compare agains
     * @return Array of Items that match
     */
    @ZenMethod
    IItemStack[] getItemsByRegexRegistryName(String regex);
    
    
    /**
     * Gets all Items where the Regex matches the Unlocalized name
     * check in syntax "mod:item:meta"
     * meta is always present
     * @param regex to compare agains
     * @return Array of Items that match
     */
    @ZenMethod
    IItemStack[] getItemsByRegexUnlocalizedName(String regex);
    
}
