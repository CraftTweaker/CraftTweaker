package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerCloneEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class MCPlayerCloneEvent implements PlayerCloneEvent {

    private final PlayerEvent.Clone event;

    public MCPlayerCloneEvent(PlayerEvent.Clone event) {
        this.event = event;
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }

    @Override
    public boolean wasDeath() {
        return event.isWasDeath();
    }

    @Override
    public IPlayer getOriginal() {
        return CraftTweakerMC.getIPlayer(event.getOriginal());
    }
}