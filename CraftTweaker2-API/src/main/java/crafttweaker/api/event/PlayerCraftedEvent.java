package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.recipes.ICraftingInventory;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.event.PlayerCraftingEvent")
@ZenRegister
public class PlayerCraftedEvent {
    
    private final IPlayer player;
    private final IItemStack output;
    private final ICraftingInventory inventory;
    
    public PlayerCraftedEvent(IPlayer player, IItemStack output, ICraftingInventory inventory) {
        this.player = player;
        this.output = output;
        this.inventory = inventory;
    }
    
    @ZenGetter("player")
    public IPlayer getPlayer() {
        return player;
    }
    
    @ZenGetter("output")
    public IItemStack getOutput() {
        return output;
    }
    
    @ZenGetter("inventory")
    public ICraftingInventory getInventory() {
        return inventory;
    }
}
