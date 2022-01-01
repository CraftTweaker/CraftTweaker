package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerWakeUpEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;

public class MCPlayerWakeUpEvent implements PlayerWakeUpEvent {

    private final net.minecraftforge.event.entity.player.PlayerWakeUpEvent event;

    public MCPlayerWakeUpEvent(net.minecraftforge.event.entity.player.PlayerWakeUpEvent event) {
        this.event = event;
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }

    @Override
    public boolean shouldSetSpawn() {
        return event.shouldSetSpawn();
    }

    @Override
    public boolean shouldUpdateWorld() {
        return event.updateWorld();
    }

    @Override
    public boolean shouldWakeImmediately() {
        return event.wakeImmediately();
    }
}
