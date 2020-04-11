package crafttweaker.mc1120.events.handling;

import crafttweaker.api.container.IContainer;
import crafttweaker.api.event.PlayerCloseContainerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;

public class MCPlayerCloseContainerEvent implements PlayerCloseContainerEvent {
    private final PlayerContainerEvent.Close event;

    public MCPlayerCloseContainerEvent(PlayerContainerEvent.Close event) {
        this.event = event;
    }

    @Override
    public IContainer getContainer() {
        return CraftTweakerMC.getIContainer(event.getContainer());
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
}
