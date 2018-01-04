package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IEntityPlayer;
import crafttweaker.api.recipes.ICraftingInventory;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.event.PlayerCraftedEvent")
@ZenRegister
public class PlayerCraftedEvent {
    
    private final IEntityPlayer player;
    private final IItemStack output;
    private final ICraftingInventory inventory;
    
    public PlayerCraftedEvent(IEntityPlayer player, IItemStack output, ICraftingInventory inventory) {
        this.player = player;
        this.output = output;
        this.inventory = inventory;
    }
    
    @ZenGetter("player")
    public IEntityPlayer getPlayer() {
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
