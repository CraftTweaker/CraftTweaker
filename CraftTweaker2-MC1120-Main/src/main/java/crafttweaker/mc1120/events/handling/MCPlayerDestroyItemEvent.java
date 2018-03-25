package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerDestroyItemEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;

public class MCPlayerDestroyItemEvent implements PlayerDestroyItemEvent {
    
    private final net.minecraftforge.event.entity.player.PlayerDestroyItemEvent event;
    
    public MCPlayerDestroyItemEvent(net.minecraftforge.event.entity.player.PlayerDestroyItemEvent event) {
        this.event = event;
    }
    
    @Override
    public IItemStack getOriginalItem() {
        return CraftTweakerMC.getIItemStack(event.getOriginal());
    }
    
    @Override
    public String getHand() {
        return String.valueOf(event.getHand());
    }
    
    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
}
