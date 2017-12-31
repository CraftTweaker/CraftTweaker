package crafttweaker.api.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IDimension;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.recipes.ICraftingInfo")
@ZenRegister
public interface ICraftingInfo {

    @ZenGetter("inventory")
    ICraftingInventory getInventory();

    @ZenGetter("player")
    IPlayer getPlayer();

    @ZenGetter("dimension")
    IDimension getDimension();
}
