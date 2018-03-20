package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.recipes.ICraftingInventory;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.event.PlayerCraftedEvent")
@ZenRegister
public interface PlayerCraftedEvent extends IPlayerEvent {
    
    @ZenGetter("output")
    IItemStack getOutput();
    
    @ZenGetter("inventory")
    ICraftingInventory getInventory();
}
