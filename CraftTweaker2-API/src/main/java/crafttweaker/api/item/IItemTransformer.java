package crafttweaker.api.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * An item transformer modifies an item stack after crafting. Used to apply
 * damage, grab multiple items, change NBT tags, replace the item with something
 * else - or whatever else you'd want to happen to the input stack.
 *
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.item.IItemTransformer")
@ZenRegister
public interface IItemTransformer {
    
    /**
     * Transforms the specified item. May modify this item (and return it) or
     * return an entirely new one.
     *
     * @param item     input item
     * @param byPlayer player that performs the crafting
     *
     * @return output item
     */
    @ZenMethod
    IItemStack transform(IItemStack item, IPlayer byPlayer);
}
