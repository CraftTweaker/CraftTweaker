package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerCraftedEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.recipes.ICraftingInventory;
import crafttweaker.mc1120.recipes.MCCraftingInventorySquared;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * @author Stan Hebben
 */
public class MCPlayerCraftedEvent implements PlayerCraftedEvent {
    
    private final PlayerEvent.ItemCraftedEvent event;
    
    public MCPlayerCraftedEvent(PlayerEvent.ItemCraftedEvent event) {
    
        this.event = event;
    }
    
    @Override
    public IItemStack getOutput() {
        return CraftTweakerMC.getIItemStack(event.crafting);
    }
    
    @Override
    public ICraftingInventory getInventory() {
        return new MCCraftingInventorySquared(event.craftMatrix, getPlayer());
    }
    
    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.player);
    }
}
