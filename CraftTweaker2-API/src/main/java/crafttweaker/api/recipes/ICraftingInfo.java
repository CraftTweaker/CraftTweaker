package crafttweaker.api.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.*;

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
    
    @ZenGetter("dimensionID")
    int getDimension();
    
    @ZenGetter("world")
    IWorld getWorld();
}
