package minetweaker.api.recipes;

import minetweaker.api.player.IPlayer;
import minetweaker.api.world.IDimension;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan Hebben
 */
@ZenClass("minetweaker.recipes.ICraftingInfo")
public interface ICraftingInfo {
    
    @ZenGetter("inventory")
    ICraftingInventory getInventory();
    
    @ZenGetter("player")
    IPlayer getPlayer();
    
    @ZenGetter("dimension")
    IDimension getDimension();
}
