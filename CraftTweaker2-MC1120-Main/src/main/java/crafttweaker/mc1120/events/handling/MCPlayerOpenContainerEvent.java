package crafttweaker.mc1120.events.handling;

import crafttweaker.api.container.IContainer;
import crafttweaker.api.event.PlayerOpenContainerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;

public class MCPlayerOpenContainerEvent implements PlayerOpenContainerEvent{
    private final PlayerContainerEvent.Open event;
    
    public MCPlayerOpenContainerEvent(PlayerContainerEvent.Open event) {
        this.event = event;
    }
    
    @Override
    public IContainer getContainer() {
        return CraftTweakerMC.getIContainer(event.getContainer());
    }
    
    @Override
    public boolean isCanceled() {
        return event.isCanceled();
    }
    
    @Override
    public void setCanceled(boolean canceled) {
        event.setCanceled(canceled);
    }
    
    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
}
