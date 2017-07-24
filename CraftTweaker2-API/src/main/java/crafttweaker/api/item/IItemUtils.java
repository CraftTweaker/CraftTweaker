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
    
    @ZenMethod
    IItemStack createPotion(int count, Object[]... params);

}
